import java.util.*;

/**
 * EventList
 * @author Cemhan Kaan Özaltan, Kutay Demiray
 * @version 6.5.2020
 */

public class EventList implements Iterable {

   // properties
   private ArrayList<Event> list;

   // constructors
   /**
    * Constructor to create empty eventlist
    */
   public EventList() {
      list = new ArrayList<>();
   }
   
//   /**
//    * Constructor that pulls data from database to initialize list
//    * Takes database as input
//    */
//   public EventList() {
//      list = new ArrayList<>();
//      // use database to pull data and initialize properties
//   }
   
   /**
    * Copy constructor
    */
   public EventList( EventList other ) {
      list = other.list;
   }

   // methods   
   
   public void addEvent( Event e ) {
      list.add( e );
   }

   public void removeEvent( Event e ) {
      list.remove( list.indexOf(e) );
   }
   
   /**
    * Iterator generator, returns list property's iterator
    */
   public Iterator iterator() {
      return list.iterator();
   }
   
   // search methods
   
   /**
    * Search by keyword, first considers events containing parameter in their title, then considers events containing
    * search parameter in their description.
    */
   public EventList searchByKeywords( String search ) {
      EventList tmp = new EventList();
      
      for ( Event e : list ) {
         if ( e.getTitle().contains( search ) )
            tmp.addEvent( e );
      }
      for( Event e : list ) {
         if ( list.indexOf( e ) == -1 && e.getDescription().contains( search ) )
            tmp.addEvent( e );
      }
      
      return tmp;
   }
   
   /**
    * Search by 
   
   
   
   // sort methods
   
   /**
    * Sorts 
    */
   public void sortByTitle() {

   }
   
   
}
