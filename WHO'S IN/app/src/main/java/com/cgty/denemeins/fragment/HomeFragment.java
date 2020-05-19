package com.cgty.denemeins.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.cgty.denemeins.CreateEvent;
import com.cgty.denemeins.FeedEvents;
import com.cgty.denemeins.R;

/**
 * @authors Gökberk Keskinkılıç, Cagatay Safak, Cemhan Kaan Özaltan
 * @version 19.05.2020
 */
public class HomeFragment extends Fragment {

    // properties
    final String FEED_SPORTS = "Sports";
    final String FEED_MEETINGS = "Meeting";
    final String FEED_TABLETOP = "Tabletop Games";
    final String FEED_ALL = "";

    AppCompatButton buttonCreateEvent;
    AppCompatImageButton buttonToSports;
    AppCompatImageButton buttonToMeetings;
    AppCompatImageButton buttonToTabletop;
    AppCompatImageButton buttonToAllTypes;

    // constructors
    public HomeFragment() {
        //required empty public constructor.
    }

    // methods
    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.fragment_home, container, false );
        buttonCreateEvent = view.findViewById( R.id.buttonCreateEvent );
        buttonToSports = view.findViewById( R.id.buttonSports );
        buttonToMeetings = view.findViewById( R.id.buttonMeetings );
        buttonToTabletop = view.findViewById( R.id.buttonTabletop );
        buttonToAllTypes = view.findViewById( R.id.buttonAllTypes );

        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fromHomeToCreateEvent = new Intent( getActivity(), CreateEvent.class );
                startActivity( fromHomeToCreateEvent );
            }
        });

        buttonToSports.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent fromHomeToFeed = new Intent( getActivity(), FeedEvents.class );
                fromHomeToFeed.putExtra( "feedEventType", FEED_SPORTS );
                startActivity( fromHomeToFeed );
            }
        });

        buttonToMeetings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent fromHomeToFeed = new Intent( getActivity(), FeedEvents.class );
                fromHomeToFeed.putExtra( "feedEventType", FEED_MEETINGS );
                startActivity( fromHomeToFeed );
            }
        });

        buttonToTabletop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent fromHomeToFeed = new Intent( getActivity(), FeedEvents.class );
                fromHomeToFeed.putExtra( "feedEventType", FEED_TABLETOP );
                startActivity( fromHomeToFeed );
            }
        });

        buttonToAllTypes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent fromHomeToFeed = new Intent( getActivity(), com.cgty.denemeins.FeedEvents.class );
                fromHomeToFeed.putExtra( "feedEventType", FEED_ALL );
                startActivity( fromHomeToFeed );
            }
        });

        return view;
    }
}