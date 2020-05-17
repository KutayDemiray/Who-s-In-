package com.cgty.denemeins.model;

/**
 * Board game event
 * @authors Cemhan Kaan Özaltan
 * @version 6.5.2020
 */
public class TabletopEvent extends Event {

    // constants
    static String[] gameTypes = { "Party Game", "Card Game", "Roleplay Game" };

    final static int PARTY_GAME_EVENT = 0;
    final static int CARD_GAME_EVENT = 1;
    final static int ROLEPLAY_GAME_EVENT = 2;

    // properties
    int gameType;

    // constructors
    public TabletopEvent() {

    }

    public TabletopEvent(String eventId, String title, String organizerId, EventDate date, String description, int capacity, String mainType, String subType, String location, String privacySetting, int gameType) {
        super(eventId, title, organizerId, date, description, capacity, mainType, subType, location, privacySetting);
        this.gameType = gameType;
    }

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