package com.party.dolphin.service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import com.party.dolphin.dto.JobDto;
import com.party.dolphin.dto.PrecinctDto;
import com.party.dolphin.model.Job;
import com.party.dolphin.model.State;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeaWulfController {

    public static final String precinctFileName = "files/%s_precincts.json";
    public static final String sendFileScript = "src/resources/sendFile.sh";

    @Autowired
    private ModelConverter modelConverter;

    // TODO: Test this with data
    public boolean sendJob(Job job) {
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
        return sendData(job.getState());
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
    //   writer.write("Rscript $HOME/project/CSE416/Dolphins/run.R %s\n" %data)
    // }

    // TODO: How much to send? Just precincts? State? Counties?
    private boolean sendData(State state) {
        String dataFileName = String.format(precinctFileName, state.getName().replace(' ', '_'));
        File file = new File(dataFileName);
        if (!file.exists() && writeFile(file, state)) {
            System.out.println("Failed to create or write file");
            return false;
        }

        Process process;
        ProcessBuilder pb = new ProcessBuilder("bash", sendFileScript, dataFileName);
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

    private boolean writeFile(File file, State state) {
        ObjectMapper mapper = new ObjectMapper();
        List<PrecinctDto> precincts = state
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
