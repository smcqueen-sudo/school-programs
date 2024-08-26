# Traveling Salesman Problem Solutions

## Overview

This repository contains a detailed exploration of various algorithms used to solve the Traveling Salesman Problem (TSP). Each algorithm has been implemented and tested on multiple graph instances to compare their effectiveness and efficiency. The primary goal is to evaluate the performance of each method in terms of solution quality and computational cost.

## Algorithms Description

1. **2-OPT Algorithm**: A local search method that iteratively improves the solution by swapping two edges and checking if the new route offers a shorter path.

2. **Held-Karp Algorithm**: An exact method using dynamic programming to solve the TSP. It is very effective for smaller graphs but scales exponentially with the number of nodes.

3. **Genetic Algorithm**: A heuristic search algorithm based on the process of natural selection. It uses a technique called mutation to generate solutions.

4. **Branch and Bound**: An exact optimization method that systematically enumerates candidate solutions by means of state space search.

## File Descriptions

- **Table.md**: Contains a detailed table comparing the results of each algorithm across various graph instances. You can view the performance outcomes and computation times to analyze which algorithm performs best under different conditions.

## Viewing the Results

The results of the algorithm comparisons are available in the `Table.md` file in this repository. Please open `Table.md` to see a neatly formatted table with all the data.
