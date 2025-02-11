package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cgty.denemeins.model.Event;
import com.cgty.denemeins.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Event activity
 * @author Yağız Yaşar, Cemhan Kaan Özaltan, Kutay Demiray
 * @version 1.0
 */
public class EventActivity extends AppCompatActivity {

   TextView eventTitle, eventOrganizerName, eventType, eventDateAndLocation, eventDescription, eventCapacity;

   AppCompatButton eventJoinButton, eventShowParticipantsButton;
   Intent intent;

   @Override
   protected void onCreate( Bundle savedInstanceState ) {

      DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events" ); //reference to events to get the current Event
      final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      intent = getIntent();
      final String eventId = intent.getStringExtra( "eventId" );

      super.onCreate(savedInstanceState);
      setContentView( R.layout.activity_event );

      eventOrganizerName = findViewById( R.id.eventOrganizerName );
      eventTitle = findViewById( R.id.eventTitle );
      eventType = findViewById( R.id.eventType );
      eventDateAndLocation = findViewById( R.id.eventDateAndLocation );
      eventDescription = findViewById( R.id.eventDescription );
      eventCapacity = findViewById( R.id.eventCapacity );
      eventShowParticipantsButton = findViewById( R.id.eventShowParticipantsButton );
      eventJoinButton = findViewById( R.id.eventJoinButton );
      
      ref.addValueEventListener( new ValueEventListener() {
         @Override
         public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
            final Event event = dataSnapshot.child( eventId ).getValue( Event.class ); // uses the eventId from intent

            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users" ); //reference to events to get the current participants and organizers

            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  String organizerUsername = dataSnapshot.child( event.getOrganizerId() ).child( "username" ).getValue( String.class );
                  eventOrganizerName.setText( "Organizer: " + organizerUsername);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
            });

            eventTitle.setText( event.getTitle() );
            if ( event.getMainType().equalsIgnoreCase("meeting") ) {
               eventType.setText(event.getMainType());
            }
            else {
               //eventType.setText(event.getMainType() + event.getSubType());
               eventType.setText(event.getMainType() + " - " + event.getSubType());
            }
            eventDateAndLocation.setText( event.getDate().toString() + " " + event.getLocation() );
            eventDescription.setText( event.getDescription() );
            eventDescription.setMovementMethod(new ScrollingMovementMethod() );

            if ( event.isFull() || FirebaseAuth.getInstance().getCurrentUser().getUid().equals( event.getOrganizerId() ) ) {
               eventJoinButton.setVisibility(View.GONE);
            } else if ( event.getParticipants().indexOf( firebaseUser.getUid()) == -1 ) {
               eventJoinButton.setText("JOIN");
            } else {
               eventJoinButton.setText( "LEAVE" );
            }

            eventCapacity.setText( "Capacity: "  + event.getNumberOfParticipants() + "/" + event.getCapacity() );
         }

         @Override
         public void onCancelled( @NonNull DatabaseError databaseError ) {

         }

      });

      eventJoinButton.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View v ) {
            addOrRemoveParticipant( eventId, firebaseUser.getUid() );
         }
      });

      eventShowParticipantsButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent fromEventToParticipants = new Intent( getBaseContext(), ShowParticipants.class );
            fromEventToParticipants.putExtra( "eventId", eventId );
            startActivity( fromEventToParticipants );
         }
      });

   }

   /**
    * A private void method to add participants and remove participants in order to regulate event's
    * remaining capacity and status according to the demand.
    * @param eventId - ID of the specific event
    * @param userId - current user
    */
   private void addOrRemoveParticipant( final String eventId, final String userId ) {

      final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events" ).child( eventId );
      final ArrayList<String> participants = new ArrayList<>();

      reference.addListenerForSingleValueEvent( new ValueEventListener() {
         @SuppressLint( "SetTextI18n" )
         @Override
         public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

            participants.clear();
            for ( DataSnapshot snapshot : dataSnapshot.child( "participants" ).getChildren() ) {
               participants.add( snapshot.getValue( String.class ) );
            }

            Event event = dataSnapshot.getValue( Event.class );

            event.setParticipants( participants );
            eventCapacity.setText( "Capacity: "  + event.getNumberOfParticipants() + "/" + event.getCapacity() );
            //event.printParticipants();

            // if the current user is organizer, join button will disappear
            if ( event.isFull() || FirebaseAuth.getInstance().getCurrentUser().getUid().equals( event.getOrganizerId() ) ) {
               eventJoinButton.setVisibility( View.GONE );
            // if the current user is not participant, join button will appear
            } else if ( event.getParticipants().indexOf( userId ) == -1 ) {
               eventJoinButton.setText("JOIN");
               event.getParticipants().add( userId );
               addJoinNotification( event.getOrganizerId(), event.getEventId(), userId, event.getTitle() );
            // if the current user is participant, leave button will appear
            } else {
               eventJoinButton.setText( "LEAVE" );
               event.getParticipants().remove( userId );
               addLeaveNotification( event.getOrganizerId(), event.getEventId(), userId, event.getTitle() );
            }
            reference.child( "participants" ).setValue( event.getParticipants() );
         }

         @Override
         public void onCancelled( @NonNull DatabaseError databaseError ) {

         }
      });

   }

   /**
    * Adding a notification to organizer if someone join to their events and setting notification
    * text according to this
    * @param organizerId
    * @param eventId
    * @param userId
    * @param eventTitle
    */
   private void addJoinNotification( final String organizerId, final String eventId, final String userId, final String eventTitle ) {
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications" ).child( organizerId );

      HashMap<String, Object> hashMap = new HashMap();
      hashMap.put( "userId",  userId );
      hashMap.put( "mentionedUserId", "" );
      hashMap.put( "text", " has joined your event called " +  eventTitle );
      hashMap.put( "eventId", eventId );
      hashMap.put( "isEvent", true );

      reference.push().setValue( hashMap );
   }

   /**
    * Adding a notification to organizer if someone leave to their events and setting notification
    * text according to this
    * @param organizerId
    * @param eventId
    * @param userId
    * @param eventTitle
    */
   private void addLeaveNotification( final String organizerId, final String eventId, final String userId, final String eventTitle ) {
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child( organizerId );

      HashMap<String, Object> hashMap = new HashMap();
      hashMap.put( "userId",  userId );
      hashMap.put( "mentionedUserId", "" );
      hashMap.put( "text", " has left your event called " +  eventTitle );
      hashMap.put( "eventId", eventId );
      hashMap.put( "isEvent", true );

      reference.push().setValue( hashMap );
   }
}