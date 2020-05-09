package com.example.databasedeneme2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventList extends ArrayAdapter<Event> {

    private Activity context;
    private List<Event> eventList;

    public EventList( Activity context, List<Event> eventList ) {
        super( context, R.layout.list_layout, eventList );
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true );

        TextView title = (TextView) listViewItem.findViewById(R.id.title);
        TextView type = (TextView) listViewItem.findViewById(R.id.type);
        TextView subtype = (TextView) listViewItem.findViewById(R.id.subtype);
        TextView participants = (TextView) listViewItem.findViewById(R.id.participants);

        Event event = eventList.get(position);

        title.setText( event.getTitle() );
        type.setText( event.getType());
        subtype.setText( event.getSubtype());
        participants.setText( Integer.toString( event.getParticipants() ) );

        return listViewItem;
    }
}
