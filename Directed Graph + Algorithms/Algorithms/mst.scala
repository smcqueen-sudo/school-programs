package graph:

	import scala.collection.mutable
	import scala.collection.mutable.Map

	/**
	 * Finds the minimum spanning tree based on a graph
	 *
	 * @param graph Graph to find mst of
	 * @tparam T Vertex type
	 * @return The MST
	 */
	def minimumSpanningTree[T](graph:Graph[T]):Option[Graph[T]] =
	{
		// Define data structures
		val dist = mutable.Map[T, Int]().withDefaultValue(Int.MaxValue)
		val parent = mutable.Map[T, Option[T]]()
		var visited = Set[T]()
		var disconnected = false

		// Make sure graph is not empty
		if (graph.getVertices.nonEmpty)
		{
			// Create our new graph
			val tree = Graph[T](graph.isDirected)

			// Add start the dist and parent
			dist.addOne(graph.getVertices.head, 0)
			parent.addOne(graph.getVertices.head, None)

			// Add all verticies to the MST
			graph.getVertices.foreach(v => tree.addVertex(v))


			// Iterate through graph
			while (visited.size < graph.getVertices.size && !disconnected)
			{
				// Get the vertex with the smallest distance, that is not visited. if none is found we are disconnected
				val current = dist.view.filterKeys(!visited.contains(_)).minByOption(_._2).map(_._1)

				current match
				{
					// If we have some vertex
					case Some(vertex) =>
						// Add vertex to visited
						visited += vertex
						// Add edge (if it exists)
						// TODO adding edges twice
						parent(vertex).foreach(p => tree.addEdge(p, vertex, dist(vertex)))
						// for all adjacent
						for (adjacent <- graph.getAdjacent(vertex))
						{
							// Get weight if it exists
							val weight = graph.getEdgeWeight(vertex, adjacent).getOrElse(Int.MaxValue)
							// If the vertex is not visited, and the weight is less then the current weight added
							if (!visited.contains(adjacent) && weight < dist(adjacent))
							{
								// Update distance
								dist(adjacent) = weight
								parent(adjacent) = Some(vertex)
							}
						}

					// If none we are disconnected
					case None => disconnected = true
				}


			}

			// If disconnected, return none
			if (disconnected)
			{
				None
			}
			else
			{
				Some(tree)
			}
		}
		else
		{
			None
		}


	}
