

import java.util.ArrayList;

/**
 * User class
 * @authors Kutay Demiray, Cemhan Kaan Özaltan, Yağız Yaşar
 * @version 1.0
 */
public class User {


   private String name;
   private String nickname;
   private String bio;
   private ArrayList<Event> pastEvents;
   //    private String profilePictureURL;
   private ArrayList<User> friendsList;
   public User(String name, String nickname) {
      this.name = name;
      this.nickname = nickname;
      bio = "";
   }

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
}