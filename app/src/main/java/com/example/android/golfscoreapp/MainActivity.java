package com.example.android.golfscoreapp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Global variables
    public String phase = "initial";
    public int initialViewState = 1;
    public int hole = 1;
    public String course;
    public int playerCount = 1;
    public int[] playerTeeAry = {0, 2, 2, 2, 2};
    public String[] playerNameAry = {"X", "Player 1", "Player 2", "Player 3", "Player 4"};
    public int[] playerHandicapAry = {0, 0, 0, 0, 0};
    public int[] courseParAry;
    public String[] courseHoleNameAry;
    public int[] courseDistanceEasyAry;
    public int[] courseDistanceMediumAry;
    public int[] courseDistanceHardAry;
    public String[] teeColorAry;
    public int holeCount;
    public int[] player1Score;
    public int[] player2Score;
    public int[] player3Score;
    public int[] player4Score;
    public boolean preserveScoreArrays = false;

    //Global object variables
    public ScrollView coursesViewGroup;
    public LinearLayout playerCountViewGroup;
    public TextView playerCountView;
    public ScrollView playerInfoViewGroup;
    public LinearLayout playerTwoInfoView;
    public LinearLayout playerThreeInfoView;
    public LinearLayout playerFourInfoView;
    public LinearLayout beginRoundViewGroup;
    public ImageView gameBackgroundImageView;
    public TextView gameHoleNumberView;
    public TextView gameHoleNameView;
    public LinearLayout player2ScoreGroup;
    public LinearLayout player3ScoreGroup;
    public LinearLayout player4ScoreGroup;
    public TextView player1NameView;
    public TextView player2NameView;
    public TextView player3NameView;
    public TextView player4NameView;
    public TextView player1ScoreView;
    public TextView player2ScoreView;
    public TextView player3ScoreView;
    public TextView player4ScoreView;
    public TextView holePar;
    public TextView holeHardTeeDistance;
    public TextView holeMediumTeeDistance;
    public TextView holeEasyTeeDistance;
    public ImageView holeMap;
    public TextView hardTeeColor;
    public TextView mediumTeeColor;
    public TextView easyTeeColor;
    public ImageView fullSizeMap;
    public Button closeMap;

    // Save off key global variables on saveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hole", hole);
        outState.putInt("initialViewState", initialViewState);
        outState.putString("phase", phase);
        outState.putString("course", course);
        outState.putInt("playerCount", playerCount);
        outState.putIntArray("playerTeeAry", playerTeeAry);
        outState.putStringArray("playerNameAry", playerNameAry);
        outState.putIntArray("playerHandicapAry", playerHandicapAry);
        outState.putIntArray("courseParAry", courseParAry);
        outState.putIntArray("courseDistanceEasyAry", courseDistanceEasyAry);
        outState.putIntArray("courseDistanceMediumAry", courseDistanceMediumAry);
        outState.putIntArray("courseDistanceHardAry", courseDistanceHardAry);
        outState.putStringArray("courseHoleNameAry", courseHoleNameAry);
        outState.putStringArray("teeColorAry", teeColorAry);
        outState.putInt("holeCount", holeCount);
        outState.putIntArray("player1Score", player1Score);
        outState.putIntArray("player2Score", player2Score);
        outState.putIntArray("player3Score", player3Score);
        outState.putIntArray("player4Score", player4Score);
    }

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rebuildState(savedInstanceState);  //Rebuild vars on a state change
        switch (phase) {
            case "initial":
                setContentView(R.layout.initial_layout_portrait);
                buildInitialObjects();
                break;
            case "game":
                setContentView(R.layout.game_layout_portrait);
                buildGameObjects();
                break;
            case "final":
                setContentView(R.layout.final_layout_portrait);
                createFinalObjects();
                break;
        }
    }

    //Rebuild key global variables on a state change
    public void rebuildState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            hole = savedInstanceState.getInt("hole", hole);
            initialViewState = savedInstanceState.getInt("initialViewState");
            phase = savedInstanceState.getString("phase", phase);
            course = savedInstanceState.getString("course", course);
            playerCount = savedInstanceState.getInt("playerCount", playerCount);
            playerTeeAry = savedInstanceState.getIntArray("playerTeeAry");
            playerNameAry = savedInstanceState.getStringArray("playerNameAry");
            playerHandicapAry = savedInstanceState.getIntArray("playerHandicapAry");
            courseParAry = savedInstanceState.getIntArray("courseParAry");
            courseDistanceEasyAry = savedInstanceState.getIntArray("courseDistanceEasyAry");
            courseDistanceMediumAry = savedInstanceState.getIntArray("courseDistanceMediumAry");
            courseDistanceHardAry = savedInstanceState.getIntArray("courseDistanceHardAry");
            courseHoleNameAry = savedInstanceState.getStringArray("courseHoleNameAry");
            teeColorAry = savedInstanceState.getStringArray("teeColorAry");
            holeCount = savedInstanceState.getInt("holeCount", holeCount);
            player1Score = savedInstanceState.getIntArray("player1Score");
            player2Score = savedInstanceState.getIntArray("player2Score");
            player3Score = savedInstanceState.getIntArray("player3Score");
            player4Score = savedInstanceState.getIntArray("player4Score");
            if (phase.equals("initial")) {
                buildInitialObjects();
            }
        }
    }

    //onConfigurationChanged
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeLayoutBasedOnOrientation(newConfig);
    }

    //Change layout based on screen orientation.
    public void changeLayoutBasedOnOrientation(Configuration newConfig) {
        //Portrait orientation
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            switch (phase) {
                case "initial":
                    setContentView(R.layout.initial_layout_portrait);
                    buildInitialObjects();
                    break;
                case "game":
                    setContentView(R.layout.game_layout_portrait);
                    buildGameObjects();
                    break;
                case "final":
                    setContentView(R.layout.final_layout_portrait);
                    createFinalObjects();
                    break;
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            switch (phase) {
                case "initial":
                    setContentView(R.layout.initial_layout_landscape);
                    buildInitialObjects();
                    break;
                case "game":
                    setContentView(R.layout.game_layout_portrait);
                    buildGameObjects();
                    break;
                case "final":
                    setContentView(R.layout.final_layout_landscape);
                    createFinalObjects();
                    break;
            }
        }
    }

    public void buildInitialObjects() {
        coursesViewGroup = findViewById(R.id.course_selection_view_group);
        playerCountViewGroup = findViewById(R.id.player_count_view_group);
        playerCountView = findViewById(R.id.player_count_view);
        playerCountView.setText(String.valueOf(playerCount));
        playerInfoViewGroup = findViewById(R.id.player_info_view_group);
        playerTwoInfoView = findViewById(R.id.player_2_info);
        playerThreeInfoView = findViewById(R.id.player_3_info);
        playerFourInfoView = findViewById(R.id.player_4_info);
        beginRoundViewGroup = findViewById(R.id.button_layout);
        switch (initialViewState) {
            case 1:
                returnToCourseSelection(null);
                break;
            case 2:
                afterCourseSelection();
                switch (playerCount) {
                    case 1:
                        setEditTextViewText();
                        break;
                    case 2:
                        playerTwoInfoView.setVisibility(View.VISIBLE);
                        setEditTextViewText();
                        break;
                    case 3:
                        playerTwoInfoView.setVisibility(View.VISIBLE);
                        playerThreeInfoView.setVisibility(View.VISIBLE);
                        setEditTextViewText();
                    case 4:
                        playerTwoInfoView.setVisibility(View.VISIBLE);
                        playerThreeInfoView.setVisibility(View.VISIBLE);
                        playerFourInfoView.setVisibility(View.VISIBLE);
                        setEditTextViewText();
                }
                break;
        }
    }

    public void setEditTextViewText() {
        for (int i = 1; i <= playerCount; i++) {
            EditText nameView = findViewById(getResources().getIdentifier("player_" + i + "_name", "id", getPackageName()));
            nameView.setText(playerNameAry[i]);
            EditText handicapView = findViewById(getResources().getIdentifier("player_" + i + "_handicap", "id", getPackageName()));
            handicapView.setText(String.valueOf(playerHandicapAry[i]));
            RadioButton teeView = findViewById(getResources().getIdentifier("player_" + i + "_tee_" + playerTeeAry[i], "id", getPackageName()));
            teeView.setChecked(true);
        }
    }

    //On click method executed when a course is selected. Sets visibilities of views.
    public void afterCourseSelection() {
        initialViewState = 2;
        coursesViewGroup.setVisibility(View.GONE);
        playerCountViewGroup.setVisibility(View.VISIBLE);
        playerInfoViewGroup.setVisibility(View.VISIBLE);
        beginRoundViewGroup.setVisibility(View.VISIBLE);
    }

    public void returnToCourseSelection(View view) {
        initialViewState = 1;
        coursesViewGroup.setVisibility(View.VISIBLE);
        playerCountViewGroup.setVisibility(View.GONE);
        playerInfoViewGroup.setVisibility(View.GONE);
        beginRoundViewGroup.setVisibility(View.GONE);
        preserveScoreArrays = false;
        courseParAry = null;
        courseDistanceEasyAry = null;
        courseDistanceMediumAry = null;
        courseDistanceHardAry = null;
        courseHoleNameAry = null;
        teeColorAry = null;
    }

    public void incrementPlayers(View view) {
        if (playerCount < 4) {
            playerCount = playerCount + 1;
            playerTeeAry[0] = playerCount;
            playerNameAry[0] = String.valueOf(playerCount);
            playerHandicapAry[0] = playerCount;
            saveEditTextVars();
            playerCountView.setText(String.valueOf(playerCount));
            switch (playerCount) {
                case 2:
                    playerTwoInfoView.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    playerThreeInfoView.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    playerFourInfoView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public void decrementPlayers(View view) {
        if (playerCount > 1) {
            playerCount = playerCount - 1;
            playerTeeAry[0] = playerCount;
            playerNameAry[0] = String.valueOf(playerCount);
            playerHandicapAry[0] = playerCount;
            saveEditTextVars();
            playerCountView.setText(String.valueOf(playerCount));
            switch (playerCount) {
                case 1:
                    playerTwoInfoView.setVisibility(View.GONE);
                    clearPlayerInfo(2);
                    break;
                case 2:
                    playerThreeInfoView.setVisibility(View.GONE);
                    clearPlayerInfo(3);
                    break;
                case 3:
                    playerFourInfoView.setVisibility(View.GONE);
                    clearPlayerInfo(4);
                    break;
            }
        }
    }

    public void clearPlayerInfo(int player) {
        RadioGroup teeRadioGroup = findViewById(getResources().getIdentifier("player_" + player + "_tee_group", "id", getPackageName()));
        teeRadioGroup.clearCheck();
    }

    public void editTextOnClick(View view) {
        saveEditTextVars();
    }

    public void saveEditTextVars() {
        for (int i = 1; i <= playerCount; i++) {
            EditText name = findViewById(getResources().getIdentifier("player_" + i + "_name", "id", getPackageName()));
            playerNameAry[i] = name.getText().toString();
            EditText handicapInput = findViewById(getResources().getIdentifier("player_" + i + "_handicap", "id", getPackageName()));
            int handicap = Integer.parseInt(handicapInput.getText().toString());
            if (handicap > 36.4) {
                Toast.makeText(this, "Per the USGA, the maximum handicap is 36.4.", Toast.LENGTH_SHORT).show();
            }
            playerHandicapAry[i] = handicap;
        }
    }

    public void setPlayerTee(View view) {
        saveEditTextVars();
        for (int i = 1; i <= playerCount; i++) {
            RadioButton hard = findViewById(getResources().getIdentifier("player_" + i + "_tee_2", "id", getPackageName()));
            RadioButton medium = findViewById(getResources().getIdentifier("player_" + i + "_tee_1", "id", getPackageName()));
            RadioButton easy = findViewById(getResources().getIdentifier("player_" + i + "_tee_0", "id", getPackageName()));
            if (hard.isChecked()) {
                playerTeeAry[i] = 2;
            } else if (medium.isChecked()) {
                playerTeeAry[i] = 1;
            } else if (easy.isChecked()) {
                playerTeeAry[i] = 0;
            }
        }
    }

    public void beginRound(View view) {
        for (int i = 1; i <= playerCount; i++) {
            if (playerHandicapAry[i] > 36.4) {
                Toast.makeText(this, "Per the USGA, the maximum handicap is 36.4.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!preserveScoreArrays) {
            switch (playerCount) {
                case 4:
                    player4Score = new int[holeCount + 1];
                case 3:
                    player3Score = new int[holeCount + 1];
                case 2:
                    player2Score = new int[holeCount + 1];
                case 1:
                    player1Score = new int[holeCount + 1];
                    break;
            }
        }
        saveEditTextVars();
        phase = "game";
        setContentView(R.layout.game_layout_portrait);
        buildGameObjects();
    }

    public void returnToPlayerInfo (View view) {
        phase = "initial";
        preserveScoreArrays = true;
        setContentView(R.layout.initial_layout_portrait);
        afterCourseSelection();
        buildInitialObjects();
    }

    public void setCourseArrays() {
        Resources resource = getResources();
        courseParAry = resource.getIntArray(resource.getIdentifier(course + "_par", "array", getPackageName()));
        courseDistanceEasyAry = resource.getIntArray(resource.getIdentifier(course + "_distance_0", "array", getPackageName()));
        courseDistanceMediumAry = resource.getIntArray(resource.getIdentifier(course + "_distance_1", "array", getPackageName()));
        courseDistanceHardAry = resource.getIntArray(resource.getIdentifier(course + "_distance_2", "array", getPackageName()));
        courseHoleNameAry = resource.getStringArray(resource.getIdentifier(course + "_hole_names", "array", getPackageName()));
        teeColorAry = resource.getStringArray(resource.getIdentifier("sa_tees", "array", this.getPackageName()));
        holeCount = courseParAry.length - 1;
    }

    public void buildGameObjects() {
        gameBackgroundImageView = findViewById(R.id.game_background_image);
        gameHoleNumberView = findViewById(R.id.hole_number);
        gameHoleNameView = findViewById(R.id.hole_name);
        player2ScoreGroup = findViewById(R.id.player_2_score_group);
        player3ScoreGroup = findViewById(R.id.player_3_score_group);
        player4ScoreGroup = findViewById(R.id.player_4_score_group);
        player1NameView = findViewById(R.id.player_1_score_name);
        player2NameView = findViewById(R.id.player_2_score_name);
        player3NameView = findViewById(R.id.player_3_score_name);
        player4NameView = findViewById(R.id.player_4_score_name);
        player1ScoreView = findViewById(R.id.player_1_score_score);
        player2ScoreView = findViewById(R.id.player_2_score_score);
        player3ScoreView = findViewById(R.id.player_3_score_score);
        player4ScoreView = findViewById(R.id.player_4_score_score);
        holePar = findViewById(R.id.hole_par);
        holeHardTeeDistance = findViewById(R.id.hole_hard_tee);
        holeMediumTeeDistance = findViewById(R.id.hole_medium_tee);
        holeEasyTeeDistance = findViewById(R.id.hole_easy_tee);
        holeMap = findViewById(R.id.hole_map_small);
        fullSizeMap = findViewById(R.id.full_screen_map);
        closeMap = findViewById(R.id.close_button);
        setGameObjects();
    }

    public void setGameObjects() {
        gameBackgroundImageView.setImageResource(getResources().getIdentifier("com.example.android.golfscoreapp:drawable/" + course + "_photo_hole_" + hole, null, null));
        String holeNumber = "Hole " + hole;
        gameHoleNumberView.setText(holeNumber);
        if (courseHoleNameAry[hole].length() <= 0) {
            gameHoleNameView.setVisibility(View.GONE);
        } else {
            gameHoleNameView.setVisibility(View.VISIBLE);
            gameHoleNameView.setText(courseHoleNameAry[hole]);
            gameHoleNumberView.setTextSize(20);
            gameHoleNameView.setTextSize(20);
        }
        String par = "Par " + String.valueOf(courseParAry[hole]);
        holePar.setText(par);
        String hardTeeDistance = String.valueOf(courseDistanceHardAry[hole]) + " Yards";
        holeHardTeeDistance.setText(hardTeeDistance);
        String mediumTeeDistance = String.valueOf(courseDistanceMediumAry[hole]) + " Yards";
        holeMediumTeeDistance.setText(mediumTeeDistance);
        String easyTeeDistance = String.valueOf(courseDistanceEasyAry[hole]) + " Yards";
        holeEasyTeeDistance.setText(easyTeeDistance);
        holeMap.setImageResource(getResources().getIdentifier("com.example.android.golfscoreapp:drawable/" + course + "_map_hole_" + hole, null, null));
        fullSizeMap.setImageResource(getResources().getIdentifier("com.example.android.golfscoreapp:drawable/" + course + "_map_hole_" + hole, null, null));
        switch (playerCount) {
            case 4:
                player4ScoreGroup.setVisibility(View.VISIBLE);
                player4NameView.setText(playerNameAry[4]);
                if (player4Score[hole] > 0) {
                    player4ScoreView.setText(String.valueOf(player4Score[hole]));
                } else {
                    player4ScoreView.setText(String.valueOf(0));
                }
            case 3:
                player3ScoreGroup.setVisibility(View.VISIBLE);
                player3NameView.setText(playerNameAry[3]);
                if (player3Score[hole] > 0) {
                    player3ScoreView.setText(String.valueOf(player3Score[hole]));
                } else {
                    player3ScoreView.setText(String.valueOf(0));
                }
            case 2:
                player2ScoreGroup.setVisibility(View.VISIBLE);
                player2NameView.setText(playerNameAry[2]);
                if (player2Score[hole] > 0) {
                    player2ScoreView.setText(String.valueOf(player2Score[hole]));
                } else {
                    player2ScoreView.setText(String.valueOf(0));
                }
            case 1:
                player1NameView.setText(playerNameAry[1]);
                if (player1Score[hole] > 0) {
                    player1ScoreView.setText(String.valueOf(player1Score[hole]));
                } else {
                    player1ScoreView.setText(String.valueOf(0));
                }
                break;
        }
    }

    public void incrementHole(View view) {
        if (hole < holeCount) {
            hole = hole + 1;
        } else {
            hole = 1;
        }
        setGameObjects();
    }

    public void decrementHole(View view) {
        if (hole > 1) {
            hole = hole - 1;
        } else {
            hole = holeCount;
        }
        setGameObjects();
    }

    public void fullSizeMap (View view) {
        fullSizeMap.setVisibility(View.VISIBLE);
        closeMap.setVisibility(View.VISIBLE);
    }

    public void closeFullMap (View view) {
        fullSizeMap.setVisibility(View.GONE);
        closeMap.setVisibility(View.GONE);
    }

    public void finishRound (View view) {
        phase = "final";
        setContentView(R.layout.final_layout_portrait);
        createFinalObjects();
    }

    public void backToScorecard (View view){
        phase = "game";
        setContentView(R.layout.game_layout_portrait);
        buildGameObjects();
    }

    public void createFinalObjects () {}

    public void clearScorecard (View view) {
        phase = "initial";
        preserveScoreArrays = false;
        initialViewState = 1;
        hole = 1;
        course = null;
        playerCount = 1;
        for (int i = 1 ; i <= 4 ; i++) {
            playerTeeAry[i] = 2;
        }
        for (int i = 1 ; i <= 4 ; i++) {
            playerNameAry[i] = "Player " + i;
        }
        for (int i = 0 ; i <= 4 ; i++) {
            playerHandicapAry[i] = 0;
        }
        courseParAry = null;
        courseHoleNameAry = null;
        courseDistanceEasyAry = null;
        courseDistanceMediumAry = null;
        courseDistanceHardAry = null;
        teeColorAry = null;
        holeCount = 0;
        player1Score = null;
        player2Score = null;
        player3Score = null;
        player4Score = null;
        setContentView(R.layout.initial_layout_portrait);
    }

    public void player1IncrementScore(View view) {
        player1Score[hole] = player1Score[hole] + 1;
        player1ScoreView.setText(String.valueOf(player1Score[hole]));
    }

    public void player2IncrementScore(View view) {
        player2Score[hole] = player2Score[hole] + 1;
        player2ScoreView.setText(String.valueOf(player2Score[hole]));
    }

    public void player3IncrementScore(View view) {
        player3Score[hole] = player3Score[hole] + 1;
        player3ScoreView.setText(String.valueOf(player3Score[hole]));
    }

    public void player4IncrementScore(View view) {
        player4Score[hole] = player4Score[hole] + 1;
        player4ScoreView.setText(String.valueOf(player4Score[hole]));
    }

    public void player1DecrementScore(View view) {
        if (player1Score[hole] > 0) {
            player1Score[hole] = player1Score[hole] - 1;
            player1ScoreView.setText(String.valueOf(player1Score[hole]));
        }
    }

    public void player2DecrementScore(View view) {
        if (player2Score[hole] > 0) {
            player2Score[hole] = player2Score[hole] - 1;
            player2ScoreView.setText(String.valueOf(player2Score[hole]));
        }
    }

    public void player3DecrementScore(View view) {
        if (player3Score[hole] > 0) {
            player3Score[hole] = player3Score[hole] - 1;
            player3ScoreView.setText(String.valueOf(player3Score[hole]));
        }
    }

    public void player4DecrementScore(View view) {
        if (player4Score[hole] > 0) {
            player4Score[hole] = player4Score[hole] - 1;
            player4ScoreView.setText(String.valueOf(player4Score[hole]));
        }
    }

    //On click method executed when St. Andrews Old Course is selected.
    public void setCourseSAOld(View view) {
        course = "sa_old";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Castle Course is selected.
    public void setCourseSACastle(View view) {
        course = "sa_castle";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews New Course is selected.
    public void setCourseSANew(View view) {
        course = "sa_new";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Jubilee Course is selected.
    public void setCourseSAJubilee(View view) {
        course = "sa_jubilee";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Edan Course is selected.
    public void setCourseSAEden(View view) {
        course = "sa_eden";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Strathtyrum Course is selected.
    public void setCourseSAStrathtyrum(View view) {
        course = "sa_strathtyrum";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Balgove Course is selected.
    public void setCourseSABalgove(View view) {
        course = "sa_balgove";
        setCourseArrays();
        afterCourseSelection();
    }

}
