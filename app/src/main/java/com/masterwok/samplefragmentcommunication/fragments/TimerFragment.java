package com.masterwok.samplefragmentcommunication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.masterwok.samplefragmentcommunication.R;
import com.masterwok.samplefragmentcommunication.utils.ThreadUtil;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


/**
 * This fragment is responsible for showing a timer that counts up each second.
 * On creation, it should start from the provided time argument.
 */
public class TimerFragment
        extends Fragment {

    public static final String BaseExtra = "extra.base";

    private Chronometer chronometer;
    private SimpleDateFormat timeFormat;

    public class ChronometerState {

        private long base;

        private ChronometerState(long base) {
            this.base = base;
        }

        public long getBase() {
            return base;
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(
                R.layout.fragment_timer,
                container,
                false
        );

        bindViewComponents(view);
        initTimeFormat();
        initChronometer();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        chronometer.start();
    }

    private void bindViewComponents(View view) {
        chronometer = view.findViewById(R.id.chronometer);
    }

    @SuppressLint("SimpleDateFormat")
    private void initTimeFormat() {
        timeFormat = new SimpleDateFormat("H:mm:ss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public void initChronometer() {
        Bundle args = getArguments();

        chronometer.setBase(args == null
                ? SystemClock.elapsedRealtime()
                : args.getLong(
                BaseExtra,
                SystemClock.elapsedRealtime()
        ));

        chronometer.setOnChronometerTickListener(c -> {
            configure(SystemClock.elapsedRealtime() - c.getBase());
        });
    }

    private void configure(long elapsedTime) {
        ThreadUtil.onMain(() -> chronometer.setText(
                timeFormat.format(elapsedTime)
        ));
    }

    /**
     * This method is invoked by the parent activity to retrieve
     * the state of the chronometer.
     *
     * @return The timer state.
     */
    public ChronometerState getTimerState() {
        // Was never shown, return null.
        if (chronometer == null) {
            return null;
        }

        return new ChronometerState(chronometer.getBase());
    }
}
