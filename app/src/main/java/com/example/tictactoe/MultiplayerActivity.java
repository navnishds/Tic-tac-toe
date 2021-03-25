package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MultiplayerActivity extends AppCompatActivity {

    // Create new game board
    private Board board;
    private TextView gameMessage;
    private TextView playerOneScore;
    private TextView playerTwoScore;
    private TextView playerOne;
    private TextView playerTwo;
    private TableLayout gameBoard;
    private Button restartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        board = new Board("player 1", "player 2");
        this.gameMessage = (TextView) findViewById(R.id.game_message);
        this.playerOneScore = (TextView) findViewById(R.id.player_one_score);
        this.playerTwoScore = (TextView) findViewById(R.id.player_two_score);
        this.playerOne = (TextView) findViewById(R.id.player_one);
        this.playerTwo = (TextView) findViewById(R.id.player_two);
        this.gameBoard = (TableLayout) findViewById(R.id.game_board);
        this.restartGame = (Button) findViewById(R.id.restart_game);
    }

    public void updateBoard(View view) {
        String tag = (String) view.getTag();
        int x = 0;
        int y = 0;
        switch (tag) {
            case "00":
                x = 0; y = 0;
                break;
            case "01":
                x = 0; y = 1;
                break;
            case "02":
                x = 0; y = 2;
                break;
            case "10":
                x = 1; y = 0;
                break;
            case "11":
                x = 1; y = 1;
                break;
            case "12":
                x = 1; y = 2;
                break;
            case "20":
                x = 2; y = 0;
                break;
            case "21":
                x = 2; y = 1;
                break;
            case "22":
                x = 2; y = 2;
                break;
        }
        this.board.updateMove(x, y);
        Button button = findViewById(view.getId());
        button.setText(this.board.board[x][y]);
        button.setEnabled(false);

        // check if any player won or if its a draw
        if (!this.board.winner.equals("")) {
            this.gameMessage.setText(this.board.getWinner());
            this.stopGame();
        }
        else {
            this.gameMessage.setText(this.board.getPlayerTurn());
        }
    }

    public void stopGame() {

        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) this.gameBoard.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setEnabled(false);
            }
        }
        this.playerOneScore.setText(String.valueOf(this.board.playerOneScore));
        this.playerTwoScore.setText(String.valueOf(this.board.playerTwoScore));
    }

    public void resetBoard(View view) {
        this.board.resetBoard();
        this.gameMessage.setText(this.board.getPlayerTurn());

        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) this.gameBoard.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setEnabled(true);
                button.setText("");
            }
        }
    }
}