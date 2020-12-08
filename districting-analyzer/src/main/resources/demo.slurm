#!/bin/bash
#
#SBATCH --job-name=test
#SBATCH --output=res.txt
#SBATCH --ntasks-per-node=40
#SBATCH --nodes=2
#SBATCH --time=05:00
#SBATCH -p short-40core
#SBATCH --mail-type=BEGIN,END
#SBATCH --mail-user=kamile.demir@stonybrook.edu

module load shared
module load intel/compiler/64/2017/17.0.0
module load intel/mkl/64/2017/0.098
module load intel/mpi/64/2017/0.098

cd /gpfs/projects/samples/intel_mpi_hello/
mpiicc mpi_hello.c -o intel_mpi_hello

mpirun ./intel_mpi_hello
