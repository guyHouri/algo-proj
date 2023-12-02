package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.example.enums.RunType;

public class BFS {
    public static Result run(GameBoard startBoard) {
        Result result = new Result(RunType.BFS);
        int visitedCounter = 0;

        if (startBoard.isPuzzleSolved()) {
            result.createFinalResult(Collections.singletonList(startBoard), visitedCounter);
            return result;
        }

        Queue<GameBoard> queue = new LinkedList<>();
        queue.add(startBoard);

        Map<String, GameBoard> predecessors = new HashMap<>();
        predecessors.put(startBoard.getId(), null);

        while (!queue.isEmpty()) {
            GameBoard currentBoard = queue.poll();

            if (currentBoard.isPuzzleSolved()) {
                result.createFinalResult(createBFSPath(predecessors, currentBoard), visitedCounter);
                return result;
            }

            visitedCounter++;

            for (GameBoard neighbor : currentBoard.getNeighborBoards()) {
                if (!predecessors.containsKey(neighbor.getId())) {
                    queue.add(neighbor);
                    predecessors.put(neighbor.getId(), currentBoard);
                }
            }
        }

        result.createFinalResult(new ArrayList<>(), visitedCounter);
        return result; // Puzzle not solved
    }

    public static List<GameBoard> createBFSPath(Map<String, GameBoard> predecessors, GameBoard end) {
        LinkedList<GameBoard> path = new LinkedList<>();
        GameBoard endGameBoardPointer = end;

        while (endGameBoardPointer != null) {
            path.addFirst(endGameBoardPointer);
            endGameBoardPointer = predecessors.get(endGameBoardPointer.getId());
        }

        return path;
    }
}
