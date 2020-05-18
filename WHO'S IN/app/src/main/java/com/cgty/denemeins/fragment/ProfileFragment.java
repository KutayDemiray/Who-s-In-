package com.cgty.denemeins.fragment;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.LoginActivity;
import com.cgty.denemeins.R;
import com.cgty.denemeins.model.Event;
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
    TextView textView_Followers;
    TextView textView_Following;
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileID = prefs.getString("profileid", "none");

        mSignOut = (ImageButton) view.findViewById(R.id.buttonSignOut);
        image_profile = view.findViewById(R.id.profilePicture);

        textView_Age = view.findViewById(R.id.textViewProfileAge);
        textView_Username = view.findViewById(R.id.textViewProfileUsername);                                          //id gonna be changed
        textView_Bio = view.findViewById(R.id.textViewProfileBio);
        textView_Followers = view.findViewById(R.id.textViewProfileFollowersInfo);
        textView_Following = view.findViewById(R.id.textViewProfileFollowingInfo);

        button_EditProfile = view.findViewById(R.id.buttonEditProfile_profile);
        button_Followers = view.findViewById(R.id.buttonFollowers_profile);
        button_Following = view.findViewById(R.id.buttonFollowing_profile);
        button_PastActivities = view.findViewById(R.id.buttonPastActivities_profile);

        //calling methods
        userInfo();
        getFollowInfo();
        getNoOfEventsCreated();

        if (profileID.equals(currentUser.getUid()))
            button_EditProfile.setText( "EDIT PROFILE");
        else
        {
            followController();
            button_PastActivities.setVisibility(View.GONE);
        }


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

        setupFireBaseListener();

        mSignOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: attempting to sign out the use.");
                FirebaseAuth.getInstance().signOut();
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
        DatabaseReference followPath;
        followPath = FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid()).child("following");

        followPath.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(profileID).exists())
                    button_EditProfile.setText( "FOLLOWING");
                else
                    button_EditProfile.setText( "FOLLOW");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void getFollowInfo()
    {
        //gets number of followers
        DatabaseReference followerPath;
        followerPath = FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid()).child("followers");

        followerPath.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                textView_Followers.setText( dataSnapshot.getChildrenCount() + " Followers");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        //gets number of following
        DatabaseReference followingPath;
        followingPath = FirebaseDatabase.getInstance().getReference().child("Follow").child(currentUser.getUid()).child("followers");

        followingPath.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                textView_Following.setText( "" + dataSnapshot.getChildrenCount() + " Following");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void getNoOfEventsCreated()
    {
        DatabaseReference eventPath;
        eventPath = FirebaseDatabase.getInstance().getReference().child("Events");

        eventPath.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int i;
                i = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Event event = snapshot.getValue(Event.class);

                    if (event.getOrganizerId().equals(profileID))
                        i++;
                }

                textView_

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void setupFireBaseListener()
    {
        Log.d( TAG, "setupFirebaseListener: setting up the auth state listener.");

        mAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user;
                user = firebaseAuth.getCurrentUser();

                if ( user != null)
                    Log.d( TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                else
                {
                    Log.d( TAG, "onAuthStateChanged: signed_out");
                    Toast.makeText( getActivity(), "Signed out!", Toast.LENGTH_SHORT).show();

                    Intent intent;
                    intent = new Intent( getActivity(), LoginActivity.class);
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity( intent);
                }
            }
        };
    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if( mAuthStateListener != null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
    }

    /**
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

        private void userInfo()
        {
            DatabaseReference userPath;
            userPath = FirebaseDatabase.getInstance().getReference("Users").child(profileID);

            userPath.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });
        }

        //goko

        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

        reference = FirebaseDatabase.getInstance().getReference( "Users").child(currentUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if( user.getImageURL().equals("default"))
                    image_profile.setImageResource(R.mipmap.ic_launcher);
                else
                    Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        setupFireBaseListener();

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: attempting to sign out the use.");
                FirebaseAuth.getInstance().signOut();
            }
        });

        return view;
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult( intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading...");
        pd.show();

        if ( imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"-"+getFileExtension(imageUri));

            uploadTask = fileReference.getFile(imageUri);
            uploadTask.continueWith(new Continuation <UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if( !task.isSuccessful())
                        throw task.getException();
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if( task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    }
                    else{
                        Toast.makeText( getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else{
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data){
        super.onActivityResult( requestCode, resultCode, data );

        if( requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();

            if ( uploadTask != null && uploadTask.isInProgress())
                Toast.makeText(getContext(), "Upload is in progress", Toast.LENGTH_SHORT).show();
            else{
                uploadImage();
            }
        }
    }

    private void setupFireBaseListener(){
        Log.d( TAG, "setupFirebaseListener: setting up the auth state listener.");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if( user != null){
                    Log.d( TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }
                else{
                    Log.d( TAG, "onAuthStateChanged: signed_out");
                    Toast.makeText( getActivity(), "Signed out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( getActivity(), LoginActivity.class);
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity( intent);
                }
            }
        };
    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}
     */
}