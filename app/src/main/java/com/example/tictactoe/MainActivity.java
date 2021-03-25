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

    public void single_player(View view) {
    }

    public void multi_player(View view) {
        Intent multiPlayer = new Intent(this, MultiplayerActivity.class);
        startActivity(multiPlayer);
    }
}