package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // call single player activity
    public void single_player(View view) {
        Intent singlePlayer = new Intent(this, SinglePlayerActivity.class);
        startActivity(singlePlayer);
    }

    // call multi player activity
    public void multi_player(View view) {
        Intent multiPlayer = new Intent(this, MultiplayerActivity.class);
        startActivity(multiPlayer);
    }
}