package com.cgty.denemeins.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgty.denemeins.EventActivity;
import com.cgty.denemeins.R;
import com.cgty.denemeins.ShowFollowers;
import com.cgty.denemeins.model.Event;
import com.cgty.denemeins.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Event adapter for listing events
 * @author Kutay Demiray, Yağız Yaşar, Cemhan Kaan Özaltan
 * @version 1.0
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    // properties
    private Context mContext;
    private ArrayList<Event> mEvents;

    // constructors
    public EventAdapter( Context mContext, ArrayList<Event> mEvents ) {
        this.mContext = mContext;
        this.mEvents = mEvents;
    }

    // methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( mContext ).inflate( R.layout.event_element, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {

        final Event event = mEvents.get( position );
        Log.d( "DENEME123", event.getEventId() );

        // get organizer's username from database
        final String uId = event.getOrganizerId(); // variables are final so that they can be used in database listener
        final TextView username = holder.textViewUsernameEventElement; // similarly, final reference for text view

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference( "Users" );

        userRef.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                // get user value at node with the key uid and set text view's text to that user's username
                User u = dataSnapshot.child( uId ).getValue( User.class );
                username.setText( u.getUsername() );
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }

        });

        // get current and max participants from database similar to how we retrieved username
        final TextView participants = holder.textViewNoOfParticipantsEventElement;
        final ArrayList<String> participantList = new ArrayList<>();

        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference( "Events" ).child( event.getEventId() );

        eventRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                // clear then fill participantList from scratch with updated data, and update the textview
                participantList.clear();

                for ( DataSnapshot participantSnapshot : dataSnapshot.child( "participants" ).getChildren() ) {
                    String s = participantSnapshot.getValue( String.class );
                    participantList.add( s );
                }

                event.setParticipants( participantList );
                participants.setText( "Capacity: " + event.getParticipants().size() + "/" + event.getCapacity() );

            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }

        });

        // initialize text view texts
        holder.textViewTitleEventElement.setText( event.getTitle() );
        holder.textViewTypeEventElement.setText( event.getMainType() + " - " + event.getSubType() );
        holder.textViewLocationEventElement.setText( event.getLocation() );
        holder.textViewDateEventElement.setText( event.getDate().toString() );
        holder.textViewNoOfParticipantsEventElement.setText( "Capacity: " + " / " + event.getCapacity() );
        holder.textViewDescriptionEventElement.setText( "\"" + event.getDescription() + "\" " );
        holder.textViewPrivacySettingEventElement.setText( event.getPrivacySetting() );

        // add listener to the event as a whole so that when user clicks they go to event page
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences( "PREPS", Context.MODE_PRIVATE ).edit();
                editor.putString( "eventId", event.getEventId() );
                editor.apply();

                Intent fromNotificationToEvent = new Intent( mContext, EventActivity.class );
                fromNotificationToEvent.putExtra( "eventId", event.getEventId() );
                mContext.startActivity( fromNotificationToEvent );
            }
        });

        /**
         * Used a similar method to list the participants
         * @author Çağatay Şafak
         * @version 20.05.2020
         */
        holder.textViewNoOfParticipantsEventElement.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                Intent intent;
                intent = new Intent( mContext, ShowFollowers.class );
                
                intent.putExtra("id", event.getEventId() );
                intent.putExtra("title", "participants" );
                mContext.startActivity( intent );

            }

        });

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // properties
        public ImageView eventElementPP, imageViewJoinEventElement;
        public TextView textViewTitleEventElement, textViewTypeEventElement, textViewUsernameEventElement,
                        textViewLocationEventElement, textViewDateEventElement, textViewNoOfParticipantsEventElement,
                        textViewDescriptionEventElement, textViewPrivacySettingEventElement;

        public ViewHolder( @NonNull View itemView ) {
            super( itemView );

            eventElementPP = itemView.findViewById( R.id.eventElementPP );
            imageViewJoinEventElement = itemView.findViewById( R.id.imageViewJoinEventElement );
            textViewTitleEventElement = itemView.findViewById( R.id.textViewTitleEventElement );
            textViewTypeEventElement = itemView.findViewById( R.id.textViewTypeEventElement );
            textViewUsernameEventElement = itemView.findViewById( R.id.textViewUsernameEventElement );
            textViewLocationEventElement = itemView.findViewById( R.id.textViewLocationEventElement );
            textViewDateEventElement = itemView.findViewById( R.id.textViewDateEventElement );
            textViewNoOfParticipantsEventElement = itemView.findViewById( R.id.textViewNoOfParticipantsEventElement );
            textViewDescriptionEventElement = itemView.findViewById( R.id.textViewDescriptionEventElement );
            textViewPrivacySettingEventElement = itemView.findViewById( R.id.textViewPrivacySettingEventElement );

        }

    }

}
