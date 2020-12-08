import multiprocessing as mp
from multiprocessing import Pool

def f(x):
    if x == 0:
        return 1
    if x == 1:
        return 2

    return f(x-1) + f(x-2)

if __name__ == '__main__':
    pool_size = mp.cpu_count()
    print("NUM CPUs", pool_size)

    with Pool(processes=pool_size) as pool:
        for i in pool.imap_unordered(f, range(15)):
            print(i)

    print("Now the pool is closed and no longer available")
