package com.cgty.denemeins.Model;

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
    public BoardGameEvent( String title, User organizer, int capacity, int accessStatus, int gameType ) {
        super( title, organizer, capacity, accessStatus );
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
        return "Board Game Event Title: " + getTitle() + ", Organizer: " + getOrganizer() + ", Capacity: " + getCapacity() + ", Privacy: " + getPrivacySetting() + ", Game Type: " + getGameType();
    }
}
