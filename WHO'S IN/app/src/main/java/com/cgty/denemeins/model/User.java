package com.cgty.denemeins.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * User model class
 * @author Cagatay Safak
 * @version 1.0
 */
public class User
{
    private String id;
    private String username;
    private String age;
    private String imageURL;
    private String bio;

    public User()
    {

    }

    public User( String id, String username, String age, String imageURL, String bio)
    {
        this.id = id;
        this.username = username;
        this.age = age;
        this.imageURL = imageURL;
        this.bio = bio;

    }

    public static User getUser( String id ) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference( "Users" );
        final User u = new User();
        final String fId = id;
        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User tmp;

                tmp = dataSnapshot.child( fId ).getValue( User.class ); // TODO can't add data to u for some reason
                Log.wtf( "DENEME123", tmp.toString() );
                u.setId( tmp.getId() );
                u.setUsername( tmp.getUsername() );
                u.setAge( tmp.getAge() );
                u.setPpURL( tmp.getPpURL() );
                u.setBio( tmp.getBio() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return u;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setPpURL(String ppURL)
    {
        this.imageURL = ppURL;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }


    public String toString() {
        return "Title: " + getUsername() + " Age: " + getAge() + " Bio: " + getBio();
    }

}