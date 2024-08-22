import java.io.IOException

package graph:

	import scala.io.Source
	/**
	 * Creates a graph based on a CSV file input
	 *
	 * @param isDirected If the graph should be directed
	 * @param fileName Name of the file to get
	 * @throws java.io.IOException Gets thrown if the program detects any error throughout the process
	 * @return A constructed graph based on the csv file
	 */
	@throws(classOf[IOException])
	def fromCSVFile(isDirected:Boolean, fileName:String):Graph[String] =
	{
		var new_graph = Graph[String](isDirected)

		// Open file
		val file_source = try
		{
			Source.fromFile(fileName)
		}
		catch
		{
			case e: Exception => throw new IOException(s"Failed to open file: $fileName", e)
		}


		try
		{
			val lines = file_source.getLines()
			// First line is number of vertices
			val num_verticies : Int = lines.next().toInt
			for (i <- 1 to num_verticies)
			{
				new_graph.addVertex(lines.next())
			}
			
			// The value after the verticies is the number of edges
			val num_edges : Int = lines.next().toInt
			
			for (i <- 1 to num_edges)
			{
				val edge = lines.next()
				val edge_data = edge.split(",")
				if (edge_data.length >= 3)
				{
					val vertex1 = edge_data(0)
					val vertex2 = edge_data(1)
					val weight = edge_data(2).toInt

					new_graph.addEdge(vertex1, vertex2, weight)
				}
				else
				{
					throw new IOException
				}
			}
		}
		catch
		{
			case e: Exception => throw new IOException(s"Error reading file: $fileName", e)
		}

		new_graph
	}
