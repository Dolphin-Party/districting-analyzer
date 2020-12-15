#!/bin/bash

# $1 is the seawulf job ID
ssh yang28@login.seawulf.stonybrook.edu "source /etc/profile.d/modules.sh; module load slurm; scontrol show job $1 | grep JobState;"
