package com.cgty.denemeins;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.R;
import com.cgty.denemeins.adapter.UserAdapter;
import com.cgty.denemeins.model.User;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Show followers
 * @author Çağatay Şafak
 * @version 1.0
 */
public class ShowFollowers extends AppCompatActivity {

    private String id;
    private String title;
    private List<String> idList;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.show_followers );
        
        Intent intent;  //intentFromProfileToFollowers
        intent = getIntent();
        
        id = intent.getStringExtra("id" );
        title = intent.getStringExtra("title" );
        
        //Toolbar settings
        Toolbar toolbar;
        toolbar = findViewById( R.id.toolbarShowFollowers );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( title );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });
        
        //Recycler settings
        recyclerView = findViewById( R.id.recyclerViewShowFollowers );
        recyclerView.setHasFixedSize( true );
        
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager( linearLayoutManager );
        
        userList = new ArrayList<>();
        
        userAdapter = new UserAdapter(this, userList, false );
        
        recyclerView.setAdapter( userAdapter );
        
        idList = new ArrayList<>();
        
        switch (title) {
            case "participants":
                getParticipants();
                break;
            case "followers":
                getFollowers();
                break;
            case "following":
                getFollowing();
                break;
        }
    }
    
    private void getParticipants() {

        DatabaseReference participatePath;
        participatePath = FirebaseDatabase.getInstance().getReference("Events" ).child(id).child( "participants" );
    
        participatePath.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                idList.clear();
                
                for ( DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    idList.add( snapshot.getKey() );
                }
    
                showUsers();
            }
            
            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
            
            }
        });
    }
    
    private void getFollowing() {

        DatabaseReference followingPath;
        followingPath = FirebaseDatabase.getInstance().getReference("Follow" ).child(id).child( "following" );
    
        followingPath.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                idList.clear();
                
                for ( DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    idList.add( snapshot.getKey() );
                }
                
                showUsers();
            }
            
            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
            
            }
        });
    }
    
    private void getFollowers() {

        DatabaseReference followerPath;
        followerPath = FirebaseDatabase.getInstance().getReference("Follow" ).child(id).child( "followers" );
        
        followerPath.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                idList.clear();
                
                for ( DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    idList.add( snapshot.getKey() );
                }
                
                showUsers();
            }
    
            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
        
            }
        });
    }
    
    private void showUsers() {

        DatabaseReference userPath;
        userPath = FirebaseDatabase.getInstance().getReference("Users" );
    
        userPath.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                userList.clear();
                
                for ( DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    User user;
                    user = snapshot.getValue( User.class );
                    
                    for ( String id: idList ) {
                        if ( user.getId().equals( id ) ) {
                            userList.add( user );
                        }
                    }
                }
                
                userAdapter.notifyDataSetChanged();  // refreshes for every change in database
            }
            
            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
            
            }
        });
    }
}