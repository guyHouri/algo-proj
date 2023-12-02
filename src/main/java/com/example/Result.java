package com.example;

import java.util.List;
import java.util.stream.IntStream;

import com.example.enums.RunType;

public class Result {
    private final long startTime;

    public long executionTime;
    public int visitedCounter;
    public List<GameBoard> path;
    public RunType runType;

    public Result(RunType runType) {
        this.startTime = System.currentTimeMillis();
        this.visitedCounter = 0;
        this.path = null;
        this.runType = runType;
    }

    public void createFinalResult(List<GameBoard> path, int visitedCounter) {
        long endTime = System.currentTimeMillis();
        this.executionTime = endTime - this.startTime;
        this.visitedCounter = visitedCounter;
        this.path = path;
    }

    public void add(Result resultToAdd) {
        this.path.addAll(resultToAdd.getPath());
        this.executionTime += resultToAdd.executionTime;
        this.visitedCounter += resultToAdd.visitedCounter;
    }

    public void printResults() {
        System.out.println("---------------- " + this.runType.getType() + " Results ----------------\n" +
                "millisecoonds : " + this.executionTime + "\n" +
                "visitedCounter: " + this.visitedCounter + "\n" +
                "pathLength    : " + this.path.size());
    }

    public void printResultsAndPath() {
        this.printResults();
        this.printPath();
    }

    public void printAvgResult(int resultsAmount) {
        float avgLength = (float) this.path.size() / resultsAmount;
        float avgMillies = (float) this.executionTime / resultsAmount;
        float avgVisited = (float) this.visitedCounter / resultsAmount;

        System.out.println("---------------- " + this.runType.getType() + " AVG Results ----------------\n" +
                "avg millisecoonds : " + avgMillies + "\n" +
                "avg visitedCounter: " + avgVisited + "\n" +
                "avg pathLength    : " + avgLength);
    }

    public void printPath() {
        IntStream.range(0, this.path.size())
                .forEach(index -> {
                    System.out.println("----------------board number " + (index + 1) + " -----------------");
                    this.path.get(index).printBoard();
                });
    }

    public List<GameBoard> getPath() {
        return this.path;
    }
}
