package com.example.tux.mylab.detail;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * one way swipe state viewpager
 */
public class OneWaySwipeViewpager extends ViewPager {
    public OneWaySwipeViewpager(Context context) {
        super(context);
    }

    public OneWaySwipeViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // don' allow up-down or left-right at same time
        // isLockSwipe true: up-down, else left-right
        boolean isLockSwipe = !super.onInterceptTouchEvent(event);
        return !isLockSwipe;
    }
}
