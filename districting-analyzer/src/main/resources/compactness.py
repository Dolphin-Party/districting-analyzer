from precinct_graph import PrecinctSubgraph
from shapely.ops import unary_union


def compactness_score(district: PrecinctSubgraph):
    shapes = [node.shape for node in district.nodes]

    district_shape = unary_union(shapes)
    district_hull = district_shape.convex_hull

    return district_shape.area / district_hull.area


#  compactness: [0,1]
#  dist_pop / state_pop = pop_percent: [0,1]

#  target_compactness: [0,1]
#  target_pop_percent: [0,1]

#  target_pop
#  target_compactness - dist_compactness
