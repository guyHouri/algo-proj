package com.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import com.example.enums.RunType;

public class AStar {
    private static class Node implements Comparable<Node> {
        GameBoard board;
        int moves;
        Node previous;
        int priority; // This is 'f' in A*

        public Node(GameBoard board, RunType runType, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = moves + AStar.heuristic(board, runType); // moves g(x) + herusitc h(x) = f(x)
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    public static Result run(GameBoard initial, RunType runType) {
        Result result = new Result(runType);
        int visitedCounter = 0;

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        openSet.add(new Node(initial, runType, 0, null));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.board.isPuzzleSolved()) {
                result.createFinalResult(buildPath(current), visitedCounter);
                return result;
            }

            visited.add(current.board.getId());
            visitedCounter++;

            for (GameBoard neighbor : current.board.getNeighborBoards()) {
                if (!visited.contains(neighbor.getId())) {
                    openSet.add(new Node(neighbor, runType, current.moves + 1, current));

                }
            }
        }

        result.createFinalResult(Collections.emptyList(), visitedCounter);
        return result; // Return an empty list if no solution is found
    }

    private static int heuristic(GameBoard board, RunType runType) {
        if (runType == RunType.ASTAR_DIJKSTRA) {
            return 0;
        } else if (runType == RunType.ASTAR_MANHATTEN) {
            return manhattanDistance(board);
        } else {
            return incompatibleHeuristic(board);
        }
    }

    private static int manhattanDistance(GameBoard gameBoard) {
        int distance = 0;
        int size = gameBoard.getSize();
        Integer[][] board = gameBoard.getBoard();

        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                if (board[col][row] != null) {
                    int targetRow = (board[col][row] - 1) / size;
                    int targetCol = (board[col][row] - 1) % size;
                    distance += Math.abs(col - targetRow) + Math.abs(row - targetCol);
                } else {
                    distance += Math.abs(size - 1 - col) + Math.abs(size - 1 - row);
                }
            }
        }
        return distance;
    }

    private static int worstManhattenValue(GameBoard gameBoard) {
        int singleWorstMove = (gameBoard.getSize() - 1) * 2; // 4X4 worst move is 3+3 = moving 6 tiles
        int puzzleSize = gameBoard.getSize() * gameBoard.getSize() - 1; // 4X4 is 15 puzzle
        return singleWorstMove * puzzleSize; // for 15 puzzle worst manhatten is 15*6 = 90
    }

    private static int incompatibleHeuristic(GameBoard gameBoard) {
        // Adding a value to the Manhattan distance consistently overestimates the cost
        // for every tile.
        // This violates the admissibility property of heuristics.
        // It ensures that the heuristic is always greater than the true cost.
        // return new Random().nextInt(worstManhattenValue(gameBoard)) + 10 +
        // manhattanDistance(gameBoard);
        return manhattanDistance(gameBoard) + 100;

    }

    private static List<GameBoard> buildPath(Node node) {
        LinkedList<GameBoard> path = new LinkedList<>();

        while (node != null) {
            path.addFirst(node.board);
            node = node.previous;
        }

        return path;
    }
}
