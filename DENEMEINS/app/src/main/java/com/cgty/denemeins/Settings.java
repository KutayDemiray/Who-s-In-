package com.cgty.denemeins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    private static final String TAG = "SETTINGS";

    //firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //widgets
    private Button mSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSignOut = (Button) findViewById().findViewById(R.id.sign_out);

    }
}
