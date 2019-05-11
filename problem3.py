def all_paths_from_to(graph, source, dest, visited, current_path, all_paths):
    # Mark the current node
    visited[source] = True
    current_path.add(source)

    # Check if we have reached the destination
    if dest == source:
        all_paths.add(current_path.copy())
    else:
        # Go through every neighbor
        current = graph.adj[source].head
        while current is not None:
            if not visited[current.data]:
                # Store it to the path
                all_paths_from_to(graph, current.data, dest, visited, current_path, all_paths)
                # Remove it to get it ready for the next path
            current = current.next
    # We need to set it back as unvisited so it can be checked for a next path
    visited[source] = False
    current_path.remove_last()

# All paths from a source node
def all_paths_from(graph, source):
    # Get all its edges
    current = graph.adj[source].head

    result = Bag()
    visited = [False]*(graph.V)
    while current is not None:
        # We ignore loopbacks
        all_paths_from_to(graph, source, current.data, visited, Bag(), result)
        current = current.next
    return result
