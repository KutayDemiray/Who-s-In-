package com.example.databasedeneme2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // properties
    EditText editTextTitle, editTextParticipants;
    Button buttonAddEvent;
    Spinner spinnerEventType, spinnerEventSubtype;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");

    ListView listViewEvents;
    List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = (EditText) findViewById( R.id.editTextTitle );
        editTextParticipants = (EditText) findViewById( R.id.editTextParticipants);
        buttonAddEvent = (Button) findViewById( R.id.buttonAddEvent);
        spinnerEventType = (Spinner) findViewById( R.id.spinnerEventType);
        spinnerEventSubtype = (Spinner) findViewById( R.id.spinnerEventSubtype);

        listViewEvents = (ListView) findViewById(R.id.listViewEvents);

        eventList = new ArrayList<>();

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
                editTextTitle.setText("");
                editTextParticipants.setText("");
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for ( DataSnapshot eventSnapshot : dataSnapshot.getChildren() ) {
                    Event event = eventSnapshot.getValue(Event.class);
                    eventList.add( event );
                }

                ArrayAdapter<Event> adapter = new EventList( MainActivity.this, eventList );
                listViewEvents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addEvent() {

        String title = editTextTitle.getText().toString().trim();
        String eventType = spinnerEventType.getSelectedItem().toString();
        String eventSubtype = spinnerEventSubtype.getSelectedItem().toString();
        int participants = Integer.parseInt( editTextParticipants.getText().toString() );

        if (TextUtils.isEmpty( title ) || TextUtils.isEmpty( eventType) || TextUtils.isEmpty( eventSubtype ) || TextUtils.isEmpty( editTextParticipants.getText().toString() ) ) {
            Toast.makeText( this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
        else {
            String id = ref.push().getKey();
            Event event = new Event( id, title, eventType, eventSubtype, participants );

            ref.child(id).setValue(event);

            Toast.makeText( this, "Event added to database", Toast.LENGTH_LONG).show();
        }

    }

}
