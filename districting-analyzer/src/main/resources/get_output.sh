#!/bin/bash

# TODO: May need to add an ssh priv key to this repo
# Sends job folder with arg file
scp yang28@login.seawulf.stonybrook.edu:/gpfs/projects/CSE416/Dolphins/job_$1/results.json "$2"