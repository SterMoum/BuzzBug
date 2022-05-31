package com.example.bug_buzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_menu);
    }


    public void onStartGame(View view) {
        Intent intent = new Intent(this, ProfileSelection.class);
        startActivity(intent);
    }

    public void onHighScores(View view){
        Intent intent= new Intent(this, HighScores.class);
        startActivity(intent);
    }

    public void QuitApp(View view) {
        MainMenu.this.finish();
        System.exit(0);
    }
}