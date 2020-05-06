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
   public EventList() {
      list = new ArrayList<>();
      // database
   }

   public EventList( ArrayList<Event> list ) {
      this.list = list;
   }

   // methods   
   public void addEvent( Event e ) {
      list.add( e );
   }

   public void removeEvent( Event e ) {
      list.remove( list.indexOf(e) );
   }

   public ArrayList<Event> search( String search ) {
      ArrayList<Event> tmp = new ArrayList<>();
      for ( Event e : list ) {
         if ( e.getTitle().contains( search ) )
            tmp.add( e );
      }
      return tmp;
   }

   public void sort() {

   }
   
   public Iterator iterator() {
      return list.iterator();
   }
}
