/**
 * Class to handle notifications as objects
 * @author Yağız Yaşar
 * @version 1.0
 */
public abstract class Notification {
   
   //properties
   User sender;
   User receiver;
   
   //ctor
   public Notification( User sender, User receiver) {
      this.sender = sender;
      this.receiver = receiver;
   }
   
   //methods
   public User getReceiver() { return receiver; }
   
   public abstract void send();
   
   public abstract void accept();
   //public abstract void decline();
}