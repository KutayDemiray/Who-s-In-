package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.cgty.denemeins.model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

   TextView eventTitle, eventType, eventDateAndLocation, eventDescription, eventCapacity,
            eventParticipants;

   Button joinButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events");
      
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_event);

      eventTitle = findViewById( R.id.eventTitle );
      eventType = findViewById( R.id.eventType );
      eventDateAndLocation = findViewById( R.id.eventDateAndLocation );
      eventDescription = findViewById( R.id.eventDescription );
      eventCapacity = findViewById( R.id.eventCapacity );
      eventParticipants = findViewById( R.id.eventCapacity );

      ref.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Event event = dataSnapshot.getValue( Event.class ); // TODO get id from intent
            eventTitle.setText( event.getTitle() );
            eventType.setText( event.getMainType() + " - " + event.getSubType() );
            eventDateAndLocation.setText( event.getDate().toString() + " " + event.getLocation() );
            eventDescription.setText( event.getDescription() );
            eventCapacity.setText( "Capacity: " + event.getParticipants().size() + "/" + event.getCapacity() );
            eventParticipants.setText( event.getParticipants().toString() );
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
      });

   }


}
