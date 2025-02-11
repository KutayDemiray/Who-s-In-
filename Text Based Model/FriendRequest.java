
/**
 * Class to handle friend requests as objects
 * @author Yağız Yaşar
 * @version 1.0
 */
public class FriendRequest extends Notification {

    //properties

    //constructor
   public FriendRequest( User sender, User receiver){
      super( sender, receiver);
   }
   
   //methods
   public String toString() {
      return sender.getName() + " sent friend request to " + receiver.getName() + ".";
   }
   
   public void send() {
      receiver.addNotification( this);
      sender.addSentNotification( this);
   }
   
   public void accept() {
      sender.addFriend( receiver);
   }
   
}

