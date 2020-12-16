#!/usr/bin/env bash

#SBATCH --job-name=dolphin_job
#SBATCH --output=debug.json
#SBATCH --ntasks-per-node=40
#SBATCH --nodes=1
#SBATCH --time=00:11:59
#SBATCH -p medium-40core
#SBATCH --mail-type=BEGIN,END
#SBATCH --mail-user=yang.yang.7@stonybrook.edu

## All sbatch params can/should be adjusted above

#echo I am using the following nodes: $SLURM_JOB_NODELIST
#echo My job ID is: $SLURM_JOB_ID

# Load python3
module load anaconda/3

# Load the python virtualenv
source activate my_env

# Run the script
# $1 is the database job ID
# $2 is the state precincts file name
python3 "./algorithm/randomdistricter.py" "./job_$1/job_args.json" "./$2" "./job_$1/" > "./job_$1/results.json"