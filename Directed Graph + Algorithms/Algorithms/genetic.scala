package graph:
	import scala.collection.mutable
	import scala.util.Random
	import scala.collection.mutable.Queue

	/**
	 * Finds the TSP using default values
	 *
	 * @param graph Graph to use
	 * @tparam T Type of graph
	 * @return Seq of edges of the TSP solution
	 */
	def geneticTSP[T](graph:Graph[T]):Seq[Edge[T]] =
	{
		// Use default values, 100 pop size, 2% inversion probability, and 1000 max iterations
		geneticTSP(graph, 100, 0.02f, 1000)
	}

	/**
	 * Finds the TSP using non-default values
	 *
	 * @param graph Graph to use
	 * @param  popSize Size of the population
	 * @param inversionProb Probability to invert
	 * @param maxIters Max iterations before termination
	 * @tparam T Type of graph
	 * @return Seq of edges of the TSP solution
	 */
	def geneticTSP[T](graph:Graph[T], 
							popSize:Int, 
							inversionProb:Float, 
							maxIters:Int):Seq[Edge[T]] =
	{
		// Generate population before passing it off to the other method
		var pop = new mutable.Queue[Seq[T]]
		for (_ <- 0 to popSize)
		{
			// Randomly select tours
			var newTour = new mutable.Queue[T]
			var unaddedVerticies = graph.getVertices.toSeq

			while (unaddedVerticies.nonEmpty)
			{
				val vertexToAdd = unaddedVerticies(Random.between(0, unaddedVerticies.size))
				newTour.enqueue(vertexToAdd)
				unaddedVerticies = unaddedVerticies.filterNot(_ == vertexToAdd)
			}

			pop.addOne(newTour.toSeq)
		}

		geneticTSP(graph, pop.toSeq, inversionProb, maxIters)
	}


	/**
	 * Reverse a section of a sequence
	 *
	 * @param seq Sequence to reserve
	 * @param i Lower bound
	 * @param k Upper bound
	 * @tparam T Type of the sequence
	 * @return Sequence reversed
	 */
	def reverse[T](seq: Seq[T], i: Int, k: Int): Seq[T] =
	{
		var lower = i
		var upper = k

		if (upper < lower)
		{
			lower = k
			upper = i
		}

		// Check and make sure we are good to swap
		if (!(lower <= 0 || upper >= seq.length - 1 || lower >= upper)) {
			val start = seq.take(1)
			val last = seq.takeRight(1)
			// Slice tour into 3 parts
			val prefix = seq.slice(1, lower)
			val mid = seq.slice(lower, upper)
			val end = seq.slice(upper, seq.length - 1)

			// Generate new tour
			start ++ prefix ++ mid.reverse ++ end ++ last
		}
		else {
			seq
		}

	}

	/**
	 * Find the length of the tour
	 *
	 * @param graph Graph to use
	 * @param tour Tour to find the length of
	 * @tparam T Type of the graph
	 * @return Length of your
	 */
	def tourLength[T](graph:Graph[T], tour:Seq[T]): Int =
	{
		var length = 0

		// Calculate tour length
		for (i <- 0 until tour.length - 1)
		{
			length += graph.getEdgeWeight(tour(i), tour(i + 1)).get
		}

		length
	}

	/**
	 * Calculate the best tour based on the length method
	 *
	 * @param graph Graph to use
	 * @param tours Tours to compare
	 * @tparam T Type of graph
	 * @return Best tour from the Seq
	 */
	def best[T](graph:Graph[T], tours: mutable.Queue[Seq[T]]): Seq[Edge[T]] =
	{
		var best = tours.head
		var bestLength = tourLength(graph, best)

		// Iterate through all tours
		for (tour <- tours)
		{
			val newTourLength = tourLength(graph, tour)
			if (newTourLength < bestLength)
			{
				best = tour
				bestLength = newTourLength
			}
		}

		val finalTour: mutable.Queue[Edge[T]] = mutable.Queue()

		// Get edges from best
		for (i <- 0 until best.length - 1)
		{
			finalTour.addOne(graph.getEdge(best(i), best(i + 1)).get)
		}

		finalTour.toSeq
	}

	/**
	 * Finds the TSP using non-default values as well as a specified population
	 *
	 * @param graph         Graph to use
	 * @param pop           Specified population
	 * @param inversionProb Probability to invert
	 * @param maxIters      Max iterations before termination
	 * @tparam T Type of graph
	 * @return Seq of edges of the TSP solution
	 */
	def geneticTSP[T](graph: Graph[T],
										pop: Seq[Seq[T]],
										inversionProb: Float,
										maxIters: Int): Seq[Edge[T]] = {
		var mutablePop: mutable.Queue[Seq[T]] = mutable.Queue.empty ++ pop
		for (_ <- 0 to maxIters) {
			for (tour <- mutablePop) {
				// Initialize values
				var newTour = tour
				val startCityIndex = Random.between(0, tour.length)
				var startCity = newTour(startCityIndex)
				var repeat = true
				var endCity = startCity
				while (repeat) {
					if (Random.between(0.0, 1.0) < inversionProb) {
						// Mutate by choosing a random end city
						val filteredTour = tour.filterNot(_ == startCity)
						endCity = filteredTour(Random.between(0, filteredTour.length))
					}
					else {
						// Find end city based on a randomly selected tour
						val filteredPop = mutablePop.filterNot(_ == tour)
						val otherTour = filteredPop(Random.between(0, filteredPop.length))
						if (otherTour.indexOf(startCity) == otherTour.length - 1) {
							endCity = otherTour(1)
						}
						else {
							endCity = otherTour(otherTour.indexOf(startCity) + 1)
						}
					}
					// If the endCiy is adjacent
					if ((newTour.indexOf(endCity) != 0 && startCity == newTour(newTour.indexOf(endCity) - 1))
						|| (newTour.indexOf(endCity) != newTour.length - 1 && startCity == newTour(newTour.indexOf(endCity) + 1))) {
						repeat = false
					}
					else {
						// Generate new tour
						newTour = reverse(newTour, startCityIndex, newTour.indexOf(endCity))
						startCity = endCity
					}
				}
				// If the tour is better, replace it
				if (tourLength(graph, newTour) < tourLength(graph, tour)) {
					mutablePop(mutablePop.indexOf(tour)) = newTour
				}
			}
		}
		best(graph, mutablePop)
	}
