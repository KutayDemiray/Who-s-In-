/**
 * Class to handle invitation to events as objects
 * @author Yağız Yaşar
 * @version 1.0
 */
public class Invitation extends Notification {

    //properties
    Event e;

    //constructor
   public Invitation( User receiver, Event e){
      super( e.getOrganizer(), receiver );
      this.e = e;
   }
   
   //methods
   public String toString() {
      return e.getOrganizer().getName() + " invited " + receiver.getName() + "to the event " + e.getTitle() + " is sent.";
   }
   
   
   public void send() {
      receiver.addNotification( this);
      e.getOrganizer().addSentNotification( this);
   }
   
   public void accept() {
      e.addUser( receiver);
   }
}
   