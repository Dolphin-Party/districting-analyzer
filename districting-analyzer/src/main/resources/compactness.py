from typing import List
from helpers import PrecinctSubgraph
from shapely.ops import unary_union


def compactness_score(district: PrecinctSubgraph):
    shapes = [node.shape for node in district.nodes]

    district_shape = unary_union(shapes)
    district_hull = district_shape.convex_hull

    return district_shape.area / district_hull.area
