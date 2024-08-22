package graph:
	import scala.collection.mutable
	import scala.collection.mutable.Map
	/**
	 * A trait for representing directed and undirected graphs
	 */
	trait Graph[T]
	{
		/**
		 * Boolean for if the graph is directed or not
		 */
		def isDirected:Boolean
		
		/**
		 * Get Iterable of vertices
		 *
		 * @return Iterable of vertices
		 */
		def getVertices:Iterable[T]

		/**
		 * Gets the weight of an edge on the graph
		 *
		 * @param source      Source of edge
		 * @param destination Destination of edge
		 * @return Int if the edge exists, None if it does not
		 */
		def getEdgeWeight(source:T, destination:T):Option[Int]

		/**
		 * Find all adjacent verticies to specified vertex
		 *
		 * @param source Specified vertex
		 * @return An iterable of all adjacent verticies
		 */
		def getAdjacent(source:T):Iterable[T]

		/**
		 * Gets specified Edge
		 *
		 * @param source      Source vertex of edge
		 * @param destination Destination vertex of edge
		 * @return Specified Edge Object
		 */
		def getEdge(source:T, destination:T):Option[Edge[T]]

		/**
		 * Gets all edges
		 *
		 * @return Iterable of edge objects
		 */
		def getEdges:Iterable[Edge[T]]

		/**
		 * Checks if an edge exists
		 *
		 * @param source      Source for edge
		 * @param destination Destination for edge
		 * @return True if edge exists
		 */
		def edgeExists(source:T, destination:T):Boolean

		/**
		 * Checks if a vertex exists
		 *
		 * @param vertex Vertex to check
		 * @return True if it exists, false otherwise
		 */
		def vertexExists(vertex:T):Boolean

		/**
		 * Add new vertex to the Graph
		 *
		 * @param vertex Vertex object to add
		 * @return Graph with vertex added
		 * @throws IllegalArgumentException When the vertex already exists
		 */
		@throws(classOf[IllegalArgumentException])
		def addVertex(vertex:T):Graph[T]

		/**
		 * Removes specified vertex
		 *
		 * @param vertex Vertex to remove
		 * @return Iterable of Graph objects
		 * @throws IllegalArgumentException When vertex does not exist
		 */
		@throws(classOf[IllegalArgumentException])
		def removeVertex(vertex:T):Graph[T]

		/**
		 * Adds edge to the graph
		 *
		 * @param source      Source of edge to add
		 * @param destination Destination of edge to add
		 * @param weight      Weight of edge to add
		 * @return Graph with new edge
		 * @throws IllegalArgumentException When edge already exists, or if the specified verticies do not exist
		 */
		@throws(classOf[IllegalArgumentException])
		def addEdge(source:T, destination:T, weight:Int):Graph[T]

		/**
		 * Remove specified edge
		 *
		 * @param source      Source of edge to remove
		 * @param destination Source of destination to remove
		 * @return Graph without specified edge
		 * @throws IllegalArgumentException When edge does not exist
		 */
		@throws(classOf[IllegalArgumentException])
		def removeEdge(source:T, destination:T):Graph[T]

		/**
		 * Converts graph to String
		 *
		 * @return String of graph
		 */
		override def toString:String
	}

	/**
	 * Edge class
	 *
	 * @param source Source of edge
	 * @param destination Destination of edge
	 * @param weight Weight of edge
	 * @tparam T Vertex Typing
	 */
	class Edge[T](val source:T, val destination:T, val weight:Int)
		extends Ordered[Edge[T]]
	{
		/**
		 * Compare edges
		 *
		 * @param other Edge to compare to
		 * @return Integer of the different of the two edges
		 */
		override def compare(other:Edge[T]):Int =
		{
			this.weight - other.weight
		}

		/**
		 * Converts edge to a string
		 *
		 * @return String of edge
		 */
		override def toString: String =
		{
			// String builder since strings are immutable
			val sb = new StringBuilder()
			sb.append("Edge from ")
				.append(source.toString)
				.append(" to ")
				.append(destination.toString)
				.append(" with a cost of ")
				.append(weight)
			sb.toString()
		}

		/**
		 * Test if two edges are equal
		 *
		 * @param obj Other object
		 * @return True if the object is an edge, and it is equal
		 */
		override def equals(obj: Any): Boolean = obj match
		{
			// Make sure that the object is an edge
			case that: Edge[T] =>
				(this.source == that.source && this.destination == that.destination && this.weight == that.weight) ||
					(this.source == that.destination && this.destination == that.source && this.weight == that.weight)
			case _ => false
		}
	}


	/**
	 Serves as a factory function for producing new empty Graphs
	 */
	object Graph
	{
		/*
		Creates and returns a new empty Graph - acts as a constructor
		*/
		def apply[T](isDirected:Boolean):Graph[T] =
		{
			// Use a mutable map to save some time and space later, also makes some methods simpler
			val graph_data = mutable.Map[T, mutable.Map[T, Int]]()
			new GraphImpl[T](isDirected, graph_data)
		}

		/*
		An private implementation of the Graph trait
		*/
		private class GraphImpl[T] (val isDirected:Boolean, var graph_data:mutable.Map[T, mutable.Map[T, Int]])
			extends Graph[T]
		{
			/**
			 * Get Iterable of verticies
			 *
			 * @return Iterable of verticies
			 */
			def getVertices:Iterable[T] =
			{
				graph_data.keys.toSeq
			}

			/**
			 * Checks if an edge exists
			 *
			 * @param source Source for edge
			 * @param destination Destination for edge
			 * @return True if edge exists
			 */
			def edgeExists(source:T, destination:T):Boolean =
			{
				// Use get to get an option, flatMap to ensure it is not a double layered option
				graph_data.get(source).flatMap(_.get(destination)).isDefined
			}

			/**
			 * Gets the weight of an edge on the graph
			 *
			 * @param source Source of edge
			 * @param destination Destination of edge
			 * @return Int if the edge exists, None if it does not
			 */
			def getEdgeWeight(source:T, destination:T):Option[Int] =
			{
				// Flatmap to ensure it is not a double layered option
				graph_data.get(source).flatMap(_.get(destination))
			}

			/**
			 * Find all adjacent verticies to specified vertex
			 *
			 * @param source Specified vertex
			 * @return An iterable of all adjacent verticies
			 */
			def getAdjacent(source:T):Iterable[T] =
			{
				// Get adjacent edges
				graph_data.get(source) match
				{
					case Some(map) => map.keys
					case None => Iterable.empty[T]
				}
			}

			/**
			 * Gets specified Edge
			 *
			 * @param source Source vertex of edge
			 * @param destination Destination vertex of edge
			 * @return Specified Edge Object
			 */
			def getEdge(source:T, destination:T):Option[Edge[T]] =
			{
				// First, we use the get to get the map with all of the connecting edges
				// Then, we map to the destination, and if it exists we construct an Edge based on the weight found
				graph_data.get(source).flatMap {
					map => map.get(destination).map {
						weight => new Edge[T](source, destination, weight)
					}
				}
			}

			/**
			 * Gets all edges
			 *
			 * @return Iterable of edge objects
			 */
			def getEdges:Iterable[Edge[T]] =
			{
				// All edges stored in a set to ensure no duplicates
				var edges : Set[Edge[T]] = Set.empty[Edge[T]]
				for (vertex <- graph_data.keys)
				{
					for (edge <- graph_data(vertex).keys)
					{
						if (isDirected)
						{
							// Add if it is directed since we do not need to check again
							edges += new Edge[T](vertex, edge, graph_data(vertex)(edge))
						}
						else
						{
							// Add if it has not been added already
							if (!edges.contains(Edge[T](edge, vertex, graph_data(edge)(vertex))))
							{
								edges += new Edge[T](vertex, edge, graph_data(vertex)(edge))
							}
						}
					}
				}
				edges
			}

			/**
			 * Checks if a vertex exists
			 *
			 * @param vertex Vertex to check
			 * @return True if it exists, false otherwise
			 */
			def vertexExists(vertex:T):Boolean =
			{
				graph_data.contains(vertex)
			}

			/**
			 * Add new vertex to the Graph
			 *
			 * @param vertex Vertex object to add
			 * @return Graph with vertex added
			 * @throws IllegalArgumentException When the vertex already exists
			 */
			def addVertex(vertex:T):Graph[T] =
			{
				// Check to make sure the input is valid
				if (vertex == null || graph_data.contains(vertex))
				{
					throw new IllegalArgumentException()
				}
				graph_data.addOne(vertex, mutable.Map.empty[T, Int])

				this
			}

			/**
			 * Removes specified vertex
			 *
			 * @param vertex Vertex to remove
			 * @return Iterable of Graph objects
			 * @throws IllegalArgumentException When vertex does not exist
			 */
			def removeVertex(vertex:T):Graph[T] =
			{
				if (vertex == null || !graph_data.contains(vertex))
				{
					throw new IllegalArgumentException()
				}
				graph_data.remove(vertex)


				// Remove edges
				for (verticies <- graph_data.keys)
				{
					if (graph_data(verticies).contains(vertex))
					{
						graph_data(verticies).remove(vertex)
					}
				}

				this
			}

			/**
			 * Adds edge to the graph
			 *
			 * @param source Source of edge to add
			 * @param destination Destination of edge to add
			 * @param weight Weight of edge to add
			 * @return Graph with new edge
			 * @throws IllegalArgumentException When edge already exists, or if the specified verticies do not exist
			 */
			def addEdge(source:T, destination:T, weight:Int):Graph[T] =
			{
				// Make sure verticies exist, and that the edge does not exist already
				if (!graph_data.contains(source) || !graph_data.contains(destination))
				{
					throw new IllegalArgumentException()
				}
				if (graph_data(source).contains(destination))
				{
					throw new IllegalArgumentException()
				}

				// Check for loops
				if (source == destination)
				{
					throw new IllegalArgumentException()
				}

				if (weight <= 0)
				{
					throw new IllegalArgumentException()
				}

				graph_data(source).addOne(destination, weight)

				// Add again if not directed
				if (!isDirected)
				{
					graph_data(destination).addOne(source, weight)
				}

				this
			}

			/**
			 * Remove specified edge
			 *
			 * @param source Source of edge to remove
			 * @param destination Source of destination to remove
			 * @return Graph without specified edge
			 * @throws IllegalArgumentException When edge does not exist
			 */
			def removeEdge(source:T, destination:T):Graph[T] =
			{
				if (!graph_data.contains(source) || !graph_data(source).contains(destination))
				{
					throw new IllegalArgumentException()
				}

				graph_data(source).remove(destination)

				// Remove the other edge if not directed
				if (!isDirected)
				{
					graph_data(destination).remove(source)
				}

				this
			}

			/**
			 * Converts graph to String
			 *
			 * @return String of graph
			 */
			override def toString:String =
			{
				// Use a string builder since strings are immutable
				val sb = new StringBuilder()
				if (graph_data.isEmpty)
				{
					sb.append("Empty Graph")
				}
				else
				{
					// Add verticies
					sb.append("Graph with verticies: \n")
					for (vertex <- graph_data.keys)
					{
						sb.append(vertex.toString + "\n")
					}

					sb.append("Edges: \n")

					// Add edges
					for (source <- graph_data.keys)
					{
						for (destination <- graph_data(source).keys)
						{
							sb.append("Edge from ")
								.append(source.toString)
								.append(" to ")
								.append(destination.toString)
								.append(" with a cost of ")
								.append(graph_data(source)(destination))
								.append("\n")
						}
					}
				}

				sb.toString()
			}
		}
	}