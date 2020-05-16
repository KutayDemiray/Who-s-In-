package com.cgty.denemeins.Model;

import java.util.Date;

/**
 * Sports event class, includes sports types
 * @author Kutay Demiray
 */
public class SportsEvent extends Event {

    // constants
    final static String[] gameTypes = { "Football" , "Basketball" , "Volleyball" , "Video Game" , "Other"};

    final static int FOOTBALL_EVENT = 0;
    final static int BASKETBALL_EVENT = 1;
    final static int VOLLEYBALL_EVENT = 2;
    final static int VIDEO_GAMES_EVENT = 3;
    final static int OTHER_SPORTS_EVENT = 4;

    // properties
    private int gameType;

    public SportsEvent() {
    }

    public SportsEvent(String eventId, String title, String organizerId, Date date, String description, int capacity, String mainType, String subType, String location, String privacySetting, int gameType) {
        super(eventId, title, organizerId, date, description, capacity, mainType, subType, location, privacySetting);
        this.gameType = gameType;
    }

    // constructor

    // methods

    public String getGameType() {
        return gameTypes[ gameType ];
    }

    public void setGameType( int gameType ) {
        this.gameType = gameType;
    }

    public String toString() {
        return "Board Game Event Title: " + getTitle() + ", Organizer: " + getOrganizerId() + ", Capacity: " + getCapacity() + ", Privacy: " + getPrivacySetting() + ", Game Type: " + getGameType();
    }

}
