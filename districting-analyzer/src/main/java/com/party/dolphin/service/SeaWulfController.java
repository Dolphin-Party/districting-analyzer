package com.party.dolphin.service;

import java.io.*;

import com.party.dolphin.model.Job;
import com.party.dolphin.model.enums.JobStatus;

import org.springframework.stereotype.Service;

@Service
public class SeaWulfController {

    public static final String sendFileScript = "src/main/resources/sendFile.sh";
    public static final String getFileScript = "src/main/resources/get_output.sh";
    public static final String startSeaWulfJobScript = "src/main/resources/run_seawulf_job.sh";
    public static final String checkSeaWulfJobScript = "src/main/resources/check_seawulf_job.sh";
    public static final String cancelSeaWulfJobScript = "src/main/resources/cancel_seawulf_job.sh";

    //public static final String seawulfDirPath = "/gpfs/projects/CSE416/Dolphins/";
    public static final String jobDirPathTemplate = "/gpfs/projects/CSE416/Dolphins/job_%d/";

    // TODO: Test this with the algorithm
    // TODO: Clean up redundant process builder and try-catch blocks
    public Job runJob(Job job) {
        if (!sendData(job)) {
            System.out.println("Could not send precinct data to the SeaWulf");
            job.setStatus(JobStatus.error);
            return job;
        }
        job = startJob(job);
        if (job.getStatus() == JobStatus.error) {
            System.out.println("Error when starting job on SeaWulf");
        }
        return job;
    }

    public Job checkJobStatus(Job job) {
        Process process;
        ProcessBuilder pb = new ProcessBuilder(
            "bash",
            checkSeaWulfJobScript,
            Integer.toString(job.getSeawulfJobId())
        );
        //pb.redirectErrorStream(true);

        String jobStatusLine;
        try {
            process = pb.start();
            jobStatusLine = processOutput(process);
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            return job;
        }

        int i = jobStatusLine.indexOf('=');
        int j = jobStatusLine.indexOf(' ', i);
        jobStatusLine = jobStatusLine.substring(i+1, j);
        System.err.println("Slurm job status: "+ jobStatusLine);
        switch (jobStatusLine) {
            case "PENDING":
            case "RUNNING":
                break;
            case "COMPLETED":
                job.setStatus(JobStatus.finishDistricting);
                if (getOutputData(job))
                    break;
            case "CANCELLED":
                job.setStatus(JobStatus.stopped);
                break;
            case "FAILED":
            default:
                job.setStatus(JobStatus.error);
        }
        return job;
    }

    public Job cancelJob(Job job) {
        ProcessBuilder pb = new ProcessBuilder(
            "bash",
            cancelSeaWulfJobScript,
            Integer.toString(job.getSeawulfJobId())
        );
        //pb.redirectErrorStream(true);

        try {
            pb.start();
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            job.setStatus(JobStatus.error);
            return job;
        }
        job.setStatus(JobStatus.stopped);
        return job;
    }

    private Job startJob(Job job) {
        Process process;
        ProcessBuilder pb = new ProcessBuilder(
            "bash",
            startSeaWulfJobScript,
            Integer.toString(job.getId()),
            job.getPrecinctFilePath()
                .substring(job.getPrecinctFilePath().lastIndexOf('/'))
        );
        //pb.redirectErrorStream(true);

        String seawulfJobId;
        try {
            process = pb.start();
            seawulfJobId = processOutput(process);
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            job.setStatus(JobStatus.error);
            return job;
        }

        seawulfJobId = seawulfJobId.trim();
        seawulfJobId = seawulfJobId.substring(seawulfJobId.lastIndexOf(' ')+1);
        System.err.println("SeaWulf job id: " + seawulfJobId);
        job.setSeawulfJobId(
            Integer.parseInt(seawulfJobId)
        );
        job.setStatus(JobStatus.running);
        return job;
    }

    private boolean sendData(Job job) {
        Process process;
        ProcessBuilder pb = new ProcessBuilder(
            "bash",
            sendFileScript,
            job.getArgsFilePath()
                .substring(0, job.getArgsFilePath().lastIndexOf('/')+1)
        );
        pb.redirectErrorStream(true);
        try {
            process = pb.start();
            System.out.println(processOutput(process));
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            return false;
        }

        return true;
    }

    private boolean getOutputData(Job job) {
        Process process;
        ProcessBuilder pb = new ProcessBuilder(
            "bash",
            getFileScript,
            Integer.toString(job.getId()),
            job.getOutputFile().getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        try {
            process = pb.start();
            System.out.println(processOutput(process));
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            return false;
        }

        System.err.println("Output file received");
        return true;
    }

    private String processOutput(Process process) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            return builder.toString();
        } catch (IOException ioex) {
            ioex.printStackTrace();
            return null;
        }
    }

}
