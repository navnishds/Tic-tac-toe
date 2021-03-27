package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SinglePlayerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Create new game board
    private Board board;
    private TextView gameMessage;
    private TextView playerOneScore;
    private TextView playerTwoScore;
    private TextView playerOne;
    private TextView playerTwo;
    private TableLayout gameBoard;
    private Button restartGame;
    private Button[][] buttons = new Button[3][3];

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        Spinner spinner = (Spinner) findViewById(R.id.game_mode);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_difficulty_array, R.layout.game_mode_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        this.board = new Board("player 1", "bot");
        this.gameMessage = (TextView) findViewById(R.id.game_message);
        this.playerOneScore = (TextView) findViewById(R.id.player_one_score);
        this.playerTwoScore = (TextView) findViewById(R.id.player_two_score);
        this.playerOne = (TextView) findViewById(R.id.player_one);
        this.playerTwo = (TextView) findViewById(R.id.player_two);
        this.gameBoard = (TableLayout) findViewById(R.id.game_board);
        this.restartGame = (Button) findViewById(R.id.restart_game);

        this.playerTwo.setText("bot");

        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) this.gameBoard.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                this.buttons[i][j] = button;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String mode = (String) adapterView.getItemAtPosition(i);
        this.board.setDifficultyMode(mode);
        if (this.board.moves > 0) {
            stopGame();
            resetBoard(this.restartGame);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
            playAI();

            if (!this.board.winner.equals("")) {
                this.gameMessage.setText(this.board.getWinner());
                this.stopGame();
            }
            else {
                this.gameMessage.setText(this.board.getPlayerTurn());
            }
        }
    }

    void playAI() {
        int[] moves = board.playAI();
        Button button = buttons[moves[0]][moves[1]];
        button.setText(board.board[moves[0]][moves[1]]);
        button.setEnabled(false);
        this.gameMessage.setText(this.board.getPlayerTurn());
    }

    public void stopGame() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
        this.playerOneScore.setText(String.valueOf(this.board.playerOneScore));
        this.playerTwoScore.setText(String.valueOf(this.board.playerTwoScore));
    }

    public void resetBoard(View view) {
        this.board.resetBoard();
        this.gameMessage.setText(this.board.getPlayerTurn());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
            }
        }
        if (this.board.playerTwo.equals(this.board.currentPlayer))
            playAI();
    }
}