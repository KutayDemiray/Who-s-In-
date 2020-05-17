package com.cgty.denemeins.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.Model.Event;
import com.cgty.denemeins.Model.User;
import com.cgty.denemeins.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Event adapter class for displaying events on screen
 * @author Kutay Demiray, Yağız Yaşar
 * @version 1.0 17.05.2020
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    // inner class
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }

    // properties
    private Context mContext;
    private List<Event> mEvent;

    // constructor
    public EventAdapter( Context mContext, List<Event> mEvent ) {
        this.mContext = mContext;
        this.mEvent = mEvent;
    }

    // methods

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate( R.layout.event_element, parent, false );
        return new EventAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        final Event event = mEvent.get( position );
        getEventInfo( holder.textViewTitle, holder.textViewOrganizer, holder.textViewType, holder.textViewDate, holder.textViewLocation, holder.textViewPrivacySetting,
                      holder.textViewParticipants, event.getEventId() );

    }

    @Override
    public int getItemCount() {
        return mEvent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // properties
        public TextView textViewTitle, textViewType, textViewParticipants, textViewOrganizer,
                 textViewDate, textViewLocation, textViewPrivacySetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById( R.id.textViewTitle );
            textViewOrganizer = itemView.findViewById( R.id.textViewOrganizer );
            textViewType = itemView.findViewById( R.id.textViewType );
            textViewDate = itemView.findViewById( R.id.textViewDate );
            textViewLocation = itemView.findViewById( R.id.textViewLocation );
            textViewPrivacySetting = itemView.findViewById( R.id.textViewPrivacySetting );
            textViewParticipants = itemView.findViewById( R.id.textViewParticipants );
        }

    }

    private void getEventInfo(final TextView textViewTitle, final TextView textViewOrganizer, final TextView textViewType, final TextView textViewDate,
                              final TextView textViewLocation, final TextView textViewPrivacySetting, final TextView textViewParticipants, String eventId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "Events").child( eventId );
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue( Event.class );
                User organizer = dataSnapshot.child( "Users" ).child( event.getOrganizerId() ).getValue( User.class );
                textViewTitle.setText( event.getTitle() );
                textViewOrganizer.setText( organizer.getUsername() );
                textViewType.setText( event.getMainType() + "-" + event.getSubType() );
                textViewDate.setText( event.getDate().toString() );
                textViewLocation.setText( event.getLocation() );
                textViewPrivacySetting.setText( event.getPrivacySetting() );
                textViewParticipants.setText( event.getParticipants().toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
