package com.example.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    String[][] board;
    String playerOneName;
    String playerTwoName;
    int playerOneScore;
    int playerTwoScore;
    String playerOne;
    String playerTwo;
    String currentPlayer;
    int moves;
    String winner = "";
    int depth = 0;

    protected Board(String playerOneName, String playerTwoName) {
        this.board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
        playerOneScore = 0;
        playerTwoScore = 0;
        playerOne = "x";
        playerTwo = "o";
        currentPlayer = "x";
        moves = 0;
    }

    String checkBoard(String[][] board, int moves) {
        for(int i = 0; i < 8; i++) {
            String line = "";

            switch (i) {
                case 0:
                    line = board[0][0] + board[0][1] + board[0][2];
                    break;
                case 1:
                    line = board[0][0] + board[1][1] + board[2][2];
                    break;
                case 2:
                    line = board[0][0] + board[1][0] + board[2][0];
                    break;
                case 3:
                    line = board[0][1] + board[1][1] + board[2][1];
                    break;
                case 4:
                    line = board[0][2] + board[1][2] + board[2][2];
                    break;
                case 5:
                    line = board[1][0] + board[1][1] + board[1][2];
                    break;
                case 6:
                    line = board[2][0] + board[2][1] + board[2][2];
                    break;
                case 7:
                    line = board[2][0] + board[1][1] + board[0][2];
                    break;
            }

            if (line.equals("xxx")) {
                return "x";
            }
            if (line.equals("ooo")) {
                return "o";
            }
        }
        if (moves == 9) {
            return "Draw";
        }
        return "";
    }

    String changePlayer(String currentPlayer) {
        if (currentPlayer.equals("x")) {
            return "o";
        }
        else {
            return "x";
        }
    }

    void updateMove(int x, int y) {
        this.board[x][y] = this.currentPlayer;

        // increment number of moves and if >= 5 check board if any player as won
        this.moves += 1;
        if (this.moves >= 5) {
            this.winner = this.checkBoard(this.board, this.moves);
        }
        // change player turn
        if (this.winner.equals("")) {
            this.currentPlayer = this.changePlayer(this.currentPlayer);
        }
        else {
            this.incrementScore(this.winner);
        }
    }

    boolean gameFinished(String winner) {
        return !winner.equals("");
    }

    String getWinner() {
        if (this.winner.equals(this.playerOne)) {
            return this.playerOneName + " Won!";
        }
        if (this.winner.equals(this.playerTwo)) {
            return this.playerTwoName + " Won!";
        }
        return this.winner;
    }

    String getPlayerTurn() {
        if (this.playerOne.equals(this.currentPlayer)) {
            return this.playerOneName + " (" + this.playerOne + ") turn";
        }
        else {
            return this.playerTwoName + " (" + this.playerTwo + ") turn";
        }
    }

    void incrementScore(String playerWon) {
        if (playerWon.equals(this.playerOne)) {
            this.playerOneScore += 1;
        }
        if (playerWon.equals(this.playerTwo)) {
            this.playerTwoScore += 1;
        }
    }

    void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = "";
            }
        }
        if (this.playerOne.equals("x")) {
            this.playerOne = "o";
            this.playerTwo = "x";
        }
        else {
            this.playerOne = "x";
            this.playerTwo = "o";
        }
        this.currentPlayer = "x";
        this.winner = "";
        this.moves = 0;
    }

    void setDifficultyMode(String mode) {
        if (mode.equals("Easy")) {
            this.depth = 3;
        }
        if (mode.equals("Medium")) {
            this.depth = 5;
        }
        if (mode.equals("Impossible")) {
            this.depth = 8;
        }
    }

    int minMax(String[][] aiBoard, int depth, boolean isMax, String player, int moves) {

        String gameStatus = this.checkBoard(aiBoard, moves);
        if (gameStatus.equals(this.currentPlayer)) {
            return 10-depth;
        }
        if (gameStatus.equals(changePlayer(this.currentPlayer))) {
            return -10+depth;
        }
        if (gameStatus.equals("Draw") || moves >= 9 || depth >= this.depth) {
            return 0;
        }

        if (isMax) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (aiBoard[i][j].equals("")) {
                        aiBoard[i][j] = player;
                        best = Math.max(best, minMax(aiBoard, depth + 1, false, changePlayer(player), moves + 1));
                        aiBoard[i][j] = "";
                    }
                }
            }
            return best;
        }
        else {
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (aiBoard[i][j].equals("")) {
                        aiBoard[i][j] = player;
                        best = Math.min(best, minMax(aiBoard, depth + 1, true, changePlayer(player), moves + 1));
                        aiBoard[i][j] = "";
                    }
                }
            }
            return best;
        }
    }

    int[] playAI() {
        int bestValue = -1000;
        int bestRowMove = -1;
        int bestColMove = -1;
//        ArrayList<> bestMoves = new ArrayList<>();
//        ArrayList<> newBestMoves = new ArrayList<>();
        String bot = this.currentPlayer;

        String[][] newBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newBoard[i][j] = this.board[i][j];
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (newBoard[i][j].equals("")){
                    newBoard[i][j] = bot;
                    int moveValue = minMax(newBoard, 0, false, this.changePlayer(bot), this.moves+1);

                    newBoard[i][j] = "";
//                    int[] temp = new int[]{moveValue, i, j};
//                    bestMoves.add(temp);
                    if (moveValue > bestValue) {
                        bestValue = moveValue;
                        bestRowMove = i;
                        bestColMove = j;
                    }
                }
            }
        }

//        for (int i = 0; i < bestMoves.size(); i++) {
//            int[] temp = bestMoves.get(i);
//            if (temp[i] == bestValue) {
//                newBestMoves.add(temp);
//            }
//        }

//        if (newBestMoves.size() > 1){
//            int rnd = new Random().nextInt(newBestMoves.size());
//            int[] temp = newBestMoves.get(rnd);
//            bestRowMove = temp[1];
//            bestColMove = temp[2];
//        }

        updateMove(bestRowMove, bestColMove);
        return new int[]{bestRowMove, bestColMove};
    }

}
