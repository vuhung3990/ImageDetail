package com.example.tux.mylab.detail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tux.mylab.R;

import java.util.ArrayList;
import java.util.List;

class ImageSliderAdapter extends PagerAdapter implements View.OnTouchListener {
    private List<String> images = new ArrayList<>();
    private Context context;
    private final DetailPresenter presenter;

    ImageSliderAdapter(Context context, DetailPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    void updateData(List<String> data) {
        images = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = images.get(position);

        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        ImageView view = (ImageView) inflater.inflate(R.layout.item_slider, container, false);
        view.setOnTouchListener(this);
        Glide.with(context)
                .load(url)
                .into(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                presenter.touchDown(v.getY(), event.getRawY());
                return true;
            case MotionEvent.ACTION_MOVE:
                presenter.touchMove(event.getRawY());
                return false;
            case MotionEvent.ACTION_UP:
                presenter.touchUp();
                return false;
            default:
                return false;
        }
    }
}
