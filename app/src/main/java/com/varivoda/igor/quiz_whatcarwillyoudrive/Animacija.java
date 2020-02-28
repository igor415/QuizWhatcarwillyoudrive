package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Animacija extends Animation {

    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from, to;

    public Animacija(Context context, ProgressBar progressBar, TextView textView, float from, float to) {
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
        this.context = context;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        int d = (int) value;
        textView.setText(context.getResources().getString(R.string.postotak_res, String.valueOf(d)));

    }
}