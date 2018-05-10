package com.example.android.golfscoreapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Global variables
    public String phase = "initial";
    public int hole = 1;
    public String course;

    //Global object variables
    public View coursesViewGroup;

    // Save off key global variables on saveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hole" , hole);
        outState.putString("phase" , phase);
        outState.putString("course" , course);
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
            hole = savedInstanceState.getInt("hole", hole);
            phase = savedInstanceState.getString("phase" , phase);
            course = savedInstanceState.getString("course" , course);
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
    }

    //On click method executed when St. Andrews Castle Course is selected.
    public void setCourseSACastle (View view) {
        course="castle";
        coursesViewGroup.setVisibility(View.GONE);
    }

}
