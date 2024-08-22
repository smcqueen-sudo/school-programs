package graph:

	import scala.collection.mutable
	import scala.collection.mutable.Stack

	/**
	 * Branch and bound algorithm that uses a default heuristic function
	 *
	 * @param graph Graph to use
	 * @tparam T Type of graph
	 * @return The result of the TSP algorithm
	 */
	def branchBoundTSP[T](graph:Graph[T]):Seq[Edge[T]] =
	{
		// Pass it off to our other method
		branchBoundTSP(graph, defaultHeur)
	}


	/**
	 * Branch and bound algorithm that takes in a heuristic function from the user
	 *
	 * @param graph Graph to use
	 * @param heur Heuristic function to use
	 * @tparam T Type of graph
	 * @return The result of the TSP algorithm
	 */
	def branchBoundTSP[T](graph:Graph[T], heur:(Graph[T], Seq[T]) => Long):Seq[Edge[T]] =
	{
		// Initialize starting variables
		var stack = mutable.Stack[Seq[T]]()
		var best: Seq[T] = null
		var depot = Seq(graph.getVertices.head)
		stack.push(depot)
		var minCost: Long = Long.MaxValue

		while (stack.nonEmpty)
		{
			var current = stack.pop()
			// If the best case scenario is better then the current best
			if ((current.size <= 1  && heur(graph, current) < minCost) ||
				pathLength(graph, current).get + heur(graph, current) < minCost)
			{
				// Check if the tour is done
				if (current.size == graph.getVertices.size + 1)
				{
					best = current
					minCost = pathLength(graph, current).get
				}
				else
				{
					// If tour is not done, then we add a all unvisited verticies onto new tours
					if (current.size < graph.getVertices.size)
					{
						for (vertex <- graph.getVertices.filterNot(current.contains(_)))
						{
							stack.push(current.appended(vertex))
						}
					}
					else
					{
						stack.push(current.appended(depot.head))
					}
				}
			}


		}

		// Convert best into a sequence of edges
		bestTour(graph, best)
	}

	/**
	 * Default Heuristic function if the user does not provide one
	 *
	 * @param graph Graph to use
	 * @param tour Tour to test
	 * @tparam T Type of graph
	 * @return A best case scenario for the final tour length
	 */
	def defaultHeur[T](graph: Graph[T], tour: Seq[T]): Long =
	{
		val unvisitedVerticies = graph.getVertices.filterNot(tour.contains(_))
		val edges = graph.getEdges
		var bestSolution = 0

		// For each unvisited vertex
		for (vertex <- unvisitedVerticies)
		{
			// Find the shortest connected edge to the vertex
			val connectedEdges = edges.filter(x => x.source == vertex || x.destination == vertex)
			var bestEdge = connectedEdges.head
			var best = connectedEdges.head.weight

			for(edge <- connectedEdges)
			{
				if (best > edge.weight)
				{
					bestEdge = edge
					best = edge.weight
				}
			}

			bestSolution += best
		}

		bestSolution
	}

	/**
	 * Convert a Seq of T to a Seq of Edges
	 *
	 * @param graph Graph to use
	 * @param tour Tour to convert
	 * @tparam T Type of graph
	 * @return Seq of edges
	 */
	def bestTour[T](graph: Graph[T], tour: Seq[T]): Seq[Edge[T]] = {
		val finalTour: mutable.Queue[Edge[T]] = mutable.Queue()

		// Get edges from best
		for (i <- 0 until tour.length - 1) {
			finalTour.addOne(graph.getEdge(tour(i), tour(i + 1)).get)
		}

		finalTour.toSeq
	}

	