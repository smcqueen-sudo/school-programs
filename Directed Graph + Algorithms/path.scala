package graph:

	import scala.collection.mutable
	import scala.collection.mutable.Map

	/**
	 * Computes the given path length
	 *
	 * @param graph Graph of path
	 * @param path Actual path in a Seq
	 * @tparam T Type that the graph is made of
	 * @return Long of the path length, None if it is not a path
	*/
	def pathLength[T](graph:Graph[T], path:Seq[T]):Option[Long] =
	{
		// If the path is too short, return None
		if (path.length > 1)
		{
			// Use sliding to get a window of two values to check
			val path_slide = path.sliding(2)
			var length: Long = 0
			var exists = true
			for (edge <- path_slide)
			{
				// Check if the edge exists
				graph.getEdge(edge.head, edge(1)) match
				{
					case Some(edge) => length += edge.weight
					case None => exists = false
				}
			}
			// Make sure the path exists
			if (exists)
			{
				Some(length)
			}
			else
			{
				None
			}
		}
		else
		{
			None
		}

	}
	
	/**
	 * Calculates the shortest path between two verticies on a graph
	 *
	* @param graph Graph to search
	* @param source Source vertex
	* @param destination Destination vertex
	* @tparam T Type of Graph
	* @return The Seq of Edges that make up the path
	*/
	def shortestPathBetween[T](graph:Graph[T], source:T, destination:T):Option[Seq[Edge[T]]] =
	{
		// Initialize our maps
		val dist = mutable.Map[T, Int]()
		val parent = mutable.Map[T, T]()
		var visited = Set[T]()
		var disconnected = false

		// Add the source to the maps
		dist.addOne(source, 0)
		parent.addOne(source, source)

		// Add all other points to the list, the max value representing infinite
		for (point <- graph.getVertices)
		{
			if (point != source)
			{
				dist.addOne(point, Int.MaxValue)
			}
		}

		// Repeat until all vertices have been added
		while(visited.size < graph.getVertices.size && !disconnected)
		{
			var current = dist.head._1
			// Get the lowest distance vertex
			for (edge <- dist.keys)
			{
				if (visited.contains(current) && dist(edge) != Int.MaxValue)
				{
					current = edge
				}
				else if (dist(edge) < dist(current) && !visited.contains(edge))
				{
					current = edge
				}
			}
			visited += current

			// Add all adjacent verticies
			for (other <- graph.getAdjacent(current))
			{
				// Make sure they have not been visited yet
				if (!visited.contains(other))
				{
					// Get the new distance
					val newDist = graph.getEdgeWeight(current, other).get + dist(current)

					// Make sure the new distance is less then the old one
					if (!dist.contains(other) || newDist < dist(other))
					{
						if (dist.contains(other))
						{
							dist.remove(other)
						}
						// Add new distance and update parent
						dist.addOne(other, newDist)
						parent.addOne(other, current)
					}
				}
			}

			// Test to ensure there is another vertex to go to, if not then exit
			disconnected = true
			for (vertex <- dist.keys)
			{
				if (!visited.contains(vertex) && dist(vertex) != Int.MaxValue)
				{
					disconnected = false
				}
			}
		}


		var current = destination
		var path = Set[Edge[T]]()
		var non_existent = false

		// Get targeted path
		while (parent.contains(current) && !(current == source || parent(current) == current))
		{
			path += graph.getEdge(parent(current), current).get
			current = parent(current)
		}

		// If source was not found return None
		if (current != source || non_existent)
		{
			None
		}
		else
		{
			Some(path.toSeq)
		}
		
	}
