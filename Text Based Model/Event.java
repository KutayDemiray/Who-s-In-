import java.util.*;

/**
 * Event
 * @authors Cemhan Kaan Özaltan, Kutay Demiray
 * @version 5.5.2020
 */
public abstract class Event {
   
   // constants
   String[] privacySettings = { "Public Event", "Invite Only", "Request Only", "Private Event" };
   
   final static int PUBLIC_EVENT = 0;
   final static int INVITE_ONLY_EVENT = 1;
   final static int REQUEST_ONLY_EVENT = 2;
   final static int PRIVATE_EVENT = 3;
   
   // properties
   private String title;
   private User organizer;
   //private Time duration;
   //private Date date;
   private String description;
   private int capacity;
   private ArrayList<User> participants;
   //private Location location;
   private int accessStatus;

   // constructors
   public Event( String title, User organizer, int capacity, int accessStatus ) {
      this.title = title;
      this.organizer = organizer;
      //this.duration = duration;
      //this.date = date;
      this.capacity = capacity;
      //this.location = location;
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
   public void invite( User receiver ) {
      
   }


   /**
    * Returns the string equivalent of the privacy setting
    */
   public String getPrivacySetting() {
      return privacySettings[ accessStatus ];
   }

   public void setPrivacySetting( int accessStatus ) {
      this.accessStatus = accessStatus;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle( String title ) {
      this.title = title;
   }

//   public Time getDuration() {
//      return duration;
//   }
//
//   public void setDuration( Time duration ) {
//      this.duration = duration;
//   }

//   public Date getDate() {
//      return date;
//   }
//
//   public void setDate( Date date ) {
//      this.date = date;
//   }

   public String getDescription() {
      return description;
   }

   public void setDescription( String description ) {
      this.description = description;
   }

   public int getCapacity() {
      return capacity;
   }

   public void setCapacity( int capacity ) {
      this.capacity = capacity;
   }
   
   public User getOrganizer() {
      return organizer;
   }
   

//   public Location getLocation() {
//      return location;
//   }

//   public void setLocation( Location location ) {
//      this.location = location;
//   }

 }
