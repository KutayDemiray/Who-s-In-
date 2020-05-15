package com.cgty.denemeins.Model;

public class MeetingEvent extends Event {

    public String toString() {
        return "Meeting Event Title: " + getTitle() + ", Organizer: " + getOrganizer() + ", Capacity: " + getCapacity() + ", Privacy: " + getPrivacySetting();
    }

    public MeetingEvent( String title, User organizer, int capacity, int accessStatus ) {
        super( title, organizer, capacity, accessStatus );
    }

}
