package graph:

	import scala.collection.mutable
	import scala.collection.mutable.Set
	import scala.collection.mutable.Queue

	/**
	 * 2OPT that generates a tour instead of starting with one
	 *
	 * @param graph Graph to find solution
	 * @tparam T Vertex Type
	 * @return 2OPT Solution
	 */
	def greedyTSP[T](graph:Graph[T]):Seq[Edge[T]] =
	{
		// Initialize data structures
		val tour: mutable.Queue[T] = mutable.Queue()
		val verticies = mutable.Stack[T]()
		val visited: mutable.Set[T] = mutable.Set()

		// Push starting vertex
		verticies.push(graph.getVertices.head)
		visited.add(graph.getVertices.head)

		// Generate tour
		while (verticies.nonEmpty)
		{
			val vertex = verticies.pop()
			tour.enqueue(vertex)

			for (adj <- graph.getAdjacent(vertex))
			{
				if (!visited.contains(adj))
				{
					verticies.push(adj)
					visited.add(adj)
				}
			}
		}
		tour.enqueue(graph.getVertices.head)

		// Use other method to do the rest
		greedyTSP(graph, tour.toSeq)
	}

	/**
	 * 2OPT that uses a starting tour
	 *
	 * @param graph Graph to find solution
	 * @param initialTour Inital tour to start with
	 * @tparam T Vertex type
	 * @return 2OPT Solution
	 */
	def greedyTSP[T](graph:Graph[T], initialTour:Seq[T]):Seq[Edge[T]] =
	{
		// Initialize best tour and best distance
		var best = initialTour
		var bestDist = 0

		// Calculate current best distance
		for (i <- 0 until initialTour.length - 1)
		{
			bestDist += graph.getEdgeWeight(initialTour(i), initialTour(i + 1)).get
		}

		// Use a boolean to keep track of if we are still improving or not
		var improvement = true

		while (improvement)
		{
			// Set improvement to false until improvement is found
			improvement = false

			// Create duplicate of the current best tour so we can change without anything getting messed up
			val curBest = best

			// Iterate through current best tour
			for (i <- curBest.indices)
			{
					for (j <- i + 1 to curBest.length)
					{
						// Generate new tour
						val newTour = twoOPTSwap(best, i, j)
						var newDist = 0
						// Calculate distance of new tour
						for (i <- 0 until newTour.length - 1)
						{
							newDist += graph.getEdgeWeight(newTour(i), newTour(i + 1)).get
						}

						// If new tour is better, update best and bestDist
						if (newDist < bestDist)
						{
							bestDist = newDist
							best = newTour
							improvement = true
						}
					}
			}
		}

		// Convert into a set of edges
		val finalTour: mutable.Queue[Edge[T]] = mutable.Queue()

		for (i <- 0 until best.length - 1)
		{
			finalTour.addOne(graph.getEdge(best(i), best(i + 1)).get)
		}

		finalTour.toSeq
	}

	/**
	 * Generates a new tour based on old tour
	 * Used in 2OPT
	 *
	 * @param tour Old tour
	 * @param i First tour divider
	 * @param k second tour divider
	 * @tparam T Vertex Type
	 * @return New Tour
	 */
	def twoOPTSwap[T](tour:Seq[T], i:Int, k:Int):Seq[T] =
	{
		// Check and make sure we are good to swap
		if (!(i <= 0 || k >= tour.length - 1 || i >= k))
		{
			val start = tour.take(1)
			val last = tour.takeRight(1)
			// Slice tour into 3 parts
			val prefix = tour.slice(1, i)
			val mid = tour.slice(i, k)
			val end = tour.slice(k, tour.length - 1)
			
			// Generate new tour
			start ++ prefix ++ mid.reverse ++ end ++ last
		}
		else
		{
			tour
		}
	}

