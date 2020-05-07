/**
 * Class to handle join event requests as objects
 * @author Yağız Yaşar
 * @version 1.0
 */
public class JoinRequest extends Notification {

    //properties
    Event e;

    //constructor
   public JoinRequest( User sender, Event e){
      super( sender, e.getOrganizer() );
      this.e = e;
   }
   
   //methods
   public String toString() {
      return sender.getName() + "'s join request to " + e.getTitle() + " is sent.";
   }
   
   public void send() {
      e.getOrganizer().addNotification( this) ;
      sender.addSentNotification( this );   
   } 
   
   public void accept() {
      e.addUser( sender);
   }
}
   