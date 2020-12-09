from precinct_graph import PrecinctGraph
from rebalance import rebalance_district


def generate_districting(districting: PrecinctGraph, iterations: int, target_pop: int,
                         pop_variance: int, target_compactness: float):
    for _ in range(iterations):
        # select two districts at random
        rebalance_district(districting, target_pop, pop_variance, target_compactness)

    return districting
