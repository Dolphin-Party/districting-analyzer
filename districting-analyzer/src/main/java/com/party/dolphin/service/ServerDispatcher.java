package com.party.dolphin.service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.model.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerDispatcher {

    public static final String algorithmDirName = "files/algorithm/";
    public static final String sendFileScript = "src/main/esources/sendFile.sh";

    public static final String precinctFilePathTemplate = "files/%s_precincts.json";
    public static final String argsFilePathTemplate = "files/tmp/job_args_%d.json";

    @Autowired
    private ModelConverter modelConverter;
    @Autowired
    private SeaWulfController seawulfController;

    public Job runJob(Job job) {
        job.setPrecinctFilePath(
            String.format(precinctFilePathTemplate, job.getState().getName().replace(' ', '_'))
        );
        if (!writePrecinctsFile(job)) {
            System.out.println("Failed to create or write file");
            job.setStatus(JobStatus.error);
            return job;
        }
        job.setArgsFilePath(
            String.format(argsFilePathTemplate, job.getId())
        );
        if (!writeArgsFile(job)) {
            System.out.println("Failed to create or write file");
            job.setStatus(JobStatus.error);
            return job;
        }

        if (job.getIsSeawulf())
            job = seawulfController.sendJob(job);
        else
            job = runLocally(job);
        return job;
    }

    public Job getJobStatus(Job job) {
        if (job.getIsSeawulf() && job.getStatus() == JobStatus.running)
            seawulfController.checkJobStatus(job);
        if (job.getStatus() == JobStatus.finishDistricting)
            job.analyzeJobResults();
        return job;
    }

    public Job cancelJob(Job job) {
        job.setStatus(JobStatus.stopped);
        return job;
    }

    private Job runLocally(Job job) {
        return job;
    }

    private boolean writeArgsFile(Job job) {
        File file = new File(job.getArgsFilePath());
        if (file.exists()) {
            System.out.println("File already exists");
            return true;
        }
        ObjectMapper mapper = new ObjectMapper();

        try {
            File parentDirs = file.getParentFile();
            if (!parentDirs.exists() && !parentDirs.mkdirs())
                throw new IOException("Couldn't create parent dir: " + parentDirs.getName());
            file.createNewFile();
            //PrintWriter writer = new PrintWriter(new FileWriter(file));
            mapper.writeValue(file, "Test");
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
            return false;
        }

        return true;
    }

    private boolean writePrecinctsFile(Job job) {
        File file = new File(job.getPrecinctFilePath());
        if (file.exists()) {
            System.out.println("File already exists");
            return true;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<PrecinctDto> precincts = job.getState()
            .getCounties().stream()
            .map(c -> c.getPrecincts())
            .flatMap(p -> p.stream())
            .map(p -> modelConverter.createPrecinctDto(p))
            .collect(Collectors.toList());
        try {
            File parentDirs = file.getParentFile();
            if (!parentDirs.exists() && !parentDirs.mkdirs())
                throw new IOException("Couldn't create parent dir: " + parentDirs.getName());
            file.createNewFile();
            //PrintWriter writer = new PrintWriter(new FileWriter(file));
            mapper.writeValue(file, precincts);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
            return false;
        }

        return true;
    }

    private Job readOutputFiles(Job job) {
        return job;
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