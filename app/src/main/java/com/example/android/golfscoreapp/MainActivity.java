package com.example.android.golfscoreapp;

import android.content.Context;
import android.content.res.Configuration;
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
    public int[] playerTeeAry = {0,2,2,2,2};
    public String[] playerNameAry = {"","Player 1","Player 2","Player 3","Player 4"};
    public int[] playerHandicapAry = {0,0,0,0,0};
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

    // Save off key global variables on saveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hole" , hole);
        outState.putInt("initialViewState" , initialViewState);
        outState.putString("phase" , phase);
        outState.putString("course" , course);
        outState.putInt("playerCount" , playerCount);
        outState.putIntArray("playerTeeAry" , playerTeeAry);
        outState.putStringArray("playerNameAry" , playerNameAry);
        outState.putIntArray("playerHandicapAry" , playerHandicapAry);
        outState.putIntArray("courseParAry" , courseParAry);
        outState.putIntArray("courseDistanceEasyAry" , courseDistanceEasyAry);
        outState.putIntArray("courseDistanceMediumAry" , courseDistanceMediumAry);
        outState.putIntArray("courseDistanceHardAry" , courseDistanceHardAry);
        outState.putStringArray("courseHoleNameAry" , courseHoleNameAry);
        outState.putStringArray("teeColorAry" , teeColorAry);
        outState.putInt("holeCount" , holeCount);
        outState.putIntArray("player1Score" , player1Score);
        outState.putIntArray("player2Score" , player2Score);
        outState.putIntArray("player3Score" , player3Score);
        outState.putIntArray("player4Score" , player4Score);
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
        }
    }

    //Rebuild key global variables on a state change
    public void rebuildState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            hole = savedInstanceState.getInt("hole" , hole);
            initialViewState = savedInstanceState.getInt("initialViewState");
            phase = savedInstanceState.getString("phase" , phase);
            course = savedInstanceState.getString("course" , course);
            playerCount = savedInstanceState.getInt("playerCount" , playerCount);
            playerTeeAry = savedInstanceState.getIntArray("playerTeeAry");
            playerNameAry = savedInstanceState.getStringArray("playerNameAry");
            playerHandicapAry = savedInstanceState.getIntArray("playerHandicapAry");
            courseParAry = savedInstanceState.getIntArray("courseParAry");
            courseDistanceEasyAry = savedInstanceState.getIntArray("courseDistanceEasyAry");
            courseDistanceMediumAry = savedInstanceState.getIntArray("courseDistanceMediumAry");
            courseDistanceHardAry = savedInstanceState.getIntArray("courseDistanceHardAry");
            courseHoleNameAry = savedInstanceState.getStringArray("courseHoleNameAry");
            teeColorAry = savedInstanceState.getStringArray("teeColorAry");
            holeCount = savedInstanceState.getInt("holeCount)" , holeCount);
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
    public void changeLayoutBasedOnOrientation (Configuration newConfig) {
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
            }
        }
    }

    public void buildInitialObjects () {
        coursesViewGroup=findViewById(R.id.course_selection_view_group);
        playerCountViewGroup=findViewById(R.id.player_count_view_group);
        playerCountView=findViewById(R.id.player_count_view);
        playerCountView.setText(String.valueOf(playerCount));
        playerInfoViewGroup=findViewById(R.id.player_info_view_group);
        playerTwoInfoView=findViewById(R.id.player_2_info);
        playerThreeInfoView=findViewById(R.id.player_3_info);
        playerFourInfoView=findViewById(R.id.player_4_info);
        beginRoundViewGroup=findViewById(R.id.button_layout);
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

    public void setEditTextViewText () {
        for (int i = 1 ; i <= playerCount ; i++) {
            EditText nameView = findViewById(getResources().getIdentifier("player_" + i + "_name", "id", getPackageName()));
            nameView.setText(playerNameAry[i]);
            EditText handicapView = findViewById(getResources().getIdentifier("player_" + i + "_handicap", "id", getPackageName()));
            handicapView.setText(String.valueOf(playerHandicapAry[i]));
            RadioButton teeView = findViewById(getResources().getIdentifier("player_" + i + "_tee_" + playerTeeAry[i], "id", getPackageName()));
            teeView.setChecked(true);
        }
    }

    //On click method executed when a course is selected. Sets visibilities of views.
    public void afterCourseSelection () {
        initialViewState = 2;
        coursesViewGroup.setVisibility(View.GONE);
        playerCountViewGroup.setVisibility(View.VISIBLE);
        playerInfoViewGroup.setVisibility(View.VISIBLE);
        beginRoundViewGroup.setVisibility(View.VISIBLE);
    }

    public void returnToCourseSelection (View view) {
        initialViewState = 1;
        coursesViewGroup.setVisibility(View.VISIBLE);
        playerCountViewGroup.setVisibility(View.GONE);
        playerInfoViewGroup.setVisibility(View.GONE);
        beginRoundViewGroup.setVisibility(View.GONE);
        courseParAry=null;
        courseDistanceEasyAry=null;
        courseDistanceMediumAry=null;
        courseDistanceHardAry=null;
        courseHoleNameAry=null;
        teeColorAry=null;
    }

    public void incrementPlayers (View view) {
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

    public void decrementPlayers (View view) {
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

    public void clearPlayerInfo (int player) {
        RadioGroup teeRadioGroup = findViewById(getResources().getIdentifier("player_" + player + "_tee_group", "id", getPackageName()));
        teeRadioGroup.clearCheck();
    }

    public void editTextOnClick (View view) {
        saveEditTextVars();
    }

    public void saveEditTextVars () {
        for (int i = 1; i <= playerCount; i++) {
            EditText name = findViewById(getResources().getIdentifier("player_" + i + "_name", "id", getPackageName()));
            playerNameAry[i] = name.getText().toString();
            EditText handicapInput = findViewById(getResources().getIdentifier("player_" + i + "_handicap", "id", getPackageName()));
            int handicap = Integer.parseInt(handicapInput.getText().toString());
            if (handicap > 36.4) {
                Toast.makeText(this,"Per the USGA, the maximum handicap is 36.4.", Toast.LENGTH_SHORT).show();
            }
            playerHandicapAry[i] = handicap;
        }
    }

    public void setPlayerTee (View view) {
        saveEditTextVars();
        for (int i = 1; i <= playerCount; i++) {
            RadioButton hard = findViewById(getResources().getIdentifier("player_" + i + "_tee_2", "id", getPackageName()));
            RadioButton medium = findViewById(getResources().getIdentifier("player_" + i + "_tee_1", "id", getPackageName()));
            RadioButton easy = findViewById(getResources().getIdentifier("player_" + i + "_tee_0", "id", getPackageName()));
            if (hard.isChecked()) {
                playerTeeAry [i] = 2;
            } else if (medium.isChecked()) {
                playerTeeAry [i] = 1;
            } else if (easy.isChecked()) {
                playerTeeAry [i] = 0;
            }
        }
    }

    public void beginRound(View view) {
        for (int i = 1; i <= playerCount; i++) {
            if (playerHandicapAry[i] > 36.4) {
                Toast.makeText(this,"Per the USGA, the maximum handicap is 36.4.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        saveEditTextVars();
        phase = "game";
        setContentView(R.layout.game_layout_portrait);
        buildGameObjects();
    }

    public void setCourseArrays() {
        courseParAry=getResources().getIntArray(getResources().getIdentifier(course +"_par", "array", getPackageName()));
        courseDistanceEasyAry=getResources().getIntArray(getResources().getIdentifier(course +"_distance_0", "array", getPackageName()));
        courseDistanceMediumAry=getResources().getIntArray(getResources().getIdentifier(course +"_distance_1", "array", getPackageName()));
        courseDistanceHardAry=getResources().getIntArray(getResources().getIdentifier(course +"_distance_2", "array", getPackageName()));
        courseHoleNameAry=getResources().getStringArray(getResources().getIdentifier(course +"_hole_names", "array", getPackageName()));
        teeColorAry=getResources().getStringArray(getResources().getIdentifier("sa_tees", "array", getPackageName()));
        holeCount = courseParAry.length - 1;
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
        switch (playerCount) {
            case 4:
                player4ScoreGroup.setVisibility(View.VISIBLE);
                player4NameView.setText(playerNameAry[4]);
            case 3:
                player3ScoreGroup.setVisibility(View.VISIBLE);
                player3NameView.setText(playerNameAry[3]);
            case 2:
                player2ScoreGroup.setVisibility(View.VISIBLE);
                player2NameView.setText(playerNameAry[2]);
            case 1:
                player1NameView.setText(playerNameAry[1]);
                break;
        }
    }

    public void incrementHole(View view){
        if (hole < holeCount) {
            hole = hole + 1;
        } else {
            hole = 1;
        }
        setGameObjects();
    }

    public void decrementHole (View view) {
        if (hole > 1) {
            hole = hole -1;
        } else {
            hole = holeCount;
        }
        setGameObjects();
    }

    //On click method executed when St. Andrews Old Course is selected.
    public void setCourseSAOld (View view) {
        course="sa_old";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Castle Course is selected.
    public void setCourseSACastle (View view) {
        course="sa_castle";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews New Course is selected.
    public void setCourseSANew (View view) {
        course="sa_new";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Jubilee Course is selected.
    public void setCourseSAJubilee (View view) {
        course="sa_jubilee";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Edan Course is selected.
    public void setCourseSAEden (View view) {
        course="sa_eden";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Strathtyrum Course is selected.
    public void setCourseSAStrathtyrum (View view) {
        course="sa_strathtyrum";
        setCourseArrays();
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Balgove Course is selected.
    public void setCourseSABalgove (View view) {
        course="sa_balgove";
        setCourseArrays();
        afterCourseSelection();
    }

}
