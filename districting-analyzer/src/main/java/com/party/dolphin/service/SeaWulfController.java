package com.party.dolphin.service;

import java.io.*;

import com.party.dolphin.model.Job;
import com.party.dolphin.model.enums.JobStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeaWulfController {

    public static final String algorithmDirName = "files/algorithm/";
    public static final String sendFileScript = "src/main/esources/sendFile.sh";

    // TODO: Test this with data
    public Job sendJob(Job job) {
        // Process process;
        // ProcessBuilder pb = new ProcessBuilder("python3", "src/main/resources/fib.py");
        // pb.redirectErrorStream(true);
        // try {
        //     process = pb.start();
        //     debugProcessOutput(process);
        // } catch (IOException ioex) {
        //     System.out.println(ioex.getMessage());
        // }
        // return true;

        // if (!sendData(job.getState())) {
        //     System.out.println("Could not send precinct data to the SeaWulf");
        //     return false;
        // }

        Process process;
        ProcessBuilder pb = new ProcessBuilder("bash", "src/main/resources/send_to_slurm.sh");
        pb.redirectErrorStream(true);
        //sendData(job);
        try {
            process = pb.start();
            debugProcessOutput(process);
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

        job.setStatus(JobStatus.running);
        return job;
    }

    public Job checkJobStatus(Job job) {
        return job;
    }

    // public boolean writeJobScript(Job job){
    //   // create a slurm script file
    //   File file = new File("job.slurm");
    //   FleWriter writer = new FileWriter("job.slurm")
    //   writer.write("#!/bin/bash")
    //   writer.write("#")
    //   writer.write("#SBATCH --job-name="+job.jobId)
    //   writer.write("#SBATCH --output="+job.jobId+".txt")
    //   writer.write("#SBATCH --ntasks-per-node=40")
    //   writer.write("#SBATCH --nodes=2")
    //   writer.write("#SBATCH --time=05:00")
    //   writer.write("#SBATCH -p short-40core")
    //   writer.write("#SBATCH --nodes=2")
    //   writer.write("#SBATCH --mail-type=BEGIN,END")
    //   writer.write("#SBATCH --mail-user=kamile.demir@stonybrook.edu")
    //   writer.write("module load slurm")
    //   writer.write("module load shared")
    //   writer.write("python run.py %s\n" %data)
    // }

    // TODO: How much to send? Just precincts? State? Counties?
    private boolean sendData(Job job) {
        Process process;
        ProcessBuilder pb = new ProcessBuilder(
            "bash",
            sendFileScript,
            job.getArgsFilePath(),
            job.getPrecinctFilePath()
        );
        pb.redirectErrorStream(true);
        try {
            process = pb.start();
            debugProcessOutput(process);
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

    private boolean readOutputData(Job job) {
        return true;
    }

    private void debugProcessOutput(Process process) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            String result = builder.toString();
            System.out.println(result);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

}
