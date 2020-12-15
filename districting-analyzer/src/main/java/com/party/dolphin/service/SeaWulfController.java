package com.party.dolphin.service;

import java.io.*;

import com.party.dolphin.model.Job;
import com.party.dolphin.model.enums.JobStatus;

import org.springframework.stereotype.Service;

@Service
public class SeaWulfController {

    //public static final String localAlgorithmDirName = "files/algorithm/";
    public static final String sendFileScript = "src/main/resources/sendFile.sh";
    public static final String startSeaWulfJobScript = "src/main/resources/run_seawulf_job.sh";
    public static final String jobDirPathTemplate = "/gpfs/projects/CSE416/Dolphins/job_%d/";

    // TODO: Test this with data
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
        return job;
    }

    public Job cancelJob(Job job) {
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
        try {
            process.waitFor();
        } catch (InterruptedException intEx) {
            process.destroy();
            job.setStatus(JobStatus.error);
            return job;
        }

        seawulfJobId = seawulfJobId.trim();
        seawulfJobId = seawulfJobId.substring(seawulfJobId.lastIndexOf(' ')+1);
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
        try {
            process.waitFor();
        } catch (InterruptedException intEx) {
            process.destroy();
            return false;
        }

        return true;
    }

    private boolean getOutputData(Job job) {
        return true;
    }

    private boolean readOutputData(Job job) {
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
