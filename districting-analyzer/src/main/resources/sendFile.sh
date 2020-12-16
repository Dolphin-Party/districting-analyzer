#!/bin/bash

# TODO: May need to add an ssh priv key to this repo
# Sends job folder with arg file
scp -r $1 $NETID@login.seawulf.stonybrook.edu:/gpfs/projects/CSE416/Dolphins/