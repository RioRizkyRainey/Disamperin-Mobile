package com.disamperin.mobile;

import android.view.View;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 * Created by Rio Rizky Rainey on 26/03/2015.
 * rizkyrainey@gmail.com
 */
public class SplashScaleAnimation extends ScaleAnimation {


    private View mView;

    private FrameLayout.LayoutParams mLayoutParams;

    private int mMarginBottomFromY, mMarginBottomToY;

    private boolean mVanishAfter = false;

    public SplashScaleAnimation(float fromX, float toX, float fromY, float toY, int duration, View view,
                    boolean vanishAfter) {
        super(fromX, toX, fromY, toY);
        setDuration(duration);
        mView = view;
        mVanishAfter = vanishAfter;
        mLayoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        int height = mView.getHeight();
        mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin - height;
        mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) - height;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime < 1.0f) {
            int newMarginBottom = mMarginBottomFromY
                    + (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime);
            mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,
                    mLayoutParams.rightMargin, newMarginBottom);
            mView.getParent().requestLayout();
        } else if (mVanishAfter) {
            mView.setVisibility(View.GONE);
        }
    }
}
