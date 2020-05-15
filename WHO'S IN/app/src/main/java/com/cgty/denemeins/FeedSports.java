package com.cgty.denemeins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Feed which shows only sports events. Accessible from HomeFragment.
 * @author Cagatay Safak
 * @version 1.0
 */
public class FeedSports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_sports);
    }
}
