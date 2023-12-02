package com.example;

import java.util.ArrayList;

import com.example.enums.RunType;

public class Tests {
    public static void basic(int boardSize, int shuffleCount) {
        GameBoard gameBoard = new GameBoard(boardSize, shuffleCount);

        Result BFSResult = BFS.run(gameBoard);
        BFSResult.printResults();

        Result AStarDijkstraResult = AStar.run(gameBoard, RunType.ASTAR_DIJKSTRA);
        AStarDijkstraResult.printResults();

        Result AStarManhattenResult = AStar.run(gameBoard, RunType.ASTAR_MANHATTEN);
        AStarManhattenResult.printResults();

        Result AStarIncompatibleResult = AStar.run(gameBoard, RunType.ASTAR_INCOMPATTIBLE);
        AStarIncompatibleResult.printResults();
        System.out.println("---------------------------------\n");
    }

    public static void avg(int boardSize, int shuffleCount, int totalTests) {

        Result totalBFSResults = new Result(RunType.BFS);
        Result totalAStarDijkstraResults = new Result(RunType.ASTAR_DIJKSTRA);
        Result totalAStarManhattanResults = new Result(RunType.ASTAR_MANHATTEN);
        Result totalAStarIncompatibleHeuristicResults = new Result(RunType.ASTAR_INCOMPATTIBLE);

        totalBFSResults.createFinalResult(new ArrayList<>(), 0);
        totalAStarDijkstraResults.createFinalResult(new ArrayList<>(), 0);
        totalAStarManhattanResults.createFinalResult(new ArrayList<>(), 0);
        totalAStarIncompatibleHeuristicResults.createFinalResult(new ArrayList<>(), 0);

        for (int testnum = 0; testnum < totalTests; testnum++) {
            System.out.println("testNum " + testnum);
            GameBoard testBoard = new GameBoard(boardSize, shuffleCount);

            Result currBFSRes = BFS.run(testBoard);
            Result currDijkstraRes = AStar.run(testBoard, RunType.ASTAR_DIJKSTRA);
            Result currManhattanRes = AStar.run(testBoard, RunType.ASTAR_MANHATTEN);
            Result currIncompatibleHeuristicRes = AStar.run(testBoard, RunType.ASTAR_INCOMPATTIBLE);

            totalBFSResults.add(currBFSRes);
            totalAStarDijkstraResults.add(currDijkstraRes);
            totalAStarManhattanResults.add(currManhattanRes);
            totalAStarIncompatibleHeuristicResults.add(currIncompatibleHeuristicRes);
        }

        totalBFSResults.printResults();
        totalAStarDijkstraResults.printResults();
        totalAStarManhattanResults.printResults();
        totalAStarIncompatibleHeuristicResults.printResults();

        System.out.println("*******************");

        totalBFSResults.printAvgResult(totalTests);
        totalAStarDijkstraResults.printAvgResult(totalTests);
        totalAStarManhattanResults.printAvgResult(totalTests);
        totalAStarIncompatibleHeuristicResults.printAvgResult(totalTests);

    }
}
