package com.youm7.newsapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Youm7TextView extends TextView {

	public Youm7TextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();// TODO Auto-generated constructor stub
	}

	public Youm7TextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();// TODO Auto-generated constructor stub
	}

	public Youm7TextView(Context context) {
		super(context);
		init();
	}

	private void init() {
        if (!isInEditMode()) {
           Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/GE SS Two Bold.otf");
            setTypeface(tf);
        }
}
}