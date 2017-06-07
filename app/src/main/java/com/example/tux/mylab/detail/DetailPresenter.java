package com.example.tux.mylab.detail;

class DetailPresenter implements DetailContract.Presenter {
    private static final int THRESHOLD = 200;
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
//        view.lockSwipe();
    }

    @Override
    public void touchUp() {
        if (Math.round(Math.abs(deltaY)) > THRESHOLD) {
            view.finish();
        } else {
            // restore origin view
            view.fadeView(originY, 100f);
//            view.unLockSwipe();
        }
    }

    /**
     * @return opacity of container depend on screen height
     */
    private float calculateViewOpacity() {
        return 1 - (Math.abs(deltaY) / halfWindowHeight);
    }
}
