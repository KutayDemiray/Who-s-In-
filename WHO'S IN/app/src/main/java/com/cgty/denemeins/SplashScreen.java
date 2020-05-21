package com.cgty.denemeins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * The "App Loading..." screen.
 *
 * @author Cagatay Safak
 * @version 1.0
 */
public class SplashScreen extends AppCompatActivity {
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen);
    }
}