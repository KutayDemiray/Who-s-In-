

import java.util.ArrayList;

/**
 * User class
 * @authors Kutay Demiray, Cemhan Kaan Özaltan, Yağız Yaşar
 * @version 1.0
 */
public class User {
   
   //properties
   private String name;
   private String nickname;
   private String bio;
   private ArrayList<Event> events;
   //    private String profilePictureURL;
   private ArrayList<User> friendsList;
   private ArrayList<Notification> notifications;
   private ArrayList<Notification> sentNotifications;
   
   //ctor
   public User(String name, String nickname) {
      this.name = name;
      this.nickname = nickname;
      bio = "";
      events = new ArrayList<Event>();
      friendsList = new ArrayList<User>();
      notifications = new ArrayList<Notification>();
   }
   
   //methods
   public String getName() {
      return name;
   }
   
   public String getNickname() {
      return nickname;
   }
   
   public String getBio() { return bio; }
   
//    public String getProfilePictureURL() { return profilePictureURL; }
   
   /*   public void setProfilePictureURL( String profilePictureURL) {
    *    this.profilePictureURL = profilePictureURL;
    *  }
    */
   public void setBio( String bio) { this.bio = bio; }
   
   public String toString() {
      return nickname;
   }
   
   public String eventsToString() {
      String str = "";
      
      for ( int i = 0; i < events.size(); i++) {
         str = events.get(i).getTitle() + "\n";
      }
      return str;
   }
   
   public String friendsToString() {
      String str = "";
      
      for ( int i = 0; i < events.size(); i++) {
         str = str + friendsList.get(i).getName() + "\n";
      } 
      return str;
   }
   
   public String showNotifications() {
      String str = "";
      for ( int i = 0; i < notifications.size(); i++) {
         str = str + notifications.get(i).toString() + "\n";
      }
      return str;
   }
   
   public void addNotification( Notification n) {
      this.notifications.add( n);
   }
   
   public void addSentNotification( Notification n) {
      this.sentNotifications.add( n);
   }
   
   public void addFriend( User user) {
      this.friendsList.add( user);
   }
   

   
   
   
  
   
   
   
   
   
   
   
}