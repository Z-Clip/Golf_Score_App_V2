package com.example.android.golfscoreapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Global variables
    public String phase = "initial";
    public int hole = 1;
    public String course;
    public int playerCount = 1;
    public String[] playerTeeAry = new String[5];
    public String[] playerNameAry = new String[5];

    //Global object variables
    public ScrollView coursesViewGroup;
    public LinearLayout playerCountViewGroup;
    public TextView playerCountView;
    public ScrollView playerInfoViewGroup;
    public LinearLayout playerTwoInfoView;
    public LinearLayout playerThreeInfoView;
    public LinearLayout playerFourInfoView;
    public Button beginRound;

    // Save off key global variables on saveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hole" , hole);
        outState.putString("phase" , phase);
        outState.putString("course" , course);
        outState.putInt("playerCount" , playerCount);
        outState.putStringArray("playerTee" , playerTeeAry);
    }

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_layout_portrait);
        rebuildState(savedInstanceState);  //Rebuild vars on a state change
        buildInitialObjects();
    }

    //Rebuild key global variables on a state change
    public void rebuildState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            hole = savedInstanceState.getInt("hole" , hole);
            phase = savedInstanceState.getString("phase" , phase);
            course = savedInstanceState.getString("course" , course);
            playerCount = savedInstanceState.getInt("playerCount" , playerCount);
            playerTeeAry = savedInstanceState.getStringArray("playerTee");
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
            if (phase.equals("inital")) {
                setContentView(R.layout.initial_layout_portrait);
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (phase.equals("inital")) {
                setContentView(R.layout.initial_layout_landscape);
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
        beginRound=findViewById(R.id.begin_round);
    }

    //On click method executed when a course is selected. Sets visibilities of views.
    public void afterCourseSelection () {
        coursesViewGroup.setVisibility(View.GONE);
        playerCountViewGroup.setVisibility(View.VISIBLE);
        playerInfoViewGroup.setVisibility(View.VISIBLE);
        beginRound.setVisibility(View.VISIBLE);
    }

    public void incrementPlayers (View view) {
        if (playerCount < 4) {
            playerCount = playerCount + 1;
            playerTeeAry[0] = String.valueOf(playerCount);
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
            playerTeeAry[playerCount+1] = null;
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
