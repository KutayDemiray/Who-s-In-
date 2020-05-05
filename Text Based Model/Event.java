/**
 * Event
 * @authors Cemhan Kaan Özaltan
 * @version 5.5.2020
 */

public class Event {

  // properties
  private String[] privacySettings = { "Public Event", "Invite Only", "Private Event" }
  private String title;
  private User organizer;
  private Time duration;
  private Date date;
  private Strings description;
  private int capacity;
  private ArrayList<User> participants;
  private Location location;
  private int accessStatus;

  // constructors
  public Event( String title, User organiser, Time duration, int capacity, Location location , int accessStatus ) {
    this.title = title;
    this.organizer = organizer;
    this.duration = duration;
    this.capacity = capacity;
    this.location = location;
    this.accessStatus = accessStatus;
    addUser( organiser );
  }

  // methods
  /**
   * Adds the given user to the participants list
   * @param u the user to add
   */
  addUser( User u ) {
    participants.add(u);
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
}
