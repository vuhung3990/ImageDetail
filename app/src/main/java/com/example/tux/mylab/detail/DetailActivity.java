package com.example.tux.mylab.detail;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tux.mylab.R;

public class DetailActivity extends AppCompatActivity implements View.OnTouchListener, DetailContract.View {
    private ConstraintLayout container;
    private ImageView imageView;
    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        presenter = new DetailPresenter(this);

        imageView = (ImageView) findViewById(R.id.imageView);
        container = (ConstraintLayout) findViewById(R.id.container);
        Glide.with(this)
                .load("http://www.androidhive.info/wp-content/uploads/2016/04/building-image-gallery-all-with-glide.jpg")
                .into(imageView);
        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                presenter.touchDown(v, event);
                return true;
            case MotionEvent.ACTION_MOVE:
                presenter.touchMove(event);
                return false;
            case MotionEvent.ACTION_UP:
                presenter.touchUp();
                return false;
            default:
                return false;
        }
    }

    @Override
    public int getScreenHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    @Override
    public void fadeView(float top, float percent) {
        // set opacity container, percent always > 20%
        percent = percent < 0.2f ? 0.2f : percent;
        // fade container
        container.animate()
                .alpha(percent)
                .setDuration(0)
                .start();

        // move content
        imageView.animate()
                .y(top)
                .setDuration(0)
                .start();
    }
}
