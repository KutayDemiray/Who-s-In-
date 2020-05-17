package com.cgty.denemeins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {

   TextView eventTitle, eventType, eventDateAndLocation, eventDescription, eventCapacity,
            eventParticipants;

   Button joinButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {

      
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_event);
   }


}
