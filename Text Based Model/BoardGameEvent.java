/**
 * Board game event
 * @authors Cemhan Kaan Özaltan
 * @version 6.5.2020
 */
public class BoardGameEvent extends Event {

   // constants
   static String[] gameTypes = { "Party Game", "Card Game", "Roleplay Game" };
   
   final int PARTY_GAME_EVENT = 0;
   final int CARD_GAME_EVENT = 1;
   final int ROLEPLAY_GAME_EVENT = 2;

   // properties
   int gameType;

   // constructors
   public BoardGameEvent( String title, User organizer, Time duration, Date date, int capacity, Location location , int accessStatus, int gameType ) {
      super( title, organizer, duration, date, capacity, location accessStatus );
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
      return title + getGameType() + ", " + duration + ", " + date + ", " + capacity + ", " + location;
   }
}
