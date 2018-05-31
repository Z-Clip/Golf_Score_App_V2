/* This application is the intellectual property of Jonathan Leverkuhn and is not to be used without
 * his written permission.
 *
 * This application, in its current state is a demo and is not intended for public distribution.
 * St. Andrews Links has graciously authorized me to utilize their intellectual property (including
 * images, course information, and their logo) so long as the app is not made public or misrepresented
 * as an extension of their brand. The code below is mine (Jonathan Leverkuhn's) and was all hand-written,
 * but the drawable and array resources contain data that does not belong to me.
 */

package com.example.android.golfscoreapp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    public ImageView fullSizeMap;
    public Button closeMap;
    public LinearLayout finalPlayer1Info;
    public LinearLayout finalPlayer2Info;
    public LinearLayout finalPlayer3Info;
    public LinearLayout finalPlayer4Info;
    public TextView finalPlayer1Name;
    public TextView finalPlayer2Name;
    public TextView finalPlayer3Name;
    public TextView finalPlayer4Name;
    public TextView finalPlayer1ParSoFar;
    public TextView finalPlayer2ParSoFar;
    public TextView finalPlayer3ParSoFar;
    public TextView finalPlayer4ParSoFar;
    public TextView finalPlayer1StrokeCount;
    public TextView finalPlayer2StrokeCount;
    public TextView finalPlayer3StrokeCount;
    public TextView finalPlayer4StrokeCount;
    public TextView finalPlayer1Tee;
    public TextView finalPlayer2Tee;
    public TextView finalPlayer3Tee;
    public TextView finalPlayer4Tee;

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

    //Change layout based on screen orientation and phase.
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
                    setContentView(R.layout.game_layout_landscape);
                    buildGameObjects();
                    break;
                case "final":
                    setContentView(R.layout.final_layout_landscape);
                    createFinalObjects();
                    break;
            }
        }
    }

    //Defines variables for the objects in the initial layout and sets their properties.
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

    /*If values are already defined for player name, handicap, or tee, populate those views in
     *the initial layout accordingly. This is mostly applicable if the user toggles back to the initial
     * layout from the game layout.
     */
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

    /*Executed if the user navigates back to the course selection screen. If clears all the course
     *variables/arrays.
     */
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

    //Executed when the user adds players to the player count.
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

    //Executed when the user removes players from the player count.
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

    //Executed when the player count is decremented. Clears variables for the player removed.
    public void clearPlayerInfo(int player) {
        RadioGroup teeRadioGroup = findViewById(getResources().getIdentifier("player_" + player + "_tee_group", "id", getPackageName()));
        teeRadioGroup.clearCheck();
    }

    /*This is actually defined in a style resource, so it only looks like it's not called to the IDE.
     *This is simply an attempt to add additional points at which EditText information is saved off to vars.
     *This is important if the layout changes due to screen rotation because we don't want information
     * that has already been entered to be lost.
     */
    public void editTextOnClick(View view) {
        saveEditTextVars();
    }

    //Gets text for the EditText views in the initial layout and saves the text to variables and/or arrays.
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

    //Builds the playerTeeAry array based on the information provided in the tee views on the initial layout.
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

    /*Prevents users from entering a handicap greater than the USGA max. Does not currently account for
     *gender, but it will in subsequent versions.
     * Saves off any text entered in the edit text views.
     * Changes the layout to the game layout.
     * Sets the phase to game and executes object variables creation and population for the game layout.
     */
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

    //Sets the phase back to "initial", changes the layout to initial, and re-defines object variables for that layout.
    public void returnToPlayerInfo (View view) {
        phase = "initial";
        preserveScoreArrays = true;
        setContentView(R.layout.initial_layout_portrait);
        afterCourseSelection();
        buildInitialObjects();
    }

    //Defines array variables for the par, distance, name, and tee color based on the array resources associated with the course.
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

    //Sets variables associated with the various objects in the game layout.
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

    //Defines values for the various views in the game layout.
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

    //Increments the hole and redefines values for the various views in the game layout.
    public void incrementHole(View view) {
        if (hole < holeCount) {
            hole = hole + 1;
        } else {
            hole = 1;
        }
        setGameObjects();
    }

    //Decrements the hole and redefines values for the various views in the game layout.
    public void decrementHole(View view) {
        if (hole > 1) {
            hole = hole - 1;
        } else {
            hole = holeCount;
        }
        setGameObjects();
    }

    //Toggles the full size map views.
    public void fullSizeMap (View view) {
        fullSizeMap.setVisibility(View.VISIBLE);
        closeMap.setVisibility(View.VISIBLE);
    }

    //"Gone-s" the full size map views.
    public void closeFullMap (View view) {
        fullSizeMap.setVisibility(View.GONE);
        closeMap.setVisibility(View.GONE);
    }

    //Sets phase to final, switches to the final layout, and executes the creation of object variables.
    public void finishRound (View view) {
        phase = "final";
        setContentView(R.layout.final_layout_portrait);
        createFinalObjects();
    }

    //Sets phase to game, switches to the game layout, and executes the creation of object variables.
    public void backToScorecard (View view){
        phase = "game";
        setContentView(R.layout.game_layout_portrait);
        buildGameObjects();
    }

    //Sets vars for the final vews, and then populates those views dynamically.
    public void createFinalObjects () {
        switch (playerCount) {
            case 4:
                finalPlayer4Info = findViewById(R.id.final_player_4_info);
                finalPlayer4Info.setVisibility(View.VISIBLE);
                finalPlayer4Name = findViewById(R.id.final_player_4_name);
                finalPlayer4Name.setText(playerNameAry[4]);
                int parSoFar4 = 0;
                for (int i = 1 ; i < courseParAry.length ; i++) {
                    if (player4Score[i] > 0) {
                        parSoFar4 = parSoFar4 + courseParAry[i];
                    }
                }
                finalPlayer4ParSoFar = findViewById(R.id.player_4_par_so_far);
                finalPlayer4ParSoFar.setText(String.valueOf(parSoFar4));
                finalPlayer4StrokeCount = findViewById(R.id.player_4_stroke_count);
                int strokes4 = 0;
                for (int i = 1 ; i < player4Score.length ; i++) {
                    strokes4 = player4Score[i] + strokes4;
                }
                finalPlayer4StrokeCount.setText(String.valueOf(strokes4));
                finalPlayer4Tee = findViewById(R.id.player_4_tee);
                String tee4 = teeColorAry[playerTeeAry[4]];
                finalPlayer4Tee.setText(tee4);
            case 3:
                finalPlayer3Info = findViewById(R.id.final_player_3_info);
                finalPlayer3Info.setVisibility(View.VISIBLE);
                finalPlayer3Name = findViewById(R.id.final_player_3_name);
                finalPlayer3Name.setText(playerNameAry[3]);
                int parSoFar3 = 0;
                for (int i = 1 ; i < courseParAry.length ; i++) {
                    if (player3Score[i] > 0) {
                        parSoFar3 = parSoFar3 + courseParAry[i];
                    }
                }
                finalPlayer3ParSoFar = findViewById(R.id.player_3_par_so_far);
                finalPlayer3ParSoFar.setText(String.valueOf(parSoFar3));
                finalPlayer3StrokeCount = findViewById(R.id.player_3_stroke_count);
                int strokes3 = 0;
                for (int i = 1 ; i < player3Score.length ; i++) {
                    strokes3 = player3Score[i] + strokes3;
                }
                finalPlayer3StrokeCount.setText(String.valueOf(strokes3));
                finalPlayer3Tee = findViewById(R.id.player_3_tee);
                String tee3 = teeColorAry[playerTeeAry[3]];
                finalPlayer3Tee.setText(tee3);
            case 2:
                finalPlayer2Info = findViewById(R.id.final_player_2_info);
                finalPlayer2Info.setVisibility(View.VISIBLE);
                finalPlayer2Name = findViewById(R.id.final_player_2_name);
                finalPlayer2Name.setText(playerNameAry[2]);
                int parSoFar2 = 0;
                for (int i = 1 ; i < courseParAry.length ; i++) {
                    if (player2Score[i] > 0) {
                        parSoFar2 = parSoFar2 + courseParAry[i];
                    }
                }
                finalPlayer2ParSoFar = findViewById(R.id.player_2_par_so_far);
                finalPlayer2ParSoFar.setText(String.valueOf(parSoFar2));
                finalPlayer2StrokeCount = findViewById(R.id.player_2_stroke_count);
                int strokes2 = 0;
                for (int i = 1 ; i < player2Score.length ; i++) {
                    strokes2 = player2Score[i] + strokes2;
                }
                finalPlayer2StrokeCount.setText(String.valueOf(strokes2));
                finalPlayer2Tee = findViewById(R.id.player_2_tee);
                String tee2 = teeColorAry[playerTeeAry[2]];
                finalPlayer2Tee.setText(tee2);
            case 1:
                finalPlayer1Info = findViewById(R.id.final_player_1_info);
                finalPlayer1Info.setVisibility(View.VISIBLE);
                finalPlayer1Name = findViewById(R.id.final_player_1_name);
                finalPlayer1Name.setText(playerNameAry[1]);
                int parSoFar1 = 0;
                for (int i = 1 ; i < courseParAry.length ; i++) {
                    if (player1Score[i] > 0) {
                        parSoFar1 = parSoFar1 + courseParAry[i];
                    }
                }
                finalPlayer1ParSoFar = findViewById(R.id.player_1_par_so_far);
                finalPlayer1ParSoFar.setText(String.valueOf(parSoFar1));
                finalPlayer1StrokeCount = findViewById(R.id.player_1_stroke_count);
                int strokes1 = 0;
                for (int i = 1 ; i < player1Score.length ; i++) {
                    strokes1 = player1Score[i] + strokes1;
                }
                finalPlayer1StrokeCount.setText(String.valueOf(strokes1));
                finalPlayer1Tee = findViewById(R.id.player_1_tee);
                String tee1 = teeColorAry[playerTeeAry[1]];
                finalPlayer1Tee.setText(tee1);
                break;
        }
    }

    //Clears ALL variables and sets the view back to initial
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
        buildInitialObjects();
    }

    //Increments the value in the array node whose key corresponds with the current hole.
    public void player1IncrementScore(View view) {
        player1Score[hole] = player1Score[hole] + 1;
        player1ScoreView.setText(String.valueOf(player1Score[hole]));
    }

    //Increments the value in the array node whose key corresponds with the current hole.
    public void player2IncrementScore(View view) {
        player2Score[hole] = player2Score[hole] + 1;
        player2ScoreView.setText(String.valueOf(player2Score[hole]));
    }

    //Increments the value in the array node whose key corresponds with the current hole.
    public void player3IncrementScore(View view) {
        player3Score[hole] = player3Score[hole] + 1;
        player3ScoreView.setText(String.valueOf(player3Score[hole]));
    }

    //Increments the value in the array node whose key corresponds with the current hole.
    public void player4IncrementScore(View view) {
        player4Score[hole] = player4Score[hole] + 1;
        player4ScoreView.setText(String.valueOf(player4Score[hole]));
    }

    //Decrements the value in the array node whose key corresponds with the current hole.
    public void player1DecrementScore(View view) {
        if (player1Score[hole] > 0) {
            player1Score[hole] = player1Score[hole] - 1;
            player1ScoreView.setText(String.valueOf(player1Score[hole]));
        }
    }

    //Decrements the value in the array node whose key corresponds with the current hole.
    public void player2DecrementScore(View view) {
        if (player2Score[hole] > 0) {
            player2Score[hole] = player2Score[hole] - 1;
            player2ScoreView.setText(String.valueOf(player2Score[hole]));
        }
    }

    //Decrements the value in the array node whose key corresponds with the current hole.
    public void player3DecrementScore(View view) {
        if (player3Score[hole] > 0) {
            player3Score[hole] = player3Score[hole] - 1;
            player3ScoreView.setText(String.valueOf(player3Score[hole]));
        }
    }

    //Decrements the value in the array node whose key corresponds with the current hole.
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
