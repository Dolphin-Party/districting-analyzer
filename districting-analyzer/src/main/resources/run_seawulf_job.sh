#!/bin/bash

# $1 is the database job ID
# $2 is the state precincts file name
ssh jpeshansky@login.seawulf.stonybrook.edu "source /etc/profile.d/modules.sh; module load slurm; cd /gpfs/projects/CSE416/Dolphins/; sbatch job.slurm $1 $2"
