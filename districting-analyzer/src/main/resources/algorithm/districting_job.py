import json
from precinct_graph import PrecinctGraph
from rebalance import rebalance_district


def generate_districting(districting: PrecinctGraph, iterations: int, target_pop: int,
                         pop_variance: int, target_compactness: float, out_dir: str, iteration: int):
    for _ in range(iterations):
        # select two districts at random
        rebalance_district(districting, target_pop, pop_variance, target_compactness)
    loc = f'{out_dir}/districting{iteration}.json'
    with open(loc, 'w+') as f:
        res = []

        for district in districting.subgraphs:
            res.append([p.id for p in district.nodes])
        json.dump(res, f)
    return loc
