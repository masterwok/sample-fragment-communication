package com.masterwok.samplefragmentcommunication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;

import com.masterwok.samplefragmentcommunication.fragments.TimerFragment;


/**
 * This activity demonstrates design used for communication between
 * fragments and activities. This design is only one of many, and is
 * probably the simplest. In this example, state is passed into the fragments
 * from the activity using primitive bundle arguments. To read state from the
 * fragment, the activity invokes a method defined on the fragments themselves.
 * This method is demonstrated by using a timer whose state is passed between
 * fragments when they are destroyed and replaced.
 * <p>
 * Some other methods worth checking out:
 * 1. Using an EventBus (e.g. GreenRobot).
 * 2. Defining a public callback interface in a fragment to be implemented by
 * the activity. The fragment can then check if its parent activity
 * implements the interface before invoking the callbacks.
 */
public class MainActivity extends FragmentActivity {

    private TimerFragment timerFragmentOne;
    private TimerFragment timerFragmentTwo;

    private AppCompatButton buttonOne;
    private AppCompatButton buttonTwo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initChronometer();
        bindViewComponents();
        subscribeToViewComponents();
        initFragments();

        showFragmentOne();
    }

    private void initChronometer() {
    }

    private void initFragments() {
        timerFragmentOne = new TimerFragment();
        timerFragmentTwo = new TimerFragment();
    }

    private void bindViewComponents() {
        buttonOne = findViewById(R.id.button_one);
        buttonTwo = findViewById(R.id.button_two);
    }

    private void subscribeToViewComponents() {
        buttonOne.setOnClickListener(view -> showFragmentOne());
        buttonTwo.setOnClickListener(view -> showFragmentTwo());
    }

    private void showFragmentOne() {
        TimerFragment.ChronometerState chronometerState = timerFragmentTwo.getTimerState();

        timerFragmentOne.setArguments(createFragmentArgs(chronometerState));

        setContainerFragment(timerFragmentOne);
    }

    private void showFragmentTwo() {
        TimerFragment.ChronometerState chronometerState = timerFragmentOne.getTimerState();

        timerFragmentTwo.setArguments(createFragmentArgs(chronometerState));

        setContainerFragment(timerFragmentTwo);
    }

    private Bundle createFragmentArgs(TimerFragment.ChronometerState chronometerState) {
        if (chronometerState == null) {
            return null;
        }

        Bundle bundle = new Bundle();

        bundle.putLong(
                TimerFragment.BaseExtra,
                chronometerState.getBase()
        );

        return bundle;
    }

    private void setContainerFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout_fragment_container, fragment)
                .commit();
    }
}
