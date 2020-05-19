package com.cgty.denemeins.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgty.denemeins.R;
import com.cgty.denemeins.model.Event;

import java.util.ArrayList;

/**
 * Event adapter class
 * @author Kutay Demiray
 * @version 1.0
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    // properties
    private Context mContext;
    private ArrayList<Event> mEvents;

    // constructors
    public EventAdapter(Context mContext, ArrayList<Event> mEvents) {
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

        holder.textViewTitleEventElement.setText( event.getTitle() );
        holder.textViewTypeEventElement.setText( event.getMainType() + " - " + event.getSubType() );
        holder.textViewUsernameEventElement.setText( event.getOrganizerId() ); // TODO change this to username instead of id
        holder.textViewLocationEventElement.setText( event.getLocation() );
        holder.textViewDateEventElement.setText( event.getDate().toString() );
        holder.textViewNoOfParticipantsEventElement.setText( "Capacity: " + event.getCapacity() ); // TODO fix to show current/max
        holder.textViewDescriptionEventElement.setText( event.getDescription() );
        holder.textViewPrivacySettingEventElement.setText( event.getPrivacySetting() );

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // properties
        public ImageView eventElementPP, imageViewJoinEventElement, imageViewDiscussEventElement;
        public TextView textViewTitleEventElement, textViewTypeEventElement, textViewUsernameEventElement,
                        textViewLocationEventElement, textViewDateEventElement, textViewNoOfParticipantsEventElement,
                        textViewDescriptionEventElement, textViewPrivacySettingEventElement;

        public ViewHolder( @NonNull View itemView ) {
            super( itemView );

            eventElementPP = itemView.findViewById( R.id.eventElementPP );
            imageViewJoinEventElement = itemView.findViewById( R.id.imageViewJoinEventElement );
            imageViewDiscussEventElement = itemView.findViewById( R.id.imageViewDiscussEventElement );

            textViewTitleEventElement = itemView.findViewById( R.id.textViewTitleEventElement );
            textViewTypeEventElement = itemView.findViewById( R.id.textViewTypeEventElement );
            textViewUsernameEventElement = itemView.findViewById( R.id.textViewUsernameEventElement );
            textViewLocationEventElement = itemView.findViewById( R.id.textViewLocationEventElement );
            textViewDateEventElement = itemView.findViewById( R.id.textViewDateEventElement );
            textViewNoOfParticipantsEventElement = itemView.findViewById( R.id.textViewNoOfParticipantsEventElement );
            textViewDescriptionEventElement = itemView.findViewById( R.id.textViewDescriptionEventElement );
            textViewPrivacySettingEventElement = itemView.findViewById( R.id.textViewDescriptionEventElement );

        }

    }

}
