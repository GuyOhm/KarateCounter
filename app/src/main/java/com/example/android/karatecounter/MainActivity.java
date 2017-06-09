package com.example.android.karatecounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Tracks RED score
    int redScore = 0;
    // Tracks BLUE score
    int blueScore = 0;
    // Tracks round number
    int roundNumber = 1;
    // Tracks number of votes for Blue
    int scoreVotesForBlue = 0;
    // Tracks number of votes for Red
    int scoreVotesForRed = 0;
    // Tracks number of kicks for BLUE
    int blueKicks = 0;
    // Tracks number of kicks for RED
    int redKicks = 0;
    // Tracks number of fouls for BLUE
    int blueFouls = 0;
    // Tracks number of fouls for RED
    int redFouls = 0;

    static final String STATE_RED_SCORE = "redScore";
    static final String STATE_BLUE_SCORE = "blueScore";
    static final String STATE_ROUND_NUMBER = "roundNumber";
    static final String STATE_RED_KICKS = "redKicks";
    static final String STATE_BLUE_KICKS = "blueKicks";
    static final String STATE_RED_FOULS = "redFouls";
    static final String STATE_BLUE_FOULS = "blueFouls";
    static final String STATE_VOTES_FOR_RED = "scoreVotesForRed";
    static final String STATE_VOTES_FOR_BLUE = "scoreVotesForBlue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_RED_SCORE, redScore);
        savedInstanceState.putInt(STATE_BLUE_SCORE, blueScore);
        savedInstanceState.putInt(STATE_ROUND_NUMBER, roundNumber);
        savedInstanceState.putInt(STATE_RED_KICKS, redKicks);
        savedInstanceState.putInt(STATE_BLUE_KICKS, blueKicks);
        savedInstanceState.putInt(STATE_RED_FOULS, redFouls);
        savedInstanceState.putInt(STATE_BLUE_FOULS, blueFouls);
        savedInstanceState.putInt(STATE_VOTES_FOR_RED, scoreVotesForRed);
        savedInstanceState.putInt(STATE_VOTES_FOR_BLUE, scoreVotesForBlue);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // restore state members from saved instance
        redScore = savedInstanceState.getInt(STATE_RED_SCORE);
        blueScore = savedInstanceState.getInt(STATE_BLUE_SCORE);
        roundNumber = savedInstanceState.getInt(STATE_ROUND_NUMBER);
        redKicks = savedInstanceState.getInt(STATE_RED_KICKS);
        blueKicks = savedInstanceState.getInt(STATE_BLUE_KICKS);
        redFouls = savedInstanceState.getInt(STATE_RED_FOULS);
        blueFouls = savedInstanceState.getInt(STATE_BLUE_FOULS);
        scoreVotesForRed = savedInstanceState.getInt(STATE_VOTES_FOR_RED);
        scoreVotesForBlue = savedInstanceState.getInt(STATE_VOTES_FOR_BLUE);


        // display restored members
        displayForRed(redScore);
        displayForBlue(blueScore);
        displayRoundNumber(roundNumber);
        displayKicksForRed(redKicks);
        displayKicksForBlue(blueKicks);
        displayFoulsForRed(redFouls);
        displayFoulsForBlue(blueFouls);
    }

    /**
     * Displays the given score for Blue fighter.
     */
    public void displayForBlue(int score) {
        TextView scoreView = (TextView) findViewById(R.id.blue_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given score for Red fighter.
     */
    public void displayForRed(int score) {
        TextView scoreView = (TextView) findViewById(R.id.red_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Check if the match is over
     *
     * @return TRUE if it's over (more than 15 votes for each fighters) otherwise FALSE
     */
    public boolean checkIfTheMatchIsOver() {
        if (scoreVotesForBlue <= 15 && scoreVotesForRed <= 15) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Handles round number display (each of the 3 judges makes one vote per round)
     */
    public void incrementRoundNumber() {
        if (scoreVotesForBlue == 3 && scoreVotesForRed == 3) {
            roundNumber = 2;
        } else if (scoreVotesForBlue == 6 && scoreVotesForRed == 6) {
            roundNumber = 3;
        } else if (scoreVotesForBlue == 9 && scoreVotesForRed == 9) {
            roundNumber = 4;
        } else if (scoreVotesForBlue == 12 && scoreVotesForRed == 12) {
            roundNumber = 5;
        } else {
            return;
        }
        displayRoundNumber(roundNumber);
    }

    /**
     * Calculate Kicks penalties (a minimum of 6 kicks must be done per round. At the end of the
     * fight 1 point is deducted per missing kick.)
     */
    public void calculateKicksPenalties() {
        if (scoreVotesForBlue == 15 && scoreVotesForRed == 15) {
            if (blueKicks < (5 * 6)) {
                blueScore = blueScore - ((5 * 6) - blueKicks);
                displayForBlue(blueScore);
            }
            if (redKicks < (5 * 6)) {
                redScore = redScore - ((5 * 6) - redKicks);
                displayForRed(redScore);
            }
        }
    }

    /**
     * Add 1 point to Blue fighter
     */
    public void addOnePointForBlue(View view) {
        scoreVotesForBlue++;

        if (!checkIfTheMatchIsOver()) {
            blueScore++;
            displayForBlue(blueScore);
            incrementRoundNumber();
        }

        calculateKicksPenalties();
    }

    /**
     * Add 1 point to Red fighter
     */
    public void addOnePointForRed(View view) {
        scoreVotesForRed++;

        if (!checkIfTheMatchIsOver()) {
            redScore++;
            displayForRed(redScore);
            incrementRoundNumber();
        }

        calculateKicksPenalties();
    }

    /**
     * Add 2 points to Blue fighter
     */
    public void addTwoPointsForBlue(View view) {
        scoreVotesForBlue++;

        if (!checkIfTheMatchIsOver()) {
            blueScore += 2;
            displayForBlue(blueScore);
            incrementRoundNumber();
        }

        calculateKicksPenalties();
    }

    /**
     * Add 2 points to Red fighter
     */
    public void addTwoPointsForRed(View view) {
        scoreVotesForRed++;

        if (!checkIfTheMatchIsOver()) {
            redScore += 2;
            displayForRed(redScore);
            incrementRoundNumber();
        }

        calculateKicksPenalties();
    }

    /**
     * Add 3 points to Blue fighter
     */
    public void addThreePointsForBlue(View view) {
        scoreVotesForBlue++;

        if (!checkIfTheMatchIsOver()) {
            blueScore += 3;
            displayForBlue(blueScore);
            incrementRoundNumber();
        }

        calculateKicksPenalties();
    }

    /**
     * Add 3 points to Red fighter
     */
    public void addThreePointsForRed(View view) {
        scoreVotesForRed++;

        if (!checkIfTheMatchIsOver()) {
            redScore += 3;
            displayForRed(redScore);
            incrementRoundNumber();
        }

        calculateKicksPenalties();

    }

    /**
     * Displays number of kicks for Blue fighter.
     */
    public void displayKicksForBlue(int kicks) {
        TextView scoreView = (TextView) findViewById(R.id.blue_kicks);
        scoreView.setText(String.valueOf(kicks));
    }

    /**
     * Displays number of kicks for Red fighter.
     */
    public void displayKicksForRed(int kicks) {
        TextView scoreView = (TextView) findViewById(R.id.red_kicks);
        scoreView.setText(String.valueOf(kicks));
    }

    /**
     * Add 1 kick to Blue fighter
     */
    public void addOneKickForBlue(View view) {
        if (!checkIfTheMatchIsOver()) {
            blueKicks++;
            displayKicksForBlue(blueKicks);
        }
    }

    /**
     * Add 1 kick to Red fighter
     */
    public void addOneKickForRed(View view) {
        if (!checkIfTheMatchIsOver()) {
            redKicks++;
            displayKicksForRed(redKicks);
        }
    }

    /**
     * Displays number of fouls for Blue fighter.
     */
    public void displayFoulsForBlue(int fouls) {
        TextView scoreView = (TextView) findViewById(R.id.blue_fouls);
        scoreView.setText(String.valueOf(fouls));
    }

    /**
     * Displays number of fouls for Red fighter.
     */
    public void displayFoulsForRed(int fouls) {
        TextView scoreView = (TextView) findViewById(R.id.red_fouls);
        scoreView.setText(String.valueOf(fouls));
    }

    /**
     * Check BLUE's number of fouls and gives penalties (At the 4th foul the round is given
     * to the opponent 0 - 9 points)
     */
    public void checkNumberOfFoulsForBlue() {
        if (blueFouls % 4 == 0) {
            redScore += 9;
            if (roundNumber < 5) {
                roundNumber++;
            }
        } else {
            return;
        }
        displayForRed(redScore);
        displayRoundNumber(roundNumber);
        scoreVotesForRed += 3;
        scoreVotesForBlue += 3;
    }

    /**
     * Check RED's number of fouls and gives penalties (At the 4th foul the round is given
     * to the opponent 0 - 9 points)
     */
    public void checkNumberOfFoulsForRed() {
        if (redFouls % 4 == 0) {
            blueScore += 9;
            if (roundNumber < 5) {
                roundNumber++;
            }
        } else {
            return;
        }
        displayForBlue(blueScore);
        displayRoundNumber(roundNumber);
        scoreVotesForRed += 3;
        scoreVotesForBlue += 3;
    }

    /**
     * Add 1 fouls to Blue fighter
     */
    public void addOneFoulForBlue(View view) {
        if (!checkIfTheMatchIsOver()) {
            blueFouls++;
            checkNumberOfFoulsForBlue();
            displayFoulsForBlue(blueFouls);
        }

        calculateKicksPenalties();
    }

    /**
     * Add 1 fouls to Red fighter
     */
    public void addOneFoulForRed(View view) {
        if (!checkIfTheMatchIsOver()) {
            redFouls++;
            checkNumberOfFoulsForRed();
            displayFoulsForRed(redFouls);
        }

        calculateKicksPenalties();
    }

    // Reset scores and counters
    public void resetScores(View view) {
        blueScore = 0;
        redScore = 0;
        blueKicks = 0;
        redKicks = 0;
        blueFouls = 0;
        redFouls = 0;
        roundNumber = 1;
        scoreVotesForBlue = 0;
        scoreVotesForRed = 0;
        displayForBlue(blueScore);
        displayForRed(redScore);
        displayKicksForBlue(blueKicks);
        displayKicksForRed(redKicks);
        displayFoulsForBlue(blueFouls);
        displayFoulsForRed(redFouls);
        displayRoundNumber(roundNumber);
    }

    /**
     * Displays the round number.
     */
    public void displayRoundNumber(int round) {
        TextView roundView = (TextView) findViewById(R.id.round_number);
        roundView.setText("Round ".concat(String.valueOf(round)));
    }

}
