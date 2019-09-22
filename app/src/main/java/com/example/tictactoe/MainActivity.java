package com.example.tictactoe;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener{
    private final Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    //the below string is used to avoid String literal warnings
    private String temp;

    private int player1Points;
    private int player2Points;

    private TextView turn;
    private TextView steps;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reset;
        Button New;

        textViewPlayer1=findViewById(R.id.player1score);
        textViewPlayer2=findViewById(R.id.player2score);
        turn=findViewById(R.id.turn);
        steps=findViewById(R.id.steps);

        reset=findViewById(R.id.reset);
        New=findViewById(R.id.New);

        //listener for New Button
        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });

        //listener for Reset Button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                String button="button_"+i+j;

                //In below line, we can get id by passing the id of it in the form of string
                int resID = getResources().getIdentifier(button, "id", getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }



    }

    //method which detects input from all buttons from board
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
            turn.setText(R.string.player2);
        } else {
            ((Button) v).setText("O");
            turn.setText(R.string.player);
        }
        temp=8-roundCount+" Steps to go";
        steps.setText(temp);

        roundCount++;

        if (check()) {
            if (player1Turn) {
                player1Win();
            } else {
                player2Win();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    //method which checks for win in every turn
    private boolean check() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    //condition for player 1 win
    private void player1Win() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    //condition for player 2 win
    private void player2Win() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    //condition for draw
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    //updates points for every round
    private void updatePoints() {
        temp=Integer.toString(player1Points);
        textViewPlayer1.setText(temp);
        temp=Integer.toString(player2Points);
        textViewPlayer2.setText(temp);
        temp=9+" Steps to go";
        steps.setText(temp);
    }

    //removes everything from board
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
        turn.setText(R.string.ready);
        temp=9+" Steps to go";
        steps.setText(temp);
    }

    //Resets the whole game i.e setting scores to 0 && removing everything from board
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePoints();
        resetBoard();
        turn.setText(R.string.ready);
    }


}


