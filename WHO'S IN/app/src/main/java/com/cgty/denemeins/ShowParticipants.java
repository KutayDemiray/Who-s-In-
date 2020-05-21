package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import com.cgty.denemeins.adapter.UserAdapter;
import com.cgty.denemeins.model.Notification;
import com.cgty.denemeins.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity class for showing participants of events
 * @author Yağız Yaşar
 * @version 21.5.20
 */
public class ShowParticipants extends AppCompatActivity {

   //properties
   private String eventId;
   private RecyclerView recyclerView;
   private List<String> participantsIdList;
   private List<User> participantList;
   private UserAdapter userAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_show_participants );

      eventId = getIntent().getStringExtra( "eventId"); // getting the eventId from the activity page

      recyclerView = findViewById( R.id.recyclerViewShowParticipants );
      recyclerView.setHasFixedSize( true);

      LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getBaseContext() );
      recyclerView.setLayoutManager( linearLayoutManager );

      participantsIdList = new ArrayList<>();
      participantList = new ArrayList<>();
      userAdapter = new UserAdapter( getBaseContext(), participantList, false );
      recyclerView.setAdapter( userAdapter);

      readParticipants();
   }

   /**
    * This methods reads participant keys from the eventId and then helps to list them with getting their infos
    * from Users
    */
   private void readParticipants() {

      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events" ).child( eventId ).child( "participants");

      reference.addValueEventListener( new ValueEventListener() {

         /**
          * Reading participant keys with DataSnapshot thanks to eventId
          * @param dataSnapshot
          */
         @Override
         public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

            participantsIdList.clear(); //clearing the whole notifications

            for ( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
               String participant = snapshot.getValue( String.class );
               participantsIdList.add( participant);
            }

            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");

            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  participantList.clear();

                  for ( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                     User participant = snapshot.getValue( User.class);

                     for ( String participantId : participantsIdList )
                        if ( participantId.equals( participant.getId() ) ) {
                           participantList.add( participant );
                     }
                  }
                  Collections.reverse( participantList ); //reversing the participants so that the newest will appear at top
                  userAdapter.notifyDataSetChanged(); //notifying the adapter when an user joins the event
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
            });
         }

         @Override
         public void onCancelled( @NonNull DatabaseError databaseError ) {

         }
      });
   }

}
