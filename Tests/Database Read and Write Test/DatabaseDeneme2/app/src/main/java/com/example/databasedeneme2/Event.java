package com.example.databasedeneme2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Event {

    // properties
    String eventId;
    String title;
    String type;
    String subtype;
    int participants;



    public Event() {}


    public Event( String eventId, String title, String type, String subtype, int participants ) {
        this.eventId = eventId;
        this.title = title;
        this.type = type;
        this.subtype = subtype;
        this.participants = participants;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
