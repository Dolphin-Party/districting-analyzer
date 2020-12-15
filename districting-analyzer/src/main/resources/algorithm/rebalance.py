import random

from spanning_tree import SpanningTree
from precinct_graph import PrecinctGraph
from precinct_subgraph import PrecinctSubgraph


def rebalance_district(districts: PrecinctGraph, target_pop: int, pop_variance: int, target_compactness: float):
    #  pick 1 district at random
    selected_district: PrecinctSubgraph = random.choice(list(districts.subgraphs))
    dist_pop_diff = abs(selected_district.population - target_pop)
    dist_compactness = selected_district.compactness

    dist_meets_pop = dist_pop_diff <= pop_variance
    dist_meets_compactness = dist_compactness >= target_compactness

    #  pick neighbor at random
    selected_neighbor: PrecinctSubgraph = random.choice(list(selected_district.neighbors))
    neighbor_pop_diff = abs(selected_neighbor.population - target_pop)
    neighbor_compactness = selected_neighbor.compactness
    neighbor_meets_pop = neighbor_pop_diff <= pop_variance
    neighbor_meets_compactness = neighbor_compactness >= target_compactness
    #  check whether districts meet parameters

    #  generate ST of merged subgraph
    st = SpanningTree(selected_district, selected_neighbor)
    #  make cut (must choose method)
    #  if both districts meet parameters, just find cut that also meets parameters
    if dist_meets_pop and dist_meets_compactness and neighbor_meets_pop and neighbor_meets_compactness:
        new_dists_meet_params = False
        for e in random.sample(st.edges, len(st.edges)):
            new_dist1, new_dist2 = st.cut(e)
            new_dist1_meets_pop = abs(new_dist1.population - target_pop) <= pop_variance
            new_dist1_meets_compactness = new_dist1.compactness >= target_compactness
            new_dist2_meets_pop = abs(new_dist2.population - target_pop) <= pop_variance
            new_dist2_meets_compactness = new_dist2.compactness >= target_compactness

            new_dists_meet_params = new_dist1_meets_pop and new_dist1_meets_compactness \
                                    and new_dist2_meets_pop and new_dist2_meets_compactness
            if new_dists_meet_params:
                break

        if new_dists_meet_params:
            for neighbor in selected_district.neighbors:
                neighbor.neighbors.remove(selected_district)
            for neighbor in selected_neighbor.neighbors:
                neighbor.neighbors.remove(selected_neighbor)
            # compute neighbors
            neighbor_nodes = set()
            for node in new_dist1.nodes:
                neighbor_nodes = neighbor_nodes | (node.adjacent_precincts - new_dist1.nodes)
            candidate_neighbors = selected_district.neighbors | selected_neighbor.neighbors
            for neighbor in candidate_neighbors:
                if len(neighbor_nodes.intersection(neighbor.nodes)):
                    neighbor.neighbors.add(new_dist1)
                    new_dist1.neighbors.add(neighbor)

            neighbor_nodes = set()

            for node in new_dist2.nodes:
                neighbor_nodes = neighbor_nodes | (node.adjacent_precincts - new_dist2.nodes)

            for neighbor in candidate_neighbors:
                if len(neighbor_nodes.intersection(neighbor.nodes)):
                    neighbor.neighbors.add(new_dist2)
                    new_dist2.neighbors.add(neighbor)

            new_dist1.neighbors.add(new_dist2)
            new_dist2.neighbors.add(new_dist1)

            districts.subgraphs.remove(selected_neighbor)
            districts.subgraphs.remove(selected_district)
            districts.subgraphs.add(new_dist1)
            districts.subgraphs.add(new_dist2)
    else:
        new_dists_beat_params = False
        #  if at least 1 district doesn't meet parameter, find cut that improves both scores
        #       |dist_pop - target_pop| < |old_pop - target_pop|
        #       dist_compactness > old_compactness
        for e in random.sample(st.edges, len(st.edges)):
            new_dist1, new_dist2 = st.cut(e)

            new_dist1_pop_diff = abs(new_dist1.population - target_pop)
            new_dist1_compactness = new_dist1.compactness
            new_dist2_pop_diff = abs(new_dist2.population - target_pop)
            new_dist2_compactness = new_dist2.compactness

            new_dists_beat_params = (new_dist1_pop_diff < dist_pop_diff
                                     and new_dist1_compactness > dist_compactness
                                     and new_dist2_pop_diff < neighbor_pop_diff
                                     and new_dist2_compactness > neighbor_compactness)
            new_dists_beat_params = new_dists_beat_params or (new_dist2_pop_diff < dist_pop_diff
                                                              and new_dist2_compactness > dist_compactness
                                                              and new_dist1_pop_diff < neighbor_pop_diff
                                                              and new_dist1_compactness > neighbor_compactness)
            if new_dists_beat_params:
                break

        if new_dists_beat_params:
            for neighbor in selected_district.neighbors:
                neighbor.neighbors.remove(selected_district)
            for neighbor in selected_neighbor.neighbors:
                neighbor.neighbors.remove(selected_neighbor)
            # compute neighbors
            neighbor_nodes = set()
            for node in new_dist1.nodes:
                neighbor_nodes = neighbor_nodes | (node.adjacent_precincts - new_dist1.nodes)
            candidate_neighbors = selected_district.neighbors | selected_neighbor.neighbors
            for neighbor in candidate_neighbors:
                if len(neighbor_nodes.intersection(neighbor.nodes)):
                    neighbor.neighbors.add(new_dist1)
                    new_dist1.neighbors.add(neighbor)

            neighbor_nodes = set()

            for node in new_dist2.nodes:
                neighbor_nodes = neighbor_nodes | (node.adjacent_precincts - new_dist2.nodes)

            for neighbor in candidate_neighbors:
                if len(neighbor_nodes.intersection(neighbor.nodes)):
                    neighbor.neighbors.add(new_dist2)
                    new_dist2.neighbors.add(neighbor)

            new_dist1.neighbors.add(new_dist2)
            new_dist2.neighbors.add(new_dist1)

            districts.subgraphs.remove(selected_neighbor)
            districts.subgraphs.remove(selected_district)
            districts.subgraphs.add(new_dist1)
            districts.subgraphs.add(new_dist2)
    #  Optional: if 1 doesn't meet parameters and the other does, find cut that improves score for 1 and stays within
    #            parameter for the other
