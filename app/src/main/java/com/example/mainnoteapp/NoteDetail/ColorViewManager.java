package com.example.mainnoteapp.NoteDetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mainnoteapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class ColorViewManager implements View.OnClickListener {

    LinearLayout mSheetView;
    ColorViewManagerCallback mCallback;
    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    ImageView imageColor1, imageColor2, imageColor3, imageColor4, imageColor5, imageColor6;

    public ColorViewManager(LinearLayout sheetView, ColorViewManagerCallback callback) {
        this.mSheetView = sheetView;
        this.mCallback = callback;
        initialize();
    }

    private void initialize() {
        setupBottomSheetBehaviour();
        setupColorImageViews();
    }

    private void setupBottomSheetBehaviour() {
        bottomSheetBehavior = BottomSheetBehavior.from(mSheetView);
        mSheetView.findViewById(R.id.textMiscellaneous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
    }

    private void setupColorImageViews() {
        imageColor1 = mSheetView.findViewById(R.id.imageColor1);
        imageColor2 = mSheetView.findViewById(R.id.imageColor2);
        imageColor3 = mSheetView.findViewById(R.id.imageColor3);
        imageColor4 = mSheetView.findViewById(R.id.imageColor4);
        imageColor5 = mSheetView.findViewById(R.id.imageColor5);
        imageColor6 = mSheetView.findViewById(R.id.imageColor6);

        View viewColor1 = mSheetView.findViewById(R.id.viewColor1);
        View viewColor2 = mSheetView.findViewById(R.id.viewColor2);
        View viewColor3 = mSheetView.findViewById(R.id.viewColor3);
        View viewColor4 = mSheetView.findViewById(R.id.viewColor4);
        View viewColor5 = mSheetView.findViewById(R.id.viewColor5);
        View viewColor6 = mSheetView.findViewById(R.id.viewColor6);

        viewColor1.setOnClickListener(ColorViewManager.this);
        viewColor2.setOnClickListener(ColorViewManager.this);
        viewColor3.setOnClickListener(ColorViewManager.this);
        viewColor4.setOnClickListener(ColorViewManager.this);
        viewColor5.setOnClickListener(ColorViewManager.this);
        viewColor6.setOnClickListener(ColorViewManager.this);
    }

    private void didTapOnImageWithIndex(int indexSelected) {
        Integer[] colorList = {R.color.colorNoteColor1, R.color.colorNoteColor2,
                R.color.colorNoteColor3, R.color.colorNoteColor4,
                R.color.colorNoteColor5, R.color.colorNoteColor6};

        Integer colorSelected = colorList[indexSelected];

        ImageView imageViews[] = {imageColor1 ,imageColor2 ,imageColor3 ,imageColor4 ,imageColor5 ,imageColor6};

        for (int i = 0; i < imageViews.length; i++) {
            ImageView currentImageView = imageViews[i];

            if (i== indexSelected) {
                currentImageView.setImageResource(R.drawable.ic_done);
            } else {
                currentImageView.setImageResource(0);
            }
        }
        mCallback.applyColor(colorSelected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewColor1: {
                didTapOnImageWithIndex(0);
                break;
            }
            case R.id.viewColor2: {
                didTapOnImageWithIndex(1);
                break;
            }
            case R.id.viewColor3: {
                didTapOnImageWithIndex(2);
                break;
            }
            case R.id.viewColor4: {
                didTapOnImageWithIndex(3);
                break;
            }
            case R.id.viewColor5: {
                didTapOnImageWithIndex(4);
                break;
            }
            case R.id.viewColor6: {
                didTapOnImageWithIndex(5);
                break;
            }
        }
    }

    public interface ColorViewManagerCallback {

        void applyColor(int color);
    }
}
