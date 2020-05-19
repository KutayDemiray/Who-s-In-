package com.cgty.denemeins;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cgty.denemeins.model.Event;
import com.cgty.denemeins.model.EventDate;
import com.cgty.denemeins.model.Notification;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Create event page
 * TODO create event under SportsEvent, MeetingEvent or TabletopEvent node depending on spinner output
 * @author Kutay Demiray
 * @version 1.0
 */
public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // views
    TextView textViewPageTitle;
    EditText editTextTitle, editTextLocation, editTextCapacity, editTextDate, editTextTime, editTextDescription;
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
        editTextTime = findViewById( R.id.editTextTime );
        editTextDescription = findViewById( R.id.editTextDescription );
        spinnerMainType = findViewById( R.id.spinnerMainType );
        spinnerSportsType = findViewById( R.id.spinnerSportsType );
        spinnerTabletopType = findViewById( R.id.spinnerTabletopType );
        spinnerPrivacy = findViewById( R.id.spinnerPrivacy );
        buttonAddEvent = findViewById( R.id.buttonAddEvent );
        //ceydas 11.05.20

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.mainTypes,
                R.layout.colored_spinner_layout);
        adapter1.setDropDownViewResource( R.layout.spinner_dropdown_layout);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.privacySettings,
                R.layout.colored_spinner_layout);
        adapter1.setDropDownViewResource( R.layout.spinner_dropdown_layout);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(
                this,
                R.array.subTypesSports,
                R.layout.colored_spinner_layout);
        adapter1.setDropDownViewResource( R.layout.spinner_dropdown_layout);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(
                this,
                R.array.subTypesTabletop,
                R.layout.colored_spinner_layout);
        adapter1.setDropDownViewResource( R.layout.spinner_dropdown_layout);

        spinnerMainType.setAdapter(adapter1);
        spinnerMainType.setOnItemSelectedListener(this);

        spinnerPrivacy.setAdapter(adapter2);
        spinnerPrivacy.setOnItemSelectedListener(this);

        spinnerSportsType.setAdapter(adapter3);
        spinnerSportsType.setOnItemSelectedListener(this);

        spinnerTabletopType.setAdapter(adapter4);
        spinnerTabletopType.setOnItemSelectedListener(this);

        initializeInputs();

        // set calendar popup to select date
        editTextDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog;
                int day, month, year;

                // get and store today's date in int variables
                final Calendar calendar = Calendar.getInstance();
                day = calendar.get( Calendar.DAY_OF_MONTH );
                month = calendar.get( Calendar.MONTH );
                year = calendar.get( Calendar.YEAR );

                // date picker dialog

                // initialize datePickerDialog with today's date
                datePickerDialog = new DatePickerDialog(CreateEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDate.setText( String.format( "%02d/%02d/%04d", dayOfMonth, month, year ) );
                            }
                        }, year, month, day );
                datePickerDialog.show();

            }
        });

        // set hour spinner popup to select the time
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog;
                int hour, minute;

                // get and store the hour and minute at the moment
                final Calendar calendar = Calendar.getInstance();
                hour = calendar.get( Calendar.HOUR_OF_DAY );
                minute = calendar.get( Calendar.MINUTE );

                // time picker dialog

                // initialize timePickerDialog with current time
                timePickerDialog = new TimePickerDialog(CreateEvent.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editTextTime.setText( String.format( "%02d:%02d", hourOfDay, minute) );
                    }
                }, hour, minute, true );
                timePickerDialog.show();

            }
        });

        // add main type spinner a listener so that only relevant subtype spinner is revealed
        spinnerMainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
            public void onNothingSelected( AdapterView<?> adapterView ) {}
        });

        // set button click listener
        buttonAddEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //cagatay
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDate = sdf.format(new Date());

                String strTitle = editTextTitle.getText().toString();
                String strLocation = editTextLocation.getText().toString();
                String strCapacity = editTextCapacity.getText().toString();  //maybe int...? String is easier anyways
                String strDate = editTextDate.getText().toString();

                if ( TextUtils.isEmpty(strTitle) || TextUtils.isEmpty(strLocation) || TextUtils.isEmpty(strCapacity) || TextUtils.isEmpty(strDate))
                    Toast.makeText(CreateEvent.this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();

                else if ( strCapacity.equals( "1")){
                    Toast.makeText(CreateEvent.this, "Capacity cannot be less than 2.", Toast.LENGTH_SHORT).show();
                }
                // TODO else if selected date is past, raise toast with error message. still?
                else if ( ( strCapacity.equals( "42")) && ( strLocation.equalsIgnoreCase("çorum") || strLocation.equalsIgnoreCase("corum") ) ){
                    addEvent();
                    initializeInputs();
                    Toast.makeText(CreateEvent.this, "PARABÉNS! CONGRATS! TEBRİKLER! THE BEST COMBINATION :) ", Toast.LENGTH_LONG).show();
                    finish();
                }

                else if ( strCapacity.equals( "42") ) {
                    addEvent();
                    initializeInputs();
                    Toast.makeText(CreateEvent.this, "PARABÉNS! CONGRATS! TEBRİKLER! \nKONYA IS THE MEANING OF THE LIFE :) ", Toast.LENGTH_LONG).show();
                    finish();
                }

                else if ( strLocation.equalsIgnoreCase("çorum") || strLocation.equalsIgnoreCase("corum")){
                    addEvent();
                    initializeInputs();
                    Toast.makeText(CreateEvent.this, "PARABÉNS! CONGRATS! TEBRİKLER! \nJourney to the Center of the Earth, HUH? :) ", Toast.LENGTH_LONG).show();
                    finish();
                }

                else if ( strLocation.equalsIgnoreCase("quiquendone") ){
                    addEvent();
                    initializeInputs();
                    Toast.makeText(CreateEvent.this, "PARABÉNS! CONGRATS! TEBRİKLER! \nIS THAT DR. OX? ", Toast.LENGTH_LONG).show();
                    finish();
                }

                else{
                    addEvent();
                    initializeInputs();
                    Toast.makeText(CreateEvent.this, "You have created an event successfully.", Toast.LENGTH_SHORT).show();
                    finish();

                }

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
        String subType = ""; // TODO
        String mainType = spinnerMainType.getSelectedItem().toString();

        EventDate date = new EventDate( editTextDate.getText().toString(), editTextTime.getText().toString() );

        String description = editTextDescription.getText().toString();

        // get subtype only from the relevant subtype spinner
        if ( spinnerMainType.getSelectedItem().toString().equals( "Sports" ) ) {
            subType = spinnerSportsType.getSelectedItem().toString();
        }
        else if ( spinnerMainType.getSelectedItem().toString().equals( "Tabletop Games") ) {
            subType = spinnerTabletopType.getSelectedItem().toString();
        }
        else if ( spinnerMainType.getSelectedItem().toString().equals( "Meeting") ) {
            subType = ""; // TODO
        }

        Event event = new Event( eventId, title, organizerId, date, description, capacity, mainType, subType, location, privacySetting );

        ref.child("Events").child( eventId ).setValue( event );

        addNotifications( eventId, organizerId, event.getTitle() );

    }

    /**
     * Reverts input fields to default values
     */
    private void initializeInputs() {
        editTextTitle.setText("");
        editTextLocation.setText("");
        editTextCapacity.setText("");

        // set default date and time as current
        EventDate date = EventDate.getInstance();
        editTextDate.setText( date.getCalendarDate() );
        editTextTime.setText( date.getTimeOfDay() );

        editTextDescription.setText("");
        spinnerMainType.setSelection(0);
        spinnerSportsType.setSelection(0);
        spinnerTabletopType.setSelection(0);
        spinnerPrivacy.setSelection(0);
    }

    @Override
    //ceydas
    public void onItemSelected(AdapterView<?> adapterView , View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addNotifications(final String eventId, String organizerId, final String eventTitle) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow").child( organizerId).child("followers");
        reference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for ( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Notifications").child( snapshot.getKey());

                    HashMap<String, Object> hashMap = new HashMap();
                    hashMap.put("userId", firebaseUser.getUid());
                    hashMap.put("text", " created an event called " + eventTitle + ".");
                    hashMap.put("eventId", eventId);
                    hashMap.put("isEvent", true);

                    reference2.push().setValue(hashMap);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
