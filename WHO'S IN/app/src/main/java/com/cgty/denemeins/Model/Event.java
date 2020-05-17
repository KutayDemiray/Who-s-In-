package com.cgty.denemeins.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Event model class
 * @author Kutay Demiray, Cemhan Kaan Özaltan, Yağız Yaşar
 * @version 2.0
 */
public class Event {

    // properties
    private User organizer;
    private String eventId;  //
    private String title;  //
    private String organizerId;
    private EventDate date;
    private String description;  //
    private int capacity;
    private String mainType;
    private String subType;
    private ArrayList<String> participants;
    private String location;
    private String privacySetting;

    // constructors

    /**
     * Empty constructor to use in database
     */
    public Event() {}

    /**
     * Regular constructor to use in parts of program code
     * @param eventId Event's ID (IMPORTANT: get an unique one from database by pushing a new node, don't create your id by yourself)
     * @param title Event Title
     * @param organizerId Organizer's User ID
     * @param date Event's time
     * @param description Short description of event
     * @param capacity Maximum number of participants
     * @param location Location (String for now, will be changed to a location on the map later on)
     * @param privacySetting Privacy setting (Only public is implemented for now)
     */
    public Event( User organizer, String eventId, String title, String organizerId, EventDate date, String description, int capacity, String mainType, String subType, String location, String privacySetting ) {
        this.organizer = organizer;
        this.eventId = eventId;
        this.title = title;
        this.organizerId = organizerId;
        this.date = date;
        this.description = description;
        this.capacity = capacity;
        this.mainType = mainType;
        this.subType = subType;
        this.location = location;
        this.privacySetting = privacySetting;
        participants = new ArrayList<>();
        addParticipant( this, getOrganizerId() );
    }

    // methods

    /**
     * Adds participant's id to participants and updates database accordingly
     * @param event Event to add participant to
     * @param participantId User ID of participants
     */
    public static void addParticipant( Event event, String participantId ) {
        // add participant id to local list of participants
        event.getParticipants().add( participantId );

        // update database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("events");
        reference.child( event.getEventId() ).child("participants").setValue( event.getParticipants() );
    }

    // getters and setters (most should never be used but they are required for adding event objects to database)
    public User getOrganizer()
    {
        return organizer;
    }

    public String getEventId() {
        return eventId;
    }


    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public EventDate getDate() {
        return date;
    }

    public void setDate(EventDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrivacySetting() {
        return privacySetting;
    }

    public void setPrivacySetting(String privacySetting) {
        this.privacySetting = privacySetting;
    }

}