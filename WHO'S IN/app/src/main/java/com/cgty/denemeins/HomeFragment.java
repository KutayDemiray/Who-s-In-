package com.cgty.denemeins;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.cgty.denemeins.model.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @authors Gökberk Keskinkılıç, Cagatay Safak
 */
public class HomeFragment extends Fragment
{
    AppCompatButton buttonCreateEvent;
    AppCompatImageButton buttonToSports;
    AppCompatImageButton buttonToGatherings;
    AppCompatImageButton buttonToTabletop;
    AppCompatImageButton buttonToOther;

    public HomeFragment()
    {
        // required empty public constructor.
    }

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        User u = User.getUser( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        buttonCreateEvent = view.findViewById(R.id.buttonCreateEvent);
        buttonToSports = view.findViewById(R.id.buttonSports);
        buttonToGatherings = view.findViewById(R.id.buttonGatherings);
        buttonToTabletop = view.findViewById(R.id.buttonTabletop);
        buttonToOther =  view.findViewById(R.id.buttonOther);

        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fromHomeToCreateEvent = new Intent( getActivity(), CreateEvent.class);
                startActivity( fromHomeToCreateEvent);
            }
        });

        buttonToSports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent fromHomeToFeedSports = new Intent( getActivity(), FeedSports.class);
                startActivity( fromHomeToFeedSports);
            }
        });

        buttonToGatherings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent fromHomeToFeedGatherings = new Intent( getActivity(), FeedGatherings.class);
                startActivity( fromHomeToFeedGatherings);
            }
        });

        buttonToTabletop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent fromHomeToFeedGatherings = new Intent( getActivity(), FeedGatherings.class);
                startActivity( fromHomeToFeedGatherings);
            }
        });

        return view;
    }
}