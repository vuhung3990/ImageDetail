package com.example.tux.mylab.detail;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.tux.mylab.R;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private ConstraintLayout container;
    private DetailPresenter presenter;
    private OneWaySwipeViewpager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        presenter = new DetailPresenter(this);
        container = (ConstraintLayout) findViewById(R.id.container);
        viewPager = (OneWaySwipeViewpager) findViewById(R.id.image_slider);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, presenter);
        viewPager.setAdapter(adapter);

        List<String> images = new ArrayList<>();
        images.add("https://s-media-cache-ak0.pinimg.com/736x/ed/8c/50/ed8c50ed4cdb091e999ed892056e28a1.jpg");
        images.add("https://s-media-cache-ak0.pinimg.com/736x/76/5b/f9/765bf93cd4280bab642329b597f1205e.jpg");
        images.add("https://s-media-cache-ak0.pinimg.com/736x/50/7d/ec/507dec046e33c14c2c5de70f669aab7d.jpg");
        adapter.updateData(images);
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
        viewPager.animate()
                .y(top)
                .setDuration(0)
                .start();
    }
}
