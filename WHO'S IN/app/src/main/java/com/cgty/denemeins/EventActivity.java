package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cgty.denemeins.model.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

   TextView eventTitle, eventType, eventDateAndLocation, eventDescription, eventCapacity,
            eventParticipants;

   AppCompatButton eventJoinButton;
   Intent intent;

   @Override
   protected void onCreate( Bundle savedInstanceState ) {

      DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events" );
      final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      intent = getIntent();
      final String eventId = intent.getStringExtra( "eventId" );
      
      super.onCreate(savedInstanceState);
      setContentView( R.layout.activity_event );

      eventTitle = findViewById( R.id.eventTitle );
      eventType = findViewById( R.id.eventType );
      eventDateAndLocation = findViewById( R.id.eventDateAndLocation );
      eventDescription = findViewById( R.id.eventDescription );
      eventCapacity = findViewById( R.id.eventCapacity );
      eventParticipants = findViewById( R.id.eventCapacity );
      eventJoinButton = findViewById( R.id.eventJoinButton );

      ref.addValueEventListener( new ValueEventListener() {
         @Override
         public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
            Event event;
            event = dataSnapshot.child( eventId ).getValue( Event.class ); // uses the eventId from intent
            eventTitle.setText( event.getTitle() );
            eventType.setText( event.getMainType() + " - " + event.getSubType() );
            eventDateAndLocation.setText( event.getDate().toString() + " " + event.getLocation() );
            eventDescription.setText( event.getDescription() );
            if ( event.getParticipants().indexOf( firebaseUser.getUid() ) == -1 ) {
               eventJoinButton.setText( "JOIN" );
            } else {
               eventJoinButton.setText( "LEAVE" );
            }
            eventCapacity.setText( "Capacity: "  + event.getNumberOfParticipants() + "/" + event.getCapacity() );
           //eventParticipants.setText( "sasd" );
         }

         @Override
         public void onCancelled( @NonNull DatabaseError databaseError ) {

         }

      });

      eventJoinButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick( View v ) {
            addOrRemoveParticipant( eventId, firebaseUser.getUid() );
         }
      });

   }

   private void addOrRemoveParticipant( final String eventId, final String userId ) {

      final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events" ).child( eventId );
      final ArrayList<String> participants = new ArrayList<>();

      reference.addListenerForSingleValueEvent( new ValueEventListener() {
         @SuppressLint( "SetTextI18n" )
         @Override
         public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

            participants.clear();
            for ( DataSnapshot snapshot : dataSnapshot.child( "participants").getChildren() ) {
               participants.add( snapshot.getValue( String.class ) );
            }

            Event event = dataSnapshot.getValue( Event.class );

            event.setParticipants( participants );
            eventCapacity.setText( "Capacity: "  + event.getNumberOfParticipants() + "/" + event.getCapacity() );
            event.printParticipants();


            if ( event.getParticipants().indexOf( userId ) == -1 ) {
               eventJoinButton.setText( "JOIN" );
               event.getParticipants().add( userId );
            } else {
               eventJoinButton.setText( "LEAVE" );
               event.getParticipants().remove( userId );
            }

            reference.child( "participants" ).setValue( event.getParticipants() );
         }

         @Override
         public void onCancelled( @NonNull DatabaseError databaseError ) {

         }
      });

   }

}
