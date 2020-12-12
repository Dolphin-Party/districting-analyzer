package com.party.dolphin.service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.party.dolphin.dto.*;
import com.party.dolphin.model.*;
import com.party.dolphin.model.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerDispatcher {

    // TODO: Turn all of these into global env. variables/properties
    public static final String algorithmDirName = "files/algorithm/";
    public static final String sendFileScript = "src/main/resources/sendFile.sh";

    public static final String precinctFilePathTemplate = "files/%s_precincts.json";
    public static final String argsFilePathTemplate = "files/tmp/job_args_%d.json";
    public static final String outputDirPathTemplate = "files/tmp/job_%d_out/";
    public static final String outputFileName = "out.json";


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

    public Job checkJobStatus(Job job) {
        if (job.getStatus() == JobStatus.running) {
            job.setOutputFilePath(
                String.format(outputDirPathTemplate + outputFileName, job.getId())
            );

            if (job.getIsSeawulf())
                job = seawulfController.checkJobStatus(job);
            else
                job = checkLocalJob(job);

            if (job.getStatus() == JobStatus.finishDistricting) {
                job = readOutputFiles(job);
                job.analyzeJobResults();
                job.setStatus(JobStatus.finishProcessing);
            }
        }
        return job;
    }

    public Job cancelJob(Job job) {
        job.setStatus(JobStatus.stopped);
        return job;
    }

    private Job runLocally(Job job) {
        String outputDir = String.format(outputDirPathTemplate, job.getId());
        if (!createDir(outputDir)) {
            System.out.println("Failed to create output dir");
            job.setStatus(JobStatus.error);
            return job;
        }

        Process process;
        ProcessBuilder pb = new ProcessBuilder(
            "python3",
            "fib.py",
            job.getArgsFilePath(),
            job.getPrecinctFilePath(),
            outputDir
        );
        pb.redirectErrorStream(true);
        try {
            process = pb.start();
            job.setStatus(JobStatus.running);
            debugProcessOutput(process);
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            job.setStatus(JobStatus.error);
        }

        return job;
    }

    private Job checkLocalJob(Job job) {
        File file = new File(job.getOutputFilePath());
        if (!file.exists())
            return job;
        // else file finished
        job.setStatus(JobStatus.finishDistricting);
        return job;
    }

    private boolean writeArgsFile(Job job) {
        File file = new File(job.getArgsFilePath());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("numDistricts", 1);
        map.put("compactness", 0.2);
        map.put("iterations", 10);
        map.put("numDistrictings", 10);
        map.put("percentDiff", 0.1);
        return writeJsonFile(file, map);
    }

    // Sacrifice efficiency for readability
    // by fetching precincts before checking if file exists
    private boolean writePrecinctsFile(Job job) {
        File file = new File(job.getPrecinctFilePath());
        List<PrecinctDto> precincts = job.getState()
            .getCounties().stream()
            .map(c -> c.getPrecincts())
            .flatMap(p -> p.stream())
            .map(p -> modelConverter.createPrecinctDto(p))
            .collect(Collectors.toList());
        // List<PrecinctNeighborDto> precinctEdges = precincts.stream()
        //     .map(p -> p.getEdges())
        //     .flatMap(l -> l.stream())
        //     .collect(Collectors.toList());
        precincts.stream()
            .forEach(p -> p.setNeighbors(null));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("precincts", precincts);
        // map.put("edges", precinctEdges);
        return writeJsonFile(file, map);
    }

    // object can be a POJO, List, or Map
    private boolean writeJsonFile(File file, Object object) {
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
            mapper.writeValue(file, object);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
            return false;
        }
        return true;
    }

    private boolean createDir(String dir) {
        File file = new File(dir);
        if (file.exists() && file.isDirectory()) {
            System.out.println("Directory already exists");
            return true;
        }

        return file.mkdirs();
    }

    // TODO:
    private Job readOutputFiles(Job job) {
        File file = new File(job.getOutputFilePath());
        if (!file.exists()) {
            System.out.println("File not found");
            job.setStatus(JobStatus.error);
            return job;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<DistrictingDto> districtings;

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