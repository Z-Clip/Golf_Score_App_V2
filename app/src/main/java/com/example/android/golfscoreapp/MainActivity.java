package com.example.android.golfscoreapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
    public String[] playerTeeAry = new String[5];
    public String[] playerNameAry = new String[5];
    public int[] playerHandicapAry = new int[5];

    //Global object variables
    public ScrollView coursesViewGroup;
    public LinearLayout playerCountViewGroup;
    public TextView playerCountView;
    public ScrollView playerInfoViewGroup;
    public LinearLayout playerTwoInfoView;
    public LinearLayout playerThreeInfoView;
    public LinearLayout playerFourInfoView;
    public LinearLayout beginRoundViewGroup;

    // Save off key global variables on saveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hole" , hole);
        outState.putInt("initialViewState" , initialViewState);
        outState.putString("phase" , phase);
        outState.putString("course" , course);
        outState.putInt("playerCount" , playerCount);
        outState.putStringArray("playerTeeAry" , playerTeeAry);
        outState.putStringArray("playerNameAry" , playerNameAry);
        outState.putIntArray("playerHandicapAry" , playerHandicapAry);
    }

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_layout_portrait);
        rebuildState(savedInstanceState);  //Rebuild vars on a state change
        switch (phase) {
            case "initial":
                buildInitialObjects();
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
            playerTeeAry = savedInstanceState.getStringArray("playerTeeAry");
            playerNameAry = savedInstanceState.getStringArray("playerNameAry");
            playerHandicapAry = savedInstanceState.getIntArray("playerHandicapAry");
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
            if (phase.equals("initial")) {
                setContentView(R.layout.initial_layout_portrait);
                buildInitialObjects();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (phase.equals("initial")) {
                setContentView(R.layout.initial_layout_landscape);
                buildInitialObjects();
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
                break;
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
    }

    public void incrementPlayers (View view) {
        if (playerCount < 4) {
            playerCount = playerCount + 1;
            playerTeeAry[0] = String.valueOf(playerCount);
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
            playerTeeAry[0] = String.valueOf(playerCount);
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
        for (int i = 1; i <= playerCount; i++) {
            RadioButton hard = findViewById(getResources().getIdentifier("player_" + i + "_tee_hard", "id", getPackageName()));
            RadioButton medium = findViewById(getResources().getIdentifier("player_" + i + "_tee_medium", "id", getPackageName()));
            RadioButton easy = findViewById(getResources().getIdentifier("player_" + i + "_tee_easy", "id", getPackageName()));
            if (hard.isChecked()) {
                playerTeeAry [i] = String.valueOf(hard.getText());
            } else if (medium.isChecked()) {
                playerTeeAry [i] = String.valueOf(medium.getText());
            } else if (easy.isChecked()) {
                playerTeeAry [i] = String.valueOf(easy.getText());
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
    }

    //On click method executed when St. Andrews Old Course is selected.
    public void setCourseSAOld (View view) {
        course="sa_old";
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Castle Course is selected.
    public void setCourseSACastle (View view) {
        course="sa_castle";
        afterCourseSelection();
    }

    //On click method executed when St. Andrews New Course is selected.
    public void setCourseSANew (View view) {
        course="sa_new";
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Jubilee Course is selected.
    public void setCourseSAJubilee (View view) {
        course="sa_jubilee";
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Edan Course is selected.
    public void setCourseSAEden (View view) {
        course="sa_eden";
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Strathtyrum Course is selected.
    public void setCourseSAStrathtyrum (View view) {
        course="sa_strathtyrum";
        afterCourseSelection();
    }

    //On click method executed when St. Andrews Balgove Course is selected.
    public void setCourseSABalgove (View view) {
        course="sa_balgove";
        afterCourseSelection();
    }

}
