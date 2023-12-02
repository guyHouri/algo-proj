package com.example.enums;

public enum RunType {
    BFS("BFS"),
    ASTAR_DIJKSTRA("AStar Dijkstra"),
    ASTAR_MANHATTEN("AStar Manhatten"),
    ASTAR_INCOMPATTIBLE("AStar Incompattible");

    private final String type;

    RunType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}