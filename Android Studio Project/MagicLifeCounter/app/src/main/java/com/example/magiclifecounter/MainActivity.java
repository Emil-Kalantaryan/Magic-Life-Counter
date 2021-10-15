package com.example.magiclifecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public int P1Life;
    public int P2Life;
    public int P1Poison;
    public int P2Poison;
    public boolean gameOver;
    public String gameOverMSG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetValues();
        updateStatus();
    }

    public void resetValues() {
        P1Life = 20;
        P2Life = 20;
        P1Poison = 0;
        P2Poison = 0;
        gameOver = false;
        updateStatus();
    }

    @SuppressLint("SetTextI18n")
    public void updateStatus() {
        TextView P1status = findViewById(R.id.P1status);
        TextView P2status = findViewById(R.id.P2status);
        P1status.setText(P1Life + "/" + P1Poison);
        P2status.setText(P2Life + "/" + P2Poison);
    }

    public void StealLifeP1toP2(View view) {
        P2Life++;
        P1Life--;
        updateStatus();
        if (P1Life == 0) {
            gameOverMSG = "Player 1 loses, his life amount has reached 0";
            gameOverDialog(gameOverMSG);
        }
    }

    public void StealLifeP2toP1(View view) {
        P1Life++;
        P2Life--;
        updateStatus();
        if (P2Life == 0) {
            gameOverMSG = "Player 2 loses, his life amount has reached 0";
            gameOverDialog(gameOverMSG);
        }
    }

    public void P1LoseLife(View view) {
        P1Life--;
        updateStatus();
        if (P1Life == 0) {
            gameOverMSG = "Player 1 loses, his life amount has reached 0";
            gameOverDialog(gameOverMSG);
        }
    }

    public void P2LoseLife(View view) {
        P2Life--;
        updateStatus();
        if (P2Life == 0) {
            gameOverMSG = "Player 2 loses, his life amount has reached 0";
            gameOverDialog(gameOverMSG);
        }
    }

    public void P1GetLife(View view) {
        P1Life++;
        updateStatus();
    }

    public void P2GetLife(View view) {
        P2Life++;
        updateStatus();
    }

    public void P1GetPoison(View view) {
        P1Poison++;
        updateStatus();
        if (P1Poison == 10) {
            gameOverMSG = "Player 1 loses. the amount of his poison has reached 10";
            gameOverDialog(gameOverMSG);
        }
    }

    public void P2GetPoison(View view) {
        P2Poison++;
        updateStatus();
        if (P2Poison == 10) {
            gameOverMSG = "Player 2 loses. the amount of his poison has reached 10";
            gameOverDialog(gameOverMSG);
        }
    }

    public void P1LosePoison(View view) {
        if (P1Poison - 1 == -1) {
            poisonNegativeToast();
        } else {
            P1Poison--;
            updateStatus();
        }
    }

    public void P2LosePoison(View view) {
        if (P2Poison - 1 == -1) {
            poisonNegativeToast();
        } else {
            P2Poison--;
            updateStatus();
        }
    }

    public void poisonNegativeToast() {
        Context context = getApplicationContext();
        CharSequence text = "The value of the poison cannot be negative";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }

    public void gameOverDialog(String msg) {
        gameOver = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Game over");
        builder.setMessage(msg+
                "\n\nPlayer 1 status: "+P1Life+"/"+P1Poison+
                "\nPlayer 2 status: "+P2Life+"/"+P2Poison);
        builder.setPositiveButton("Restart", (dialogInterface, i) -> restartWithSnackbar());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void restartWithSnackbar() {
        resetValues();
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.Restart, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.magic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        restartWithSnackbar();
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("P1Life", P1Life);
        savedInstanceState.putInt("P2Life", P2Life);
        savedInstanceState.putInt("P1Poison", P1Poison);
        savedInstanceState.putInt("P2Poison", P2Poison);
        savedInstanceState.putBoolean("gameOver", gameOver);
        savedInstanceState.putString("gameOverMSG", gameOverMSG);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        P1Life = savedInstanceState.getInt("P1Life");
        P2Life = savedInstanceState.getInt("P2Life");
        P1Poison = savedInstanceState.getInt("P1Poison");
        P2Poison = savedInstanceState.getInt("P2Poison");
        gameOver = savedInstanceState.getBoolean("gameOver");
        gameOverMSG = savedInstanceState.getString("gameOverMSG");
        updateStatus();
        if ( gameOver ) {
            gameOverDialog(gameOverMSG);
        }
    }
}