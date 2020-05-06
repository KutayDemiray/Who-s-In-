public class MeetingEvent extends Event {
   
   public String toString() {
      return getTitle() + ", " + getOrganizer() + ", " + getCapacity();
   }
   
   public MeetingEvent( String title, User organizer, int capacity, int accessStatus ) {
      super( title, organizer, capacity, accessStatus );
   }
   
}