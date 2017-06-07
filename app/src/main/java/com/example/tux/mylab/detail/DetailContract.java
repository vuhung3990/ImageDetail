package com.example.tux.mylab.detail;

import android.view.MotionEvent;

interface DetailContract {
    interface View {
        /**
         * @return screen height of device
         */
        int getScreenHeight();

        /**
         * move view when drag
         *
         * @param top                     position
         * @param percentOpacityContainer percent opacity of container
         */
        void fadeView(float top, float percentOpacityContainer);

        /**
         * close activity
         */
        void finish();

        /**
         * lock view pager swipe
         */
        void lockSwipe();

        /**
         * unlock viewpager swipe
         */
        void unLockSwipe();
    }

    interface Presenter {
        /**
         * on touch down
         *
         * @param originViewY to save origin of view
         * @param eventRawY   to calculate distance from top of view to top of touch point
         * @see MotionEvent#ACTION_DOWN
         */
        void touchDown(float originViewY, float eventRawY);

        /**
         * on touch move
         *
         * @param eventRawY calculate top of view when drag
         * @see MotionEvent#ACTION_MOVE
         */
        void touchMove(float eventRawY);

        /**
         * on touch up
         *
         * @see MotionEvent#ACTION_UP
         */
        void touchUp();
    }
}
