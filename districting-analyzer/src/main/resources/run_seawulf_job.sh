#!/bin/bash

# $1 is the database job ID
# $2 is the state precincts file name
ssh $NETID@login.seawulf.stonybrook.edu "source /etc/profile.d/modules.sh; module load slurm; cd /gpfs/projects/CSE416/Dolphins/; sbatch parallel.slurm $1 $2;"
