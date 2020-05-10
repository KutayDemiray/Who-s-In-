package com.cgty.denemeins;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cgty.denemeins.model.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Create event page (View + Controller)
 * @author Kutay Demiray
 * @version 1.0
 */
public class CreateEvent extends AppCompatActivity {

    // database reference
    DatabaseReference eventsReference = FirebaseDatabase.getInstance().getReference("Events" );
    DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");
    // views
    TextView textViewPageTitle;
    EditText editTextTitle, editTextLocation, editTextCapacity, editTextDate, editTextDescription;
    Spinner spinnerMainType, spinnerSportsType, spinnerTabletopType, spinnerPrivacy;
    Button buttonAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // initialize views
        textViewPageTitle = findViewById( R.id.textViewPageTitle );
        editTextTitle = findViewById( R.id.editTextTitle );
        editTextLocation = findViewById( R.id.editTextLocation );
        editTextCapacity = findViewById( R.id.editTextCapacity);
        editTextDate = findViewById( R.id.editTextDate );
        editTextDescription = findViewById( R.id.editTextDescription );
        spinnerMainType = findViewById( R.id.spinnerMainType );
        spinnerSportsType = findViewById( R.id.spinnerSportsType );
        spinnerTabletopType = findViewById( R.id.spinnerTabletopType );
        spinnerPrivacy = findViewById( R.id.spinnerPrivacy );
        buttonAddEvent = findViewById( R.id.buttonAddEvent );

        initializeInputs();

        // set main type spinner listener so that only relevant subtype spinner is revealed
        spinnerMainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // TODO
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ( spinnerMainType.getSelectedItem().toString().equals( "Meeting") ) {
                    spinnerSportsType.setVisibility( Spinner.GONE );
                    spinnerTabletopType.setVisibility( Spinner.GONE );
                }
                else if ( spinnerMainType.getSelectedItem().toString().equals( "Sports") ) {
                    spinnerSportsType.setVisibility( Spinner.VISIBLE );
                    spinnerTabletopType.setVisibility( Spinner.GONE );
                }
                else if ( spinnerMainType.getSelectedItem().toString().equals( "Tabletop Games") ) {
                    spinnerSportsType.setVisibility( Spinner.GONE );
                    spinnerTabletopType.setVisibility( Spinner.VISIBLE );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set button click listener
        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
                initializeInputs();
            }
        });

    }

    /**
     * Adds event to database according to information on editText's
     */
    private void addEvent() {

        // use database to get id data
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(); // create new database reference
        String organizerId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // get user id using auth tools
        String eventId = ref.child("Events").push().getKey(); // creates new node in events and gets its unique id

        // fetch rest of the data from views to use in Event constructor
        String title = editTextTitle.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        int capacity = Integer.parseInt( editTextCapacity.getText().toString() );
        String privacySetting = spinnerPrivacy.getSelectedItem().toString();
        String subType = ""; // if event main type is not meeting, it will be changed accordingly later
        String mainType = spinnerMainType.getSelectedItem().toString();

        // format date
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/YYYY");
        Date date = new Date();
        try {
            date = dateFormat.parse( editTextDate.getText().toString() );
        }
        catch ( Exception ParseException ) {}

        String description = editTextDescription.getText().toString();

        // get subtype only from the relevant subtype spinner
        if ( spinnerMainType.getSelectedItem().toString().equals( "Sports" ) ) {
            subType = spinnerSportsType.getSelectedItem().toString();
        }
        else if ( spinnerMainType.getSelectedItem().toString().equals( "Tabletop Games") ) {
            subType = spinnerTabletopType.getSelectedItem().toString();
        }
        else if ( spinnerMainType.getSelectedItem().toString().equals( "Meeting") ) {
            subType = "";
        }

        Event event = new Event( eventId, title, organizerId, date, description, capacity, mainType, subType, location, privacySetting );

        ref.child("Events").child( eventId ).setValue( event );

    }

    /**
     * Reverts input fields to default values
     */
    private void initializeInputs() {
        editTextTitle.setText("");
        editTextLocation.setText("");
        editTextCapacity.setText("");

        // set default date as today's date
        Date date = new Date();
        editTextDate.setText( date.getDate() + "/" + ( date.getMonth() + 1 ) + "/" + ( date.getYear() + 1900 ) );

        editTextDescription.setText("");
        spinnerMainType.setSelection(0);
        spinnerSportsType.setSelection(0);
        spinnerTabletopType.setSelection(0);
        spinnerPrivacy.setSelection(0);
    }
}
