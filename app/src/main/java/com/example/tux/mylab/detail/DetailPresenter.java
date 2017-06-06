package com.example.tux.mylab.detail;

import android.view.MotionEvent;
import android.view.View;

class DetailPresenter implements DetailContract.Presenter {
    private static final int THRESHOLD = 100;
    private final DetailContract.View view;
    private float originY;
    private float dY;
    private float deltaY;
    private float halfWindowHeight;

    DetailPresenter(DetailContract.View view) {
        this.view = view;
        halfWindowHeight = view.getScreenHeight() / 2;
    }

    @Override
    public void touchDown(View v, MotionEvent event) {
        // to restore
        originY = v.getY();
        // calculate distance from top of view to top of touch point
        dY = originY - event.getRawY();
    }

    @Override
    public void touchMove(MotionEvent event) {
        // calculate top of view when drag
        float viewY = event.getRawY() + dY;

        deltaY = viewY - originY;
        view.fadeView(viewY, calculateViewOpacity());
    }

    @Override
    public void touchUp() {
        if (Math.round(Math.abs(deltaY)) > THRESHOLD) {
            view.finish();
        } else {
            // restore origin view
            view.fadeView(originY, 100f);
        }
    }

    /**
     * @return opacity of container depend on screen height
     */
    private float calculateViewOpacity() {
        return 1 - (Math.abs(deltaY) / halfWindowHeight);
    }
}
