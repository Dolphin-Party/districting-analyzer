#!/usr/bin/env bash

#SBATCH --job-name=test_ssh_submit
#SBATCH --output=test_ssh_submit-latest.log
#SBATCH --ntasks-per-node=24
#SBATCH --nodes=2
#SBATCH --time=00:05:00
#SBATCH -p short-24core

echo I am using the following nodes: $SLURM_JOB_NODELIST
echo My job ID is: $SLURM_JOB_ID

cat << EOF > py_mpi_hello.py

import os
import time

from mpi4py import MPI

start_time = time.time()

comm = MIP.COMM_WORLD
size = comm.Get_size()
rank = comm.Get_rank()

print("This is node {}".format(rank))

if rank == 0:
	print("We have {} nodes in our cluster.".format(size))
	data = [x for x in range(size)]
	print('We will be scattering files across nodes:', data)
else:
	data = None

data = comm.scatter(data, root=0)
print('rank', rank, 'has data:', data)

if rank != 0: 
	command = "python hello_world.py --node {}".format(data)
	print("Running command:", command)
	print('Starting multiproc')
	os.system("python py_multiproc.py")

comm.barrier()
print("Completed in {:.2f}s for rank {}:".format(time.time() - start_time, rank))
EOF

mpirun -np 2 python py_mpi_hello.py
