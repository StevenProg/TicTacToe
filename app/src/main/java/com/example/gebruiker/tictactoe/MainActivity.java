package com.example.gebruiker.tictactoe;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

// Created by Steven Schoenmaker (10777679)

public class MainActivity extends AppCompatActivity {

    int[][] board = new int[3][3];
    Button But_Reset;
    Game game;
    TextView text;
    int movesPlayed;
    Boolean Winner = false;
    Tile[][] play_board;
    Boolean GameOver = false;
    ImageButton but_0;
    ImageButton but_1;
    ImageButton but_2;
    ImageButton but_3;
    ImageButton but_4;
    ImageButton but_5;
    ImageButton but_6;
    ImageButton but_7;
    ImageButton but_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but_0 = findViewById(R.id.button0);
        but_1 = findViewById(R.id.button1);
        but_2 = findViewById(R.id.button2);
        but_3 = findViewById(R.id.button3);
        but_4 = findViewById(R.id.button4);
        but_5 = findViewById(R.id.button5);
        but_6 = findViewById(R.id.button6);
        but_7 = findViewById(R.id.button7);
        but_8 = findViewById(R.id.button8);

        but_0.setTag(R.drawable.background);
        but_1.setTag(R.drawable.background);
        but_2.setTag(R.drawable.background);
        but_3.setTag(R.drawable.background);
        but_4.setTag(R.drawable.background);
        but_5.setTag(R.drawable.background);
        but_6.setTag(R.drawable.background);
        but_7.setTag(R.drawable.background);
        but_8.setTag(R.drawable.background);

        game = new Game();
        for (int i = 0; i < 9; i++) {
            String Button = "button" + i;
            int ID = getResources().getIdentifier(Button, "id", getPackageName());
            board[(int) Math.floor(i / 3)][i % 3] = ID;
        }
        But_Reset = findViewById(R.id.button);
        text = findViewById(R.id.textView2);
        movesPlayed = 0;

        if (savedInstanceState != null) {
            but_0.setImageResource(savedInstanceState.getInt("but_0"));
            but_1.setImageResource(savedInstanceState.getInt("but_1"));
            but_2.setImageResource(savedInstanceState.getInt("but_2"));
            but_3.setImageResource(savedInstanceState.getInt("but_3"));
            but_4.setImageResource(savedInstanceState.getInt("but_4"));
            but_5.setImageResource(savedInstanceState.getInt("but_5"));
            but_6.setImageResource(savedInstanceState.getInt("but_6"));
            but_7.setImageResource(savedInstanceState.getInt("but_7"));
            but_8.setImageResource(savedInstanceState.getInt("but_8"));
            text.setText(savedInstanceState.getInt("text"));
            movesPlayed = (savedInstanceState.getInt("movesPlayed"));


        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // always call super

        outState.putInt("but_0", (int) but_0.getTag());
        outState.putInt("but_1", (int) but_1.getTag());
        outState.putInt("but_2", (int) but_2.getTag());
        outState.putInt("but_3", (int) but_3.getTag());
        outState.putInt("but_4", (int) but_4.getTag());
        outState.putInt("but_5", (int) but_5.getTag());
        outState.putInt("but_6", (int) but_6.getTag());
        outState.putInt("but_7", (int) but_7.getTag());
        outState.putInt("but_8", (int) but_8.getTag());
        outState.putString("text", (String) text.getText());
        outState.putInt("movesPlayed", movesPlayed);

    }
    // creates 3 by 3 playing board
    public void tileClicked(View view) {
        int id = view.getId();
        int row = 0;
        int column = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == id) {
                    row = i;
                    column = j;
                }
            }
        }

        // imageButtons which get pressed will be changed based on player's turn
        Tile tile = game.draw(row, column);
        if (!Winner) {
            ImageButton current_view = (ImageButton) view;
            switch (tile) {
                case CROSS:
                    text.setText("Player two's turn");
                    current_view.setImageResource(R.drawable.cross);
                    current_view.setTag(R.drawable.cross);

                    break;
                case CIRCLE:
                    text.setText("Player one's turn");
                    current_view.setImageResource(R.drawable.circle);
                    current_view.setTag(R.drawable.circle);

                    break;
                case INVALID:
                    break;
            }
        }

        // win messages
        Winner = winCheck(play_board);
        if (Winner && !GameOver) {
            GameOver = true;
            switch (tile) {
                case CROSS:
                    text.setText("Player one wins!");
                    break;
                case CIRCLE:
                    text.setText("Player two wins!");
                    break;
            }
        }
        if (movesPlayed > 8 && !Winner) {
            text.setText("Game is a draw!");
        }
    }


    // win conditions (any 3 tiles in a row(horizontal, vertical and diagonal))
    public boolean winCheck(Tile[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]) && !board[i][0].equals(Tile.BLANK) ) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]) && !board[0][i].equals(Tile.BLANK) ) {
                return true;
            }
        }
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals(Tile.BLANK)) {
            System.out.println(3);
            return true;
        }

        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals(Tile.BLANK)) {
            System.out.println(4);
            return true;
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    public void resetClicked(View view) {

        // resets all game parameters
        game = new Game();
        but_0.setImageResource(R.drawable.background);
        but_0.setTag(R.drawable.background);
        but_1.setImageResource(R.drawable.background);
        but_1.setTag(R.drawable.background);
        but_2.setImageResource(R.drawable.background);
        but_2.setTag(R.drawable.background);
        but_3.setImageResource(R.drawable.background);
        but_3.setTag(R.drawable.background);
        but_4.setImageResource(R.drawable.background);
        but_4.setTag(R.drawable.background);
        but_5.setImageResource(R.drawable.background);
        but_5.setTag(R.drawable.background);
        but_6.setImageResource(R.drawable.background);
        but_6.setTag(R.drawable.background);
        but_7.setImageResource(R.drawable.background);
        but_7.setTag(R.drawable.background);
        but_8.setImageResource(R.drawable.background);
        but_8.setTag(R.drawable.background);
        text.setText("Player one's turn");
        movesPlayed = 0;
        Winner = false;
        GameOver = false;

    }



    public enum Tile {
        BLANK,
        CROSS,
        CIRCLE,
        INVALID
    }

    public class Game  implements Serializable {
        final private int BOARD_SIZE = 3;
        private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn

        // initiates blank playing field
        Game() {
            play_board = new Tile[BOARD_SIZE][BOARD_SIZE];
            for (int i = 0; i < BOARD_SIZE; i++)
                for (int j = 0; j < BOARD_SIZE; j++)
                    play_board[i][j] = Tile.BLANK;

            playerOneTurn = true;
        }

        // based on player's turn the tile will be given a value, and the player turn will switch
        Tile draw(int row, int column) {
            if (play_board[row][column] == Tile.BLANK && playerOneTurn && !GameOver) {
                playerOneTurn = false;
                movesPlayed = movesPlayed + 1;
                play_board[row][column] = Tile.CROSS;

            } else if (play_board[row][column] == Tile.BLANK && !playerOneTurn && !GameOver) {
                playerOneTurn = true;
                movesPlayed = movesPlayed + 1;
                play_board[row][column] = Tile.CIRCLE;
            } else {
                return Tile.INVALID;
            }
            return play_board[row][column];
        }
    }
}
