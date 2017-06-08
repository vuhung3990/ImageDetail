package com.imagedetail.detail;

class DetailPresenter implements DetailContract.Presenter {
    /**
     * half device height
     */
    private float halfWindowHeight;
    /**
     * min value for finish detail
     */
    private static final int THRESHOLD = 200;
    /**
     * min value for lock viewpager, it's important for disable swipe viewpager when swipe up|down
     */
    private static final int THRESHOLD_MIN_VERTICAL = 10;
    private final DetailContract.View view;
    private float originY;
    private float dY;
    private float deltaY;
    private boolean isVertivalScroll = false;

    DetailPresenter(DetailContract.View view) {
        this.view = view;
        halfWindowHeight = view.getScreenHeight() / 2;
    }

    @Override
    public void touchDown(float originViewY, float eventRawY) {
        // to restore
        originY = originViewY;
        // calculate distance from top of view to top of touch point
        dY = originY - eventRawY;
    }

    @Override
    public void touchMove(float eventRawY) {
        // calculate top of view when drag
        float viewY = eventRawY + dY;
        deltaY = Math.round(Math.abs(viewY - originY));
        view.fadeView(viewY, calculateViewOpacity());

        if (!isVertivalScroll && Math.round(Math.abs(deltaY)) > THRESHOLD_MIN_VERTICAL) {
            isVertivalScroll = true;
            view.lockSwipe();
        }
    }

    @Override
    public void touchUp() {
        isVertivalScroll = false;
        view.unLockSwipe();
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
