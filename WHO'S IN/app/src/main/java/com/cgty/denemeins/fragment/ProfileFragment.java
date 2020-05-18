package com.cgty.denemeins.fragment;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.LoginActivity;
import com.cgty.denemeins.R;
import com.cgty.denemeins.model.User;

import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Profile Fragment Class
 * @author Gökberk Keskinkılıç, Cagatay Safak
 * @version 2.0
 */
public class ProfileFragment extends Fragment
{
    //properties
    private ImageView image_profile;
    private ImageView image_signOut;
    private ImageButton mSignOut;
    Button button_EditProfile;
    Button button_Followers;
    Button button_Following;
    Button button_PastActivities;
    TextView textView_Age;
    TextView textView_Username;
    TextView textView_Bio;
    FirebaseUser currentUser;
    String profileID;

    //goko
    TextView username;
    DatabaseReference reference;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    private static final String TAG = "SETTINGS";

    //firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //constructor
    public ProfileFragment()
    {
        //required empty public constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view;
        view = inflater.inflate( R.layout.fragment_profile, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileID = prefs.getString("profileid","none");

        mSignOut = (ImageButton) view.findViewById( R.id.buttonSignOut);
        image_profile = view.findViewById(R.id.profilePicture);

        textView_Age = view.findViewById(R.id.textViewProfileAge);
        textView_Username = view.findViewById(R.id.textViewProfileUsername);                                          //id gonna be changed
        textView_Bio = view.findViewById(R.id.textViewProfileBio);

        button_EditProfile = view.findViewById(R.id.buttonEditProfile_profile);
        button_Followers = view.findViewById(R.id.buttonFollowers_profile);
        button_Following = view.findViewById(R.id.buttonFollowing_profile);
        button_PastActivities = view.findViewById(R.id.buttonPastActivities_profile);

        button_EditProfile.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v)
            {
                String buttonText;
                buttonText = button_EditProfile.getText().toString();

                if ( buttonText.equals( "EDIT PROFILE"))
                {
                    //go to edit profile screen
                }
                else if ( buttonText.equals( "FOLLOW"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid()).child("following").child(profileID).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileID).child("followers").child(currentUser.getUid()).setValue(true);
                }
                else if ( buttonText.equals( "FOLLOWING"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid()).child("following").child(profileID).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileID).child("followers").child(currentUser.getUid()).removeValue();
                }
            }
        });

        return view;
    }

    private void userInfo()
    {
        DatabaseReference userPath;
        userPath = FirebaseDatabase.getInstance().getReference("Users").child(profileID);

        userPath.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (getContext() == null)
                    return;

                User user;
                user = dataSnapshot.getValue(User.class);

                Glide.with(getContext()).load(user.getImageURL()).into(image_profile);

                textView_Username.setText(user.getUsername());
                textView_Age.setText(user.getAge());
                textView_Bio.setText(user.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void followController()
    {
        DatabaseReference followPath = FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid()).child("following");
    }
}