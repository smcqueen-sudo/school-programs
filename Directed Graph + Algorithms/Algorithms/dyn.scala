package graph:

  import scala.collection.mutable
  import scala.collection.mutable.Map
  import scala.collection.mutable.Set
  import scala.collection.mutable.Stack

  /**
   * Dynamic programming solution to TSP
   *
   * @param graph Graph to solve
   * @tparam T The type that is stored in the graphs
   * @return The final tour
   */
  def dynamicTSP[T](graph: Graph[T]): Seq[Edge[T]] =
  {
    // Initialize data structures
    var dist = mutable.Map[(mutable.Set[T], T), Int]()
    var parent = mutable.Map[(mutable.Set[T], T), T]()
    val depot = graph.getVertices.head
    val ends = mutable.Set() ++ graph.getVertices.filterNot(_ == depot)

    // Add all other verticies to dist and parent
    for (vertex <- ends)
    {
      dist.addOne((mutable.Set(vertex), vertex), graph.getEdgeWeight(depot, vertex).get)
      parent.addOne((mutable.Set(vertex), vertex), depot)
    }

    // Iterate through all subset of all different sizes
    for (sub_size <- 2 until ends.size + 1)
    {
      for (hist <- ends.subsets(sub_size))
      {
        // Create a copy of the subset that is mutable
        val mutable_hist = mutable.Set() ++= hist
        for (k <- hist)
        {
          // Locate the minimum distance to the current vertex
          var min_distance = Int.MaxValue
          var parent_vertex = hist.head
          for (x <- hist if x != k)
          {
            // Calculate the cost
            mutable_hist.remove(k)
            val cost = dist(mutable_hist, x) + graph.getEdgeWeight(x, k).get
            mutable_hist.addOne(k)

            // Check if it is smaller, if so then override the previous best
            if (cost < min_distance)
            {
              min_distance = cost
              parent_vertex = x
            }
          }

          // Add the parent and the min distance
          dist.addOne((mutable.Set() ++= hist += k, k), min_distance)
          parent.addOne((mutable.Set() ++= hist += k, k), parent_vertex)
        }
      }
    }

    // Used to calculate optimal starting point
    var optimal = depot
    var min = Int.MaxValue

    // Calculate optimal starting point
    for (x <- ends)
    {
      val current_min = dist(ends, x) + graph.getEdgeWeight(x, depot).get
      if (current_min < min)
      {
        optimal = x
        min = current_min
      }
    }

    // Used to calculate optimal tour
    val solution = mutable.Stack[Edge[T]](graph.getEdge(optimal, depot).get)
    val visited = mutable.Set() ++ graph.getVertices.filterNot(vertex => vertex == depot)
    var current_vertex = parent((visited, optimal))
    var prev_vertex = optimal

    visited -= prev_vertex
    solution.push(graph.getEdge(current_vertex, prev_vertex).get)

    // Go backwards using the parent map
    while (visited.nonEmpty)
    {
      prev_vertex = current_vertex
      current_vertex = parent((visited, current_vertex))
      visited -= prev_vertex
      solution.push(graph.getEdge(current_vertex, prev_vertex).get)
    }

    solution.toSeq
  }
