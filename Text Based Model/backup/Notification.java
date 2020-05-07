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
   public abstract void send( User sender, User receiver);
   
   public abstract void accept();
   
   public abstract void decline();
   
   public abstract boolean isAccepted();
}