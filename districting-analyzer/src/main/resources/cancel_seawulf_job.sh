#!/bin/bash

# $1 is the seawulf job ID
ssh $NETID@login.seawulf.stonybrook.edu "source /etc/profile.d/modules.sh; module load slurm; scancel $1;"
