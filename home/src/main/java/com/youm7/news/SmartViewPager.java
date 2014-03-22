package com.youm7.news;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Custom {@link ViewPager} implementation to resolve scroll gesture directions more accurate than a regular
 * {@link ViewPager} component. This will make it perfectly usable into a scroll container such as {@link ScrollView},
 * {@link ListView}, etc.
 * <p>
 * Default ViewPager becomes hardly usable when it's nested into a scroll container. Such container will intercept any
 * touch event with minimal vertical shift from the child ViewPager. So switch the page by scroll gesture with a regular
 * {@link ViewPager} nested into a scroll container, user will need to move his finger horizontally without vertical
 * shift. Which is obviously quite irritating. {@link SmartViewPager} has a much much better behavior at resolving
 * scrolling directions.
 */
public class SmartViewPager extends ViewPager {

        // -----------------------------------------------------------------------
        //
        // Constructor
        //
        // -----------------------------------------------------------------------
        public SmartViewPager(Context context, AttributeSet attrs) {
                super(context, attrs);
                mGestureDetector = new GestureDetector(context, new XScrollDetector());
        }

        // -----------------------------------------------------------------------
        //
        // Fields
        //
        // -----------------------------------------------------------------------
        private GestureDetector mGestureDetector;
        private boolean mIsLockOnHorizontalAxis = false;

        // -----------------------------------------------------------------------
        //
        // Methods
        //
        // -----------------------------------------------------------------------
       
        @Override
        public boolean onTouchEvent(MotionEvent event) {
                // decide if horizontal axis is locked already or we need to check the scrolling direction
                if (!mIsLockOnHorizontalAxis)
                        mIsLockOnHorizontalAxis = mGestureDetector.onTouchEvent(event);

                // release the lock when finger is up
                if (event.getAction() == MotionEvent.ACTION_UP)
                        mIsLockOnHorizontalAxis = false;

                getParent().requestDisallowInterceptTouchEvent(mIsLockOnHorizontalAxis);
                
                return super.onTouchEvent(event);
        }

        // -----------------------------------------------------------------------
        //
        // Inner Classes
        //
        // -----------------------------------------------------------------------
        private class XScrollDetector extends SimpleOnGestureListener {

                // -----------------------------------------------------------------------
                //
                // Methods
                //
                // -----------------------------------------------------------------------
                /**
                 * @return true - if we're scrolling in X direction, false - in Y direction.
                 */
                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        return Math.abs(distanceX) > (Math.abs(distanceY));
                }

        }
        @Override
        protected void onMeasure(int arg0, int arg1) {
        // TODO Auto-generated method stub
        super.onMeasure(arg0, arg1);
        View view = getChildAt(0);
        if (view != null) {
            // measure the first child view with the specified measure spec
            view.measure(arg0, arg1);
            Log.e("toprel", Integer.toString(view.getMeasuredHeight()));
            Log.e("toprelw", Integer.toString(view.getMeasuredWidth()));
           
        }

        setMeasuredDimension(getMeasuredWidth(), measureHeight(arg1, view));
        }
        private int measureHeight(int measureSpec, View view) {
            int result = 0;
            int specMode = MeasureSpec.getMode(measureSpec);
            int specSize = MeasureSpec.getSize(measureSpec);

            if (specMode == MeasureSpec.EXACTLY) {
                result = specSize;
            } else {
                // set the height from the base view if available
                if (view != null) {
                    result = view.getMeasuredHeight();
                }
                if (specMode == MeasureSpec.AT_MOST) {
                    result = Math.min(result, specSize);
                }
            }
            return result;
        }

}