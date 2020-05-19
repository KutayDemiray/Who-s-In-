package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.cgty.denemeins.adapter.EventAdapter;
import com.cgty.denemeins.model.Event;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Feed showing all events or only sports/meeting/tabletop events, depending on from where it was accessed.
 * Accessible from some buttons in HomeFragment.
 * @author Cagatay Safak, Kutay Demiray, Cemhan Kaan Özaltan
 * @version 1.0
 */
public class FeedEvents extends AppCompatActivity {

    // constants

    // event types
    private final int FEED_SPORTS = 0;
    private final int FEED_MEETINGS = 1;
    private final int FEED_TABLETOP = 2;
    private final int FEED_ALL = 3;

    // properties
    private AppBarLayout feedEventsBar;
    private Toolbar toolbarFeedEvents;
    private ImageView imageViewLogo;
    private RecyclerView feedEventsRecyclerView;
    private ArrayList<Event> mEvents;
    private EventAdapter eventAdapter;
    private Intent intent = getIntent();

    // Determines which events should be displayed. Acquired from HomeFragment
    final int DISPLAY_EVENTS_TYPE = intent.getIntExtra( "feedEventType", FEED_ALL );


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_feed_sports );

        feedEventsBar = findViewById( R.id.feedEventsBar );
        toolbarFeedEvents = findViewById( R.id.toolbarFeedEvents );
        imageViewLogo = findViewById( R.id.imageViewLogo );
        feedEventsRecyclerView = findViewById( R.id.feedEventsRecyclerView );

        mEvents = new ArrayList<>();
        eventAdapter = new EventAdapter( FeedEvents.this, mEvents );

        feedEventsRecyclerView.setAdapter( eventAdapter );

        readEvents();

    }

    private void readEvents() {

        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events" );

        eventsRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                mEvents.clear();

                for ( DataSnapshot eventSnapshot : dataSnapshot.getChildren() ) {

                    Event event = eventSnapshot.getValue( Event.class );

                    boolean conditionDisplayAll = DISPLAY_EVENTS_TYPE == FEED_ALL;
                    boolean conditionDisplayMeetings = DISPLAY_EVENTS_TYPE == FEED_MEETINGS && event.getMainType().equals( "Meeting" );
                    boolean conditionDisplaySports = DISPLAY_EVENTS_TYPE == FEED_SPORTS && event.getMainType().equals( "Sports" );
                    boolean conditionDisplayTabletop = DISPLAY_EVENTS_TYPE == FEED_TABLETOP && event.getMainType().equals( "Tabletop Games" );

                    // display event if it matches the criteria
                    if ( conditionDisplayAll || conditionDisplayMeetings || conditionDisplaySports || conditionDisplayTabletop ) {
                        mEvents.add(event);
                    }

                }

            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }

        });

    }

}
