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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity
{
    //variables
    EditText edittextUsername, edittextAge, edittextEmail, edittextPassword;
    Button buttonSignUp;
    TextView textviewGoLogin;
    FirebaseAuth auth;
    DatabaseReference path;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //declaration
        edittextUsername = findViewById( R.id.editTextSignUpUserName);
        edittextAge = findViewById( R.id.editTextSignUpAge);
        edittextEmail = findViewById( R.id.editTextSignUpEMail);
        edittextPassword = findViewById( R.id.editTextSignUpPassword);
        buttonSignUp = findViewById( R.id.buttonSignUpActivity);
        textviewGoLogin = findViewById( R.id.textViewGoLogin);
        auth = FirebaseAuth.getInstance();


        // go login text
        textviewGoLogin.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity( new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        //sign up
        buttonSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pd = new ProgressDialog( SignUpActivity.this);
                pd.setMessage("Wait...");
                pd.show();

                String strUsername = edittextUsername.getText().toString();
                String strAge = edittextAge.getText().toString();           //maybe int..................
                String strEmail = edittextEmail.getText().toString();
                String strPassword = edittextPassword.getText().toString();

                if( TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strAge) || TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword))
                    Toast.makeText(SignUpActivity.this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();

                else if( strPassword.length() < 5)
                    Toast.makeText(SignUpActivity.this, "Password must be at least 6 characterss long.", Toast.LENGTH_SHORT).show();

                else
                    signUp( strUsername, strAge, strEmail, strPassword);
            }
        });
    }

    private void signUp(final String un, final String age, String mail, String pw)
    {
        //code to register new user...
        auth.createUserWithEmailAndPassword( mail, pw).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            path = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                            HashMap<String, Object> hashmap = new HashMap<>();

                            hashmap.put("id", userID);
                            hashmap.put("username", un.toLowerCase());
                            hashmap.put("age", age);
                            hashmap.put("bio", "");
                            hashmap.put("picurl", "https://firebasestorage.googleapis.com/v0/b/deneme-ins.appspot.com/o/femalePP.jpg?alt=media&token=caf1f449-bba5-430f-a738-843873166082");

                            path.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        pd.dismiss();

                                        Intent intentFromSignUpToMain = new Intent( SignUpActivity.this, MainActivity.class);
                                        intentFromSignUpToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);     //makes SignUpActivity unreachable after profile created
                                        startActivity(intentFromSignUpToMain);
                                    }
                                }
                            });
                        }

                        else
                        {
                            pd.dismiss();

                            Toast.makeText(SignUpActivity.this, "Invalid e-mail or password.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}