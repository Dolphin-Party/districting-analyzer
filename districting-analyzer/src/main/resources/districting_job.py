from precinct_graph import PrecinctGraph
from rebalance import rebalance_district


def generate_districting(districting: PrecinctGraph, iterations: int):
    for _ in range(iterations):
        # select two districts at random
        rebalance_district(districting)

    return districting
