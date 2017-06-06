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
    }

    interface Presenter {
        /**
         * on touch down
         *
         * @param v     image
         * @param event touch event
         * @see MotionEvent#ACTION_DOWN
         */
        void touchDown(android.view.View v, MotionEvent event);

        /**
         * on touch move
         *
         * @param event touch event
         * @see MotionEvent#ACTION_MOVE
         */
        void touchMove(MotionEvent event);

        /**
         * on touch up
         *
         * @see MotionEvent#ACTION_UP
         */
        void touchUp();
    }
}
