package com.party.dolphin.service;

import java.io.*;
import java.util.*;
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
    public static final String algorithmFileName = "src/main/resources/randomdistricter.py";
    public static final String sendFileScript = "src/main/resources/sendFile.sh";

    public static final String precinctFilePathTemplate = "files/%s_precincts.json";
    public static final String argsFilePathTemplate = "files/tmp/job_args_%d.json";
    public static final String outputDirPathTemplate = "files/tmp/job_%d_out/";
    public static final String outputFileName = "results.json";


    @Autowired
    private JobService jobService;
    @Autowired
    private DtoConverter dtoConverter;
    @Autowired
    private ModelConverter modelConverter;
    @Autowired
    private SeaWulfController seawulfController;

    /** Job Control **/
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
            String outputFile = String.format(outputDirPathTemplate + outputFileName, job.getId());
            job.setOutputFile(new File(outputFile));

            if (job.getIsSeawulf())
                job = seawulfController.checkJobStatus(job);
            else
                job = checkLocalJob(job);

            if (job.getStatus() == JobStatus.finishDistricting) {
                job = readOutputFiles(job);
                //job.analyzeJobResults();
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
        String outputFilePath = String.format(outputDirPathTemplate + outputFileName, job.getId());
        job.setOutputFile(new File(outputFilePath));
        if (!createDir(job.getOutputFile().getParent())) {
            System.out.println("Failed to create output dir");
            job.setStatus(JobStatus.error);
            return job;
        }

        Process process;
        ProcessBuilder pb = new ProcessBuilder(
            "python3",
            algorithmFileName,
            job.getArgsFilePath(),
            job.getPrecinctFilePath(),
            job.getOutputFile().getParent()
        );
        pb.redirectErrorStream(true);
        pb.redirectOutput(job.getOutputFile());
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
        if (!job.getOutputFile().exists())
            return job;
        // else file finished
        job.setStatus(JobStatus.finishDistricting);
        return job;
    }

    /** Write files **/
    private boolean writeArgsFile(Job job) {
        File file = new File(job.getArgsFilePath());
        if (file.exists()) {
            System.out.println("File " + file.getName() + " already exists");
            return true;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("numDistricts", job.getState().getNumberDistricts());
        map.put("compactness", job.getCompactnessAmount());
        map.put("iterations", job.getIterations());
        map.put("numDistrictings", job.getNumberDistrictings());
        map.put("percentDiff", job.getPercentDiff());
        return writeJsonFile(file, map);
    }

    private boolean writePrecinctsFile(Job job) {
        File file = new File(job.getPrecinctFilePath());
        if (file.exists()) {
            System.out.println("File " + file.getName() + " already exists");
            return true;
        }

        List<PrecinctDto> precincts = job.getState()
            .getCounties().stream()
            .map(c -> c.getPrecincts())
            .flatMap(p -> p.stream())
            .map(p -> modelConverter.createPrecinctDto(p))
            .collect(Collectors.toList());
        for (PrecinctDto p : precincts) {
            if (!p.convertShapeToCoordinates())
                return false;
        }
        List<PrecinctNeighborDto> precinctEdges = precincts.stream()
            .map(p -> p.getEdges())
            .flatMap(l -> l.stream())
            .collect(Collectors.toList());
        precincts.stream()
            .forEach(p -> {
                p.setNeighbors(null);
                p.setDemographics(null);
            });

        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("Num. precincts: " + precincts.size());
        System.out.println("Num. edges: " + precinctEdges.size());
        map.put("precincts", precincts);
        map.put("edges", precinctEdges);
        return writeJsonFile(file, map);
    }

    // object can be a POJO, List, or Map
    private boolean writeJsonFile(File file, Object object) {
        if (file.exists()) {
            System.out.println("File " + file.getName() + " already exists");
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
            file.delete();
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

    /** Read file **/
    public Job readOutputFiles(Job job) {
        if (!job.getOutputFile().exists()) {
            System.out.println("File not found");
            job.setStatus(JobStatus.error);
            return job;
        }

        ObjectMapper mapper = new ObjectMapper();
        String[][][] ids;
        try {
            ids = mapper.readValue(job.getOutputFile(), String[][][].class);
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
            job.setStatus(JobStatus.error);
            return job;
        }

        ArrayList<Districting> districtings = new ArrayList<Districting>(job.getNumberDistrictings());
        for (String[][] districting : ids) {
            Districting d = parseDistrictingDistricts(districting, job);
            districtings.add(d);
        }
        job.setDistrictings(districtings);
        job = jobService.saveJob(job);
        return job;
    }

    private Districting parseDistrictingDistricts(String[][] districtingDistricts, Job job) {
        Districting districting = new Districting();
        districting = jobService.saveDistricting(districting);
        districting.setJob(job);
        districting.setTargetDemographic(job.getTargetDemographic());

        ArrayList<District> districts = new ArrayList<District>();
        for (String[] district : districtingDistricts) {
            District d = parseDistrictPrecincts(district);
            d.setDistricting(districting);
            d = jobService.saveDistrict(d);
            districts.add(d);
        }

        districting.setDistricts(districts);
        districting = jobService.saveDistricting(districting);
        return districting;
    }

    private District parseDistrictPrecincts(String[] districtPrecincts) {
        DistrictDto dto = new DistrictDto();
        HashSet<String> precincts = new HashSet<String>();
        for (String precinctId : districtPrecincts) {
            precincts.add(precinctId);
        }
        dto.setPrecincts(precincts);
        return dtoConverter.createNewDistrict(dto);
    }

    // Debug, for printing process stdout/stderr
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