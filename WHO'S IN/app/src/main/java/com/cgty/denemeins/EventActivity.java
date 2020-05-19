package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class EventActivity extends AppCompatActivity {

   TextView eventTitle, eventType, eventDateAndLocation, eventDescription, eventCapacity,
            eventParticipants;

   Button joinButton;
   Intent intent;
   //String eventId;

   @Override
   protected void onCreate(Bundle savedInstanceState) {

      DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events");
      final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      intent = getIntent();
      final String eventId = intent.getStringExtra( "eventId" );
      
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_event);

      eventTitle = findViewById( R.id.eventTitle );
      eventType = findViewById( R.id.eventType );
      eventDateAndLocation = findViewById( R.id.eventDateAndLocation );
      eventDescription = findViewById( R.id.eventDescription );
      eventCapacity = findViewById( R.id.eventCapacity );
      eventParticipants = findViewById( R.id.eventCapacity );
      joinButton = findViewById( R.id.eventJoinButton);

      ref.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Event event;
            event = dataSnapshot.child( eventId).getValue( Event.class ); // uses the eventId from intent
            eventTitle.setText( event.getTitle() );
            eventType.setText( event.getMainType() + " - " + event.getSubType() );
            eventDateAndLocation.setText( event.getDate().toString() + " " + event.getLocation() );
            eventDescription.setText( event.getDescription() );
            eventCapacity.setText( "Capacity: "  + "/" + event.getCapacity() );
           // eventParticipants.setText( "sasd" );
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
      });

      joinButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            addOrRemoveParticipant( eventId);
         }
      });
   }

   private void addOrRemoveParticipant( final String eventId) {

      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events").child( eventId);
      final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

      reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Event event = dataSnapshot.child( eventId ).getValue( Event.class );
            if ( !event.isParticipant( firebaseUser.getUid() ) ) {
               event.addParticipant( eventId, firebaseUser.getUid());
               joinButton.setText( "LEAVE");
            } else {
               event.removeParticipant( eventId, firebaseUser.getUid());
               joinButton.setText( "JOIN");
            }

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
      });
   }

}
