package com.example.tux.mylab.detail;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * one way swipe state viewpager
 */
public class OneWaySwipeViewpager extends ViewPager {
    private OnSwipeListener touchListener = null;
    private boolean isLockSwipe;

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
        return !isLockSwipe && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP && touchListener != null)
            touchListener.onTouchUp();
        return !isLockSwipe && super.onTouchEvent(ev);
    }

    /**
     * toggle state of view pager
     *
     * @param lockSwipe true: lock, false: unlock
     */
    public void setLockSwipe(boolean lockSwipe) {
        isLockSwipe = lockSwipe;
    }

    /**
     * to invoke callback for touch events
     *
     * @param listener add listener
     */
    public void setOnTouchUpListener(OnSwipeListener listener) {
        this.touchListener = listener;
    }

    /**
     * to invoke callback for touch events
     */
    interface OnSwipeListener {
        /**
         * @see MotionEvent#ACTION_UP
         */
        void onTouchUp();
    }
}
