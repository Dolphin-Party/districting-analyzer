cat src/main/resources/seawulf.slurm | ssh jpeshansky@login.seawulf.stonybrook.edu 'source /etc/profile.d/modules.sh; module load slurm; module load anaconda/3; cd /gpfs/projects/CSE416/; sbatch'
