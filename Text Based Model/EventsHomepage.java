import java.util.*;

/**
 * @author Kutay Demiray, Cemhan Kaan �zaltan, Ya??z Ya?ar
 */
public class EventsHomepage {
   
   
   public static void main( String[] args ) {
      
      EventList events = new EventList();
      Scanner scan = new Scanner( System.in );
      String name, nickname;
      User current, bilkentEsports, bilkentYES, bilkentRavens;
      int option;
         
      bilkentEsports = new User( "Denizhan Soyda?", "DS" );
      bilkentYES = new User( "Arda Tavusbay", "bilkent cs slave" );
      bilkentRavens = new User( "John Doe", "DOE" );
       
      events.addEvent( new SportsEvent( "Bilkent Esports 75th League of Legends Tournament", bilkentEsports, 80, Event.REQUEST_ONLY_EVENT, SportsEvent.VIDEO_GAMES_EVENT ) );
      events.addEvent( new MeetingEvent( "YES Talks: Volvo CEO", bilkentYES, 200, Event.PUBLIC_EVENT ) );
      events.addEvent( new SportsEvent( "Bilkent 2v2 CS:GO Tournament", bilkentEsports, 32, Event.PRIVATE_EVENT, SportsEvent.VIDEO_GAMES_EVENT ) );
      events.addEvent( new BoardGameEvent( "Dungeons & Dragons Night", bilkentRavens, 5, Event.INVITE_ONLY_EVENT, BoardGameEvent.ROLEPLAY_GAME_EVENT ) );
      
      
      System.out.println( "Welcome to Who's In, world's leading event organizing program!\nPlease sign up" );
      System.out.print( "Name: " );
      name = scan.nextLine();
      System.out.println();
      System.out.print( "Nickname: " );
      nickname = scan.nextLine();
      
      current = new User( name, nickname );
      System.out.println( "Logged in as user " + current.getNickname() );
      
      do {
         System.out.println( "What do you want to do?\n"
                           + "=======================\n"
                           + "1. List events\n" 
                           + "2. Create an event\n"
                           + "3. Search an event\n" 
                           + "4. Remove events\n"
                           + "5. Add friend\n"
                           + "=======================" );
         option = scan.nextInt();
         scan.nextLine();
         
         if ( option == 1 ) {
            Iterator iterator = events.iterator();
            while ( iterator.hasNext() ) {
               System.out.println( iterator.next() );
            }
         }
         else if ( option == 2 ) {
            System.out.print( "Title: " );
            String title = scan.nextLine();
            System.out.println();
            System.out.print( "Capacity: " );
            int capacity = scan.nextInt();
            scan.nextLine();
            System.out.println();
            System.out.print( "Access: " );
            int accessStatus = scan.nextInt();
            scan.nextLine();
            System.out.println();
            System.out.print( "Event type ( 0 = meeting, 1 = sports, 2 = board ): " );
            int eventType = scan.nextInt();
            scan.nextLine();
            System.out.println();
            System.out.print( "Event subtype: ");
            for ( String s : SportsEvent.gameTypes ) {
               System.out.print( s + " " );
            }
            System.out.println();
            for ( String s : BoardGameEvent.gameTypes ) {
               System.out.print( s + " " );
            }
            System.out.println();
            int eventSubtype = scan.nextInt();
            
            if ( eventType == 0 ) {
               events.addEvent( new MeetingEvent( title, current, capacity, accessStatus ) ); 
            }
            else if ( eventType == 1 ) {
               events.addEvent( new SportsEvent( title, current, capacity, accessStatus, eventSubtype ) );
            }
            else if ( eventType == 2 ) {
               events.addEvent( new BoardGameEvent( title, current, capacity, accessStatus, eventSubtype ) );
            }
            
         }
         else if ( option == 3 ) {
            System.out.print( "Which one:" );
            String searchParam = scan.nextLine();
            EventList tmp = new EventList( events.searchByKeywords( searchParam ) );
            
            
            Iterator iterator = tmp.iterator();
            while ( iterator.hasNext() ) {
               System.out.println( iterator.next() );
            }
            
         }
//         else if ( option == 4 ) {
//            System.out.print( "Which one:" );
//            String searchParam = scan.nextLine();
//            EventList tmp = new EventList( events.search( searchParam ) );
//            
//            
//            Iterator iterator = tmp.iterator();
//
//            
//            events.removeEvent( (Event) iterator.next() ); 
//         }
         else if ( option == 5 ) {
            System.exit(0);
         }
      } while ( true );
         
                                   
      
      
   }
   
   
   
   
   
}