package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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

    // event types to compare with what we get from intent extra
    private final String FEED_SPORTS = "Sports";
    private final String FEED_MEETINGS = "Meeting";
    private final String FEED_TABLETOP = "Tabletop Games";
    private final String FEED_ALL = "";

    // properties
    private AppBarLayout feedEventsBar;
    private ImageView imageViewLogo;
    private RecyclerView feedEventsRecyclerView;
    private ArrayList<Event> mEvents;
    private EventAdapter eventAdapter;
    private Intent intent;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_feed_events );

        // initialize views
        feedEventsBar = findViewById( R.id.feedEventsBar );
        imageViewLogo = findViewById( R.id.imageViewLogo );
        feedEventsRecyclerView = findViewById( R.id.feedEventsRecyclerView );
        feedEventsRecyclerView.setHasFixedSize( true );
        feedEventsRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );

        // initialize adapter
        mEvents = new ArrayList<>();
        eventAdapter = new EventAdapter( this, mEvents );

        feedEventsRecyclerView.setAdapter( eventAdapter );

        // determine and display event type
        intent = getIntent();
        Log.d( "DENEME123", intent.toString() );
        String type = intent.getExtras().getString( "feedEventType" ); // event type
        Log.d( "DENEME123", type );

        readEvents( type );

    }

    /**
     * Fetches events with given type from database and displays them on screen
     * @param DISPLAY_EVENTS_TYPE Event type, supposed to be acquired from previous intent
     */
    private void readEvents( final String DISPLAY_EVENTS_TYPE ) {

        final DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events" );

        eventsRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                mEvents.clear();

                for ( DataSnapshot eventSnapshot : dataSnapshot.getChildren() ) {

                    Event event = eventSnapshot.getValue( Event.class );

                    boolean conditionDisplayAll = DISPLAY_EVENTS_TYPE.equals( FEED_ALL );
                    boolean conditionDisplayMeetings = DISPLAY_EVENTS_TYPE.equals( FEED_MEETINGS ) && event.getMainType().equals( FEED_MEETINGS );
                    boolean conditionDisplaySports = DISPLAY_EVENTS_TYPE.equals( FEED_SPORTS ) && event.getMainType().equals( FEED_SPORTS );
                    boolean conditionDisplayTabletop = DISPLAY_EVENTS_TYPE.equals( FEED_TABLETOP ) && event.getMainType().equals( FEED_TABLETOP );

                    // Display event if it is in the future and matches one of the criteria (only one of them can be true at the same time)
                    if ( !event.getDate().isPast() && ( conditionDisplayAll || conditionDisplayMeetings || conditionDisplaySports || conditionDisplayTabletop ) ) {
                        Log.d( "DENEME123", event.toString() );
                        mEvents.add(event);
                    }

                }

                eventAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }

        });

    }

}
