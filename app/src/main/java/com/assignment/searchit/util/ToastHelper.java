package com.assignment.searchit.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.searchit.R;

public class ToastHelper {

    public void makeToast(Context context, String message, int length){
        View toastView = LayoutInflater.from(context).inflate(R.layout.toast, null);
        Toast toast = new Toast(context);
        TextView textView = toastView.findViewById(R.id.toast_text);
        textView.setText(message);
        toast.setView(toastView);
        toast.setDuration(length);
        toast.show();
    }

}
