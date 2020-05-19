package com.cgty.denemeins.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


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
    private String picurl;
    private String bio;

    public User()
    {

    }

    public User( String id, String username, String age, String imageURL, String bio)
    {
        this.id = id;
        this.username = username;
        this.age = age;
        this.picurl = picurl;
        this.bio = bio;

    }

    /**
     * Get user from database by User ID
     * Could not implement yet (asynchronous databases do not directly allow retrieving and using data outside their listeners)
     * Possible workaround is using a TextView and setting and getting text from that
     * @author Cemhan Kaan Özaltan, Kutay Demiray
     * @param id User ID
     * @return User object
     */
    public static User getUser( String id ) {
        return null;
    }
    /*
        final User user = new User();
        readData( new FirebaseCallback() {
            @Override
            public void onCallback( User value ) {

                user.setId( value.getId() );
                user.setUsername( value.getUsername() );
                user.setBio( value.getBio() );
                user.setPpURL( value.getPpURL() );
                user.setAge( value.getAge() );
            }
        }, id );
        Log.wtf( "DENEME123", user.toString() );
        return user;
    }

    // helpers for User.getUser method
    private interface FirebaseCallback {
        void onCallback( User value );
    }

    private static void readData( final FirebaseCallback fbCallback, String uId ) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users" ).child( uId );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue( User.class );
                fbCallback.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    */
    // getters and setters
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

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl)
    {
        this.picurl = picurl;
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