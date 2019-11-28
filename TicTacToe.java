package com.company.arraygames;

import java.util.Scanner;

public class TicTacToe {

    private Scanner s = new Scanner(System.in);

    private final int p1 = 1; // keeping track of which player is playing made easy
    private final int p2 = -1;
    private final int p1Win = 2;
    private final int p2Win = -2;
    // colour stuff
    private final String yellowColour = "\u001B[33m";
    private final String greenColour = "\u001B[32m";
    private final String resetColour = "\u001B[0m";
    private final String greenO = String.format("%sO%s", greenColour, resetColour); // green O called if someone wins to highlight where they won
    private final String greenX = String.format("%sX%s", greenColour, resetColour); // green O called if someone wins to highlight where they won
    private final String p1O = "O";
    private final String p2X = "X";
    private final String emptySpace = " ";

    private boolean playing = true; // playing is true as long as someone doesn't win

    private String gw;
    private String gt;

    private boolean tied = false;

    private final int cols = 3; // amt of cols
    private final int rows = 3; // amt of roles

    private int[][] board = new int[cols][rows]; // board 6x7 (col, row)

    private int turn = 0; // keep track of the number of turns passed
    private int currentPlayer = p1; // O always starts

    private TicTacToe() {
        displayBoard();
        while (playing) {
            try {
                turn++; // next turn
                if (checkForTie()) {
                    gt = String.format("%n%sTie!%s%n%n", yellowColour, resetColour);
                    tied = true;
                    playing = false;
                    break;
                }
                playerTurn(currentPlayer, requestMove());
                if(checkForWin(currentPlayer)) { // if the current player has won
                    if(currentPlayer == p1) { // if the player was O
                        gw = String.format("Player 1 (O) Wins in %d Turns!%n%n", turn);
                        playing = false; // stop playing
                        break; // break the loop
                    } else if(currentPlayer == p2) { // if the player was X
                        gw = String.format("Player 2 (X) Wins in %d Turns!%n%n", turn);
                        playing = false; // stop playing
                        break; // break the loop
                    }
                }
                displayBoard();
                currentPlayer *= -1;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("oops");
            }
        }
        displayBoard(); // show the final board (winning play highlighted green) when/if somebody has won
        if(tied) {
            System.out.println(gt);
        } else {
            System.out.println(gw);
        }
    }

    private int[] requestMove() {
        boolean validMove = false;
        int[] rowcol = new int[2];
        int desiredCol = 0;
        int desiredRow = 0;
        if(currentPlayer == p1) {
            System.out.println("Player 1 (O), where would you like to place your piece? (row col)");
        } else {
            System.out.println("Player 2 (X), where would you like to place your piece? (row col)");
        }
        while(!validMove) {
            try {
                desiredRow = s.nextInt();
                desiredCol = s.nextInt();
                System.out.println("COOL");
                if(desiredRow - 1 > 3 || desiredRow - 1 < 0) {
                    System.out.printf("(%d, %d) is not a valid location because it is out of bounds. Try again.%n", desiredRow, desiredCol);
                } else if(desiredCol - 1 > 3 || desiredCol - 1 < 0) {
                    System.out.printf("(%d, %d) is not a valid location because it is out of bounds. Try again.%n", desiredRow, desiredCol);
                } else if(board[desiredCol - 1][desiredRow - 1] == p1 || board[desiredCol - 1][desiredRow - 1] == p2) {
                    System.out.printf("(%d, %d) is not a valid location because there is already a piece there. Try again.%n", desiredRow, desiredCol);
                } else {
                    validMove = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("That is not a valid number. Try again.");
                s.nextLine();
            }

        }
        desiredCol--;
        desiredRow--;
        rowcol[0] = desiredCol;
        rowcol[1] = desiredRow;
        return rowcol;
    }

    private void playerTurn(int player, int[] rowcol) {
        board[rowcol[0]][rowcol[1]] = player;
    }

    private void displayBoard() {

        System.out.printf("%s   1  2  3%s%n", yellowColour, resetColour); // displays a coordinate system so the players can see which column is which

        for (int i = 0; i < board.length; i++) { // rows
            for (int j = 0; j < board[i].length; j++) { // columns
                if(j == 0) {
                    System.out.printf("%s%d%s ", yellowColour, i+1, resetColour); // draw the row coordinates if the row has yet to be drawn
                }
                if(board[i][j] == p1) {
                    System.out.printf("|%s|", p1O);
                } else if(board[i][j] == p2) {
                    System.out.printf("|%s|", p2X);
                } else if(board[i][j] == p1Win) {
                    System.out.printf("|%s|", greenO);
                } else if(board[i][j] == p2Win) {
                    System.out.printf("|%s|", greenX);
                } else {
                    System.out.printf("|%s|", emptySpace);
                }
            }
            System.out.println(); // new line
        }
    }

    private boolean checkForTie() {
        if(turn >= 10) { // if the board is full
            return true;
        }
        return false;
    }

    private boolean checkForWin(int player) {
        if(checkLeftRight(player)) {
            return true;
        } else if(checkUpDown(player)) {
            return true;
        } else if(checkDiagonal1(player)) {
            return true;
        } else if(checkDiagonal2(player)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkDiagonal2(int player) {
        if(board[0][0] == player) {
            if(board[1][1] == player) {
                if(board[2][2] == player) {
                    if(player == p1) {
                        board[0][0] = p1Win;
                        board[1][1] = p1Win;
                        board[2][2] = p1Win;
                    } else {
                        board[0][0] = p2Win;
                        board[1][1] = p2Win;
                        board[2][2] = p2Win;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal1(int player) {
        if(board[0][2] == player) {
            if(board[1][1] == player) {
                if(board[2][0] == player) {
                    if(player == p1) {
                        board[0][2] = p1Win;
                        board[1][1] = p1Win;
                        board[2][0] = p1Win;
                    } else {
                        board[0][2] = p2Win;
                        board[1][1] = p2Win;
                        board[2][0] = p2Win;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkUpDown(int player) {
        for(int i = 0; i < 3; i++) {
            if(board[0][i] == player) {
                if(board[1][i] == player) {
                    if(board[2][i] == player) {
                        if(player == p1) {
                            for(int k = 0; k < 3; k++) {
                                board[k][i] = p1Win; // go back and re draw the winning move as green
                            }
                        } else {
                            for(int k = 0; k < 3; k++) {
                                board[k][i] = p2Win; // go back and re draw the winning move as green
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkLeftRight(int player) {
        for(int i = 0; i < 3; i++) {
            if(board[i][0] == player) {
                if(board[i][1] == player) {
                    if(board[i][2] == player) {
                        if(player == p1) {
                            for(int k = 0; k < 3; k++) {
                                board[i][k] = p1Win; // go back and re draw the winning move as green
                            }
                        } else {
                            for(int k = 0; k < 3; k++) {
                                board[i][k] = p2Win; // go back and re draw the winning move as green
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}