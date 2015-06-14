package com.disamperin.mobile.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.disamperin.mobile.R;


public class TextProgressBar extends ProgressBar {

    private String text = "";
    private int textColor = Color.BLACK;
    private float textSize = 15;

    public TextProgressBar(Context context) {
        super(context);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(attrs);
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        if (attrs != null) {
        	int[] style = new int[]{R.attr.text, R.attr.textColor, R.attr.textSize};
                    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextProgressBar);
            setText(a.getString(0));
            setTextColor(a.getColor(1, textColor));
            setTextSize(a.getDimension(2, textSize));
            a.recycle();
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.centerX();
        int y = getHeight() / 2 - bounds.centerY();
        canvas.drawText(text, x, y, textPaint);
    }

    public String getText() {
        return text;
    }

    public synchronized void setText(String text) {
        if (text != null) {
            this.text = text;
        } else {
            this.text = "";
        }
        postInvalidate();
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        if(progress > getMax()/2.3) {
            setTextColor(getResources().getColor(android.R.color.white));
        } else {
            setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    public int getTextColor() {
        return textColor;
    }

    public synchronized void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public synchronized void setTextSize(float textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

}
