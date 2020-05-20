package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Login Activity class
 * @author Çağatay Şafak
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    EditText editTextEMail, editTextPassword;
    Button buttonLogin;
    TextView textViewGoSignUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        editTextEMail = findViewById( R.id.editTextLoginEMail );
        editTextPassword = findViewById( R.id.editTextLoginPassword );
        buttonLogin = findViewById( R.id.buttonLoginActivity );
        textViewGoSignUp = findViewById( R.id.textViewGoSignUp );
        auth = FirebaseAuth.getInstance();

        textViewGoSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                startActivity( new Intent(LoginActivity.this, SignUpActivity.class ) );
            }
        });

        buttonLogin.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                final ProgressDialog pdLogin;
                pdLogin= new ProgressDialog( LoginActivity.this );
                pdLogin.setMessage( "Please wait to login..." );
                pdLogin.show();

                String stringEmailLogin;
                String stringPasswordLogin;

                stringEmailLogin = editTextEMail.getText().toString();
                stringPasswordLogin = editTextPassword.getText().toString();

                if(TextUtils.isEmpty( stringEmailLogin ) || TextUtils.isEmpty( stringPasswordLogin ) ) {
                    Toast.makeText(LoginActivity.this, "These fields can not be empty.", Toast.LENGTH_SHORT ).show();
                }
                else {
                    //code to Login the user.

                    auth.signInWithEmailAndPassword( stringEmailLogin, stringPasswordLogin).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete( @NonNull Task<AuthResult> task )
                        {
                            if( task.isSuccessful())
                            {
                                DatabaseReference pathLogin = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( auth.getCurrentUser().getUid() );

                                pathLogin.addValueEventListener( new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange( @NonNull DataSnapshot dataSnapshot )
                                    {
                                        pdLogin.dismiss();

                                        Intent intentFromLoginToMain = new Intent( LoginActivity.this, MainActivity.class );
                                        intentFromLoginToMain.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                        startActivity( intentFromLoginToMain);
                                        finish();
                                    }
                                    @Override
                                    public void onCancelled( @NonNull DatabaseError databaseError ) {
                                        pdLogin.dismiss();
                                    }
                                });
                            }
                            else
                            {
                                pdLogin.dismiss();
                                Toast.makeText(LoginActivity.this, "Error! Wrong username or password.", Toast.LENGTH_LONG ).show();
                            }
                        }
                    });
                }
            }
        });
    }
}