package com.example.tux.mylab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.imagedetail.detail.DetailActivity;
import com.imagedetail.detail.DetailInput;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imageUrls = new ArrayList<>();
                imageUrls.add("http://i2.kym-cdn.com/photos/images/facebook/000/000/578/1234931504682.jpg");
                imageUrls.add("https://fthmb.tqn.com/2PPc625hMNvAUfPVS9m6Aq4GWk4=/469x428/filters:no_upscale()/about/trollface_533-58072dba3df78cbc28f275af.jpg");
                imageUrls.add("https://fthmb.tqn.com/KbN_rNM85weLEEHtuQailTblYIk=/768x0/filters:no_upscale()/about/foreveralone-58072dc65f9b5805c23977d3.png");
                imageUrls.add("http://3.bp.blogspot.com/-V28tN8EbrLY/UUQXu3xZ_wI/AAAAAAAACeg/IsW2Sw6ITGI/s1600/RedEyes.png");
                imageUrls.add("http://meme.vandorp.biz/pffr.jpg");

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailInput.INPUT_KEY, new DetailInput.Builder()
                        .commentCount(9)
                        .likeCount(21)
                        .listImageUrl(imageUrls)
                        .title("Peter Tũn")
                        .status("enjoy meme")
                        .time("2 day ago")
                        .build());
                startActivity(intent);
            }
        });
    }
}
