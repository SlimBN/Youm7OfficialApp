package com.youm7.news;

import android.widget.ImageView;

import android.content.Context;

import android.util.AttributeSet;



public class ResizableImageViewLarge extends ImageView {
	public ResizableImageViewLarge(Context context)
	{
	    super(context);
	}

	public ResizableImageViewLarge(Context context, AttributeSet attrs)
	{
	    super(context, attrs);
	}

	public ResizableImageViewLarge(Context context, AttributeSet attrs,
	        int defStyle)
	{
	    super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
	   
	   
	        int width =  MeasureSpec.getSize(widthMeasureSpec);
	       
	       
	        int height = width * 200 / 380;
	        setMeasuredDimension(width, height);
	     
	        requestLayout();
	       
	}
}
