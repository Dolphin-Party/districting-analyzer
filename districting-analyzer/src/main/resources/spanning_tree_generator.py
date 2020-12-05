#written by Kamile

import random

def generate_spanning_tree(precincts: List[PrecinctNode]):
    spanning_tree = []
    root = random.choice(precincts)
    while len(root.adjacent_precincts)>0:
        root = random.choice(root.adjacent_precincts)
        spanning_tree.append(root)
    return spanning_tree
