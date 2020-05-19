package com.cgty.denemeins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @author Çağatay Şafak
 * @version 1.0
 */
public class StartActivity extends AppCompatActivity
{
    Button buttonStartLogin;
    Button buttonStartSignUp;
    FirebaseUser userStart;

    @Override
    protected void onStart() {
        super.onStart();

        userStart = FirebaseAuth.getInstance().getCurrentUser();
        if ( userStart != null ) {                              //if a user present on device (or database), go to main activity directly
            startActivity( new Intent(StartActivity.this, MainActivity.class ) );
            finish();
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        buttonStartLogin = findViewById( R.id.buttonLoginStart );
        buttonStartSignUp = findViewById( R.id.buttonSignUpStart );

        buttonStartLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent ( StartActivity.this, LoginActivity.class ) );
            }
        });

        buttonStartSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                startActivity( new Intent (StartActivity.this, SignUpActivity.class ) );
            }
        });
    }
}