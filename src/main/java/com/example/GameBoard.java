package com.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private Integer[][] board;
    private Point nullPoint;
    private int size;
    private String id;

    public static Point[] DIRECTIONS = { new Point(1, 0), new Point(-1, 0), new Point(0, -1), new Point(0, 1) };

    public GameBoard(Integer[][] board) {
        this.size = board.length;
        this.board = new Integer[this.size][this.size];
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                this.board[col][row] = board[col][row];
            }
        }
        this.nullPoint = findNullPoint();
        this.id = generateId(board);
    }

    public GameBoard(int size, int moves) {
        this.size = size;
        this.board = new Integer[size][size];
        initializeBoard();
        createRandomBoard(moves);
        this.id = generateId(this.board);
    }

    public String generateId(Integer[][] board) {
        String id = "";
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                if (nullPoint.getX() != col || nullPoint.getY() != row) {
                    id += board[col][row].toString() + ',';
                } else {
                    id += " ,";
                }
            }
        }

        return id;
    }

    public void initializeBoard() {
        int firstVallue = 1;

        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                this.board[col][row] = firstVallue++;
            }
        }

        this.board[this.size - 1][this.size - 1] = null;
        this.nullPoint = new Point(this.size - 1, this.size - 1);
    }

    public boolean isPuzzleSolved() {
        int startingNumber = 1;

        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                if (col == this.size - 1 && row == this.size - 1) {
                    return this.board[col][row] == null;
                }
                if (this.board[col][row] == null || board[col][row] != startingNumber++) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard() {
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                if (this.board[col][row] == null) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%3d", this.board[col][row]);
                }
            }
            System.out.println();
        }
    }

    public static void printById(String id) {
        String[] boardNumbers = id.split(",");
        int size = (int) Math.sqrt(boardNumbers.length);

        for (int i = 0; i < boardNumbers.length; i++) {
            if (boardNumbers[i].equals("null")) {
                System.out.print("   ");
            } else {
                System.out.printf("%3d", boardNumbers[i]);
            }
            if (i % size == 0) {
                System.out.println();
            }
        }
    }

    public String getId() {
        this.id = generateId(this.board);
        return id;
    }

    public Point findNullPoint() {
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                if (this.board[col][row] == null) {
                    return new Point(col, row);
                }
            }
        }

        return new Point(-1, -1);
    }

    public void createRandomBoard(int moves) {
        Random r = new Random();

        for (int moveCounter = 0; moveCounter < moves; moveCounter++) {
            ArrayList<Point> neighbors = getPointNeighbors(this.nullPoint);
            Point pointToMove = neighbors.get(r.nextInt(neighbors.size()));
            swapPointsOnBoard(this.nullPoint, pointToMove);
            this.nullPoint = pointToMove;
        }
    }

    public void swapPointsOnBoard(Point p1, Point p2) {
        Integer temp = this.board[(int) p1.getX()][(int) p1.getY()];
        this.board[(int) p1.getX()][(int) p1.getY()] = this.board[(int) p2.getX()][(int) p2.getY()];
        this.board[(int) p2.getX()][(int) p2.getY()] = temp;
    }

    public ArrayList<Point> getPointNeighbors(Point point) {
        ArrayList<Point> neighbors = new ArrayList<>();

        for (int directionCounter = 0; directionCounter < DIRECTIONS.length; directionCounter++) {
            int neighborX = (int) (point.getX() + DIRECTIONS[directionCounter].getX());
            int neighborY = (int) (point.getY() + DIRECTIONS[directionCounter].getY());

            if (neighborX >= 0 && neighborX < this.size && neighborY >= 0 && neighborY < this.size) {
                neighbors.add(new Point(neighborX, neighborY));
            }
        }

        return neighbors;
    }

    public ArrayList<GameBoard> getNeighborBoards() {
        ArrayList<Point> neighbors = getPointNeighbors(nullPoint);
        ArrayList<GameBoard> neigborBoards = new ArrayList<>();

        for (Point neighbor : neighbors) {
            GameBoard newBoard = new GameBoard(this.board);
            newBoard.swapPointsOnBoard(this.nullPoint, neighbor);
            newBoard.nullPoint = new Point(neighbor);
            neigborBoards.add(newBoard);
        }

        return neigborBoards;
    }

    public int getSize() {
        return this.size;
    }

    public Integer[][] getBoard() {
        return this.board;
    }
}
