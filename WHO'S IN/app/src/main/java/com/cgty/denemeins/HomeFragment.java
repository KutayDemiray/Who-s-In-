package com.cgty.denemeins;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @authors Gökberk Keskinkılıç, Cagatay Safak
 */
public class HomeFragment extends Fragment
{
    Button buttonCreateEvent;
    Button buttonToSports;
    Button buttonToGatherings;
    Button buttonToTabletop;
    Button buttonToOther;

    public HomeFragment()
    {
        //required empty public constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonCreateEvent = (Button) view.findViewById(R.id.buttonCreateEvent);
        buttonToSports = (Button) view.findViewById(R.id.buttonSports);
        buttonToGatherings = (Button) view.findViewById(R.id.buttonGatherings);
        buttonToTabletop = (Button) view.findViewById(R.id.buttonTabletop);
        buttonToOther = (Button) view.findViewById(R.id.buttonOther);

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

        return view;
    }
}