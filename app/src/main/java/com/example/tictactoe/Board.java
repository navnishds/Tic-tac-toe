package com.example.tictactoe;

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

    protected Board(String playerOneName, String playerTwoName) {
        this.board = new String[3][3];
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
        playerOneScore = 0;
        playerTwoScore = 0;
        playerOne = "x";
        playerTwo = "o";
        currentPlayer = "x";
        moves = 0;
    }

    void checkBoard() {
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
                this.winner = "x";
                this.incrementScore(this.winner);
                break;
            }
            if (line.equals("ooo")) {
                this.winner = "o";
                this.incrementScore(this.winner);
                break;
            }
        }
        if (this.moves == 9) {
            this.winner = "Draw";
        }
    }

    void changePlayer() {
        if (this.currentPlayer.equals("x")) {
            this.currentPlayer = "o";
        }
        else {
            this.currentPlayer = "x";
        }
    }

    void updateMove(int x, int y) {
        this.board[x][y] = this.currentPlayer;

        // increment number of moves and if >= 5 check board if any player as won
        this.moves += 1;
        if (this.moves >= 5) {
            this.checkBoard();
        }
        // change player turn
        if (this.winner.equals("")) {
            this.changePlayer();
        }
    }

    String getWinner() {
        if (winner.equals(playerOne)) {
            return playerOneName + " Won!";
        }
        if (winner.equals(playerTwo)) {
            return playerTwoName + " Won!";
        }
        return winner;
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
        else {
            this.playerTwoScore += 1;
        }
    }

    void resetBoard() {
        this.board = new String[3][3];
        if (this.playerOne.equals("x")) {
            this.playerOne = "o";
            this.playerTwo = "x";
        }
        else {
            this.playerOne = "x";
            this.playerTwo = "0";
        }
        this.currentPlayer = "x";
        this.winner = "";
        this.moves = 0;
    }

}
