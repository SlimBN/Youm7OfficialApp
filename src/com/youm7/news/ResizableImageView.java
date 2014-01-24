package com.youm7.news;

import android.widget.ImageView;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;


public class ResizableImageView extends ImageView {
	public ResizableImageView(Context context)
	{
	    super(context);
	}

	public ResizableImageView(Context context, AttributeSet attrs)
	{
	    super(context, attrs);
	}

	public ResizableImageView(Context context, AttributeSet attrs,
	        int defStyle)
	{
	    super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
	   
	   
	        int width =  MeasureSpec.getSize(widthMeasureSpec);
	       
	      
	        int height = width * 130 / 170;
	        setMeasuredDimension(width, height);
	       
	       
	}
}
