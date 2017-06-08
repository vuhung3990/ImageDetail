package com.imagedetail.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imagedetail.R;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements DetailContract.View, OneWaySwipeViewpager.OnSwipeListener, ViewPager.OnPageChangeListener {
    private RelativeLayout container;
    private OneWaySwipeViewpager viewPager;
    private DetailPresenter presenter;
    private Toolbar toolbar;
    private int total;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        presenter = new DetailPresenter(this);

        // enable back button
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        container = (RelativeLayout) findViewById(R.id.container);
        viewPager = (OneWaySwipeViewpager) findViewById(R.id.image_slider);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, presenter);
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchUpListener(this);
        viewPager.addOnPageChangeListener(this);

        // footer
        TextView status = (TextView) findViewById(R.id.status_txt);
        TextView like = (TextView) findViewById(R.id.like_txt);
        TextView comment = (TextView) findViewById(R.id.comment_txt);

        // input
        DetailInput input = getIntent().getParcelableExtra(DetailInput.INPUT_KEY);
        total = input.getListImageUrl().size();
        time = input.getTime();

        adapter.updateData(input.getListImageUrl());
        toolbar.setTitle(input.getTitle());
        setToolbarSubtitle(1, total, time);

        status.setText(input.getStatus());
        like.setText(String.format(Locale.US, "%d Likes", input.getLikeCount()));
        comment.setText(String.format(Locale.US, "%d Comments", input.getCommentCount()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void lockSwipe() {
        viewPager.setLockSwipe(true);
    }

    @Override
    public void unLockSwipe() {
        viewPager.setLockSwipe(false);
    }

    @Override
    public void onTouchUp() {
        presenter.touchUp();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setToolbarSubtitle(position + 1, total, time);
    }

    /**
     * set subtitle of toolbar
     *
     * @param imageIndex number of image index
     * @param total      total image
     * @param time       time of taken image
     */
    private void setToolbarSubtitle(int imageIndex, int total, String time) {
        toolbar.setSubtitle(String.format(Locale.US, "%d/%d image - %s", imageIndex, total, time));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
