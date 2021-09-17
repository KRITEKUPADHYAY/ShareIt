package com.assignment.searchit.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.assignment.searchit.R;
import com.squareup.picasso.Picasso;


public class DialogFullImage extends DialogFragment {


    public DialogFullImage() {
        // Required empty public constructor
    }

    ImageView imgImage, imgClose;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int w = ViewGroup.LayoutParams.MATCH_PARENT;
            int h = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(w, h);
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.full_screen_bg));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_full_image, container, false);

        imgClose = view.findViewById(R.id.imgFullScreenClose);
        imgImage = view.findViewById(R.id.imgFullScreenImage);

        try {
            String url = getArguments().getString("url", "noImage");
            Log.d("hello","Full Screen Url : "+url);
            Picasso.get().load(url).error(R.drawable.ic_thumbnail).into(imgImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }
}