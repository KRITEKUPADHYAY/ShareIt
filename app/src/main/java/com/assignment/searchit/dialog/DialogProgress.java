package com.assignment.searchit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.assignment.searchit.R;

public class DialogProgress extends Dialog {

    public DialogProgress(@NonNull Context context) {
        super(context, R.style.Loader);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}