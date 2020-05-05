import java.util.*;
import java.awt.*;

/**
 * Event
 * @authors Cemhan Kaan Özaltan
 * @version 5.5.2020
 */
public class Event extends Observable {

   // properties
   private String[] privacySettings = { "Public Event", "Invite Only", "Private Event" }
   private String title;
   private User organizer;
   private Time duration;
   private Date date;
   private String description;
   private int capacity;
   private ArrayList<User> participants;
   private Location location;
   private int accessStatus;

   // constructors
   public Event( String title, User organizer, Time duration, int capacity, Location location , int accessStatus ) {
      this.title = title;
      this.organizer = organizer;
      this.duration = duration;
      this.capacity = capacity;
      this.location = location;
      this.accessStatus = accessStatus;
      participants = new ArrayList<>();
      addUser( organizer );
   }

   // methods
   /**
    * Adds the given user to the participants list
    * @param u the user to add
    */
   public void addUser( User u ) {
      participants.add(u);
      setChanged();
      notifyObservers();
   }

   /**
    * Removes a user from the participants list
    * @param u the user to remove
    */
   public void removeUser( User u ) {
      for ( int i = 0; i < participants.size(); i++ ) {
        if ( participants.get(i) == u )
           participants.remove(i);
      }
   }

   /**
    * Invites the given user to the event
    * @param u the user to invite
    */
   public void invite( User u ) {
      // create notification for user u, check if accepted
   }

   /**
    * Cancels the event
    */
   public void cancel() {
      this = null;
   }

   public String getPrivacySetting() {
      return accessStatus;
   }

   public void setPrivacySetting( int accessStatus ) {
      this.accessStatus = accessStatus;
      setChanged();
      notifyObservers();
   }

   public String getTitle() {
      return title;
   }

   public void setTitle( String title ) {
      this.title = title;
      setChanged();
      notifyObservers();
   }

   public Time getDuration() {
      return duration;
   }

   public void setDuration( Time duration ) {
      this.duration = duration;
      setChanged();
      notifyObservers();
   }

   public Date getDate() {
      return date;
   }

   public void setDate( Date date ) {
      this.date = date;
      setChanged();
      notifyObservers();
   }

   public String getDescription() {
      return description;
   }

   public void setDescription( String description ) {
      this.description = description;
      setChanged();
      notifyObservers();
   }

   public int getCapacity() {
      return capacity;
   }

   public void setCapacity( int capacity ) {
      this.capacity = capacity;
      setChanged();
      notifyObservers();
   }

   public Location getLocation() {
      return location;
   }

   public void setLocation( Location location ) {
      this.location = location;
      setChanged();
      notifyObservers();
   }
}
