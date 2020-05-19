package com.cgty.denemeins.model;


/**
 * Class for in app notifications
 * @author Yağız Yaşar
 */
public class Notification {
   //constants
   private final String[] NOTIFICATION_TYPES = { "Follower", "Event is Created" }; // TODO: More types will be added

   //properties
   private String id;
   private String text;
   private String eventId;
   private String userId;
   private int    notificationType;


   public Notification( String id, String text, String eventId, String userId, int notificationType ) {
      this.id = id;
      this.text = text;
      this.eventId = eventId;
      this.userId = userId;
      this.notificationType = notificationType;
   }

   public Notification() {
   }

   public String getEventId() {
      return eventId;
   }

   public void setEventId( String eventId ) {
      this.eventId = eventId;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId( String userId ) {
      this.userId = userId;
   }

   public int getNotificationType() {
      return notificationType;
   }

   public void setNotificationType( int notificationType ) {
      this.notificationType = notificationType;
   }

   public String getId() {
      return id;
   }

   public void setId( String id ) {
      this.id = id;
   }

   public String getText() {
      return text;
   }

   public void setText( String text ) {
      this.text = text;
   }

   public boolean isEvent() {
      if ( notificationType == 1 )
         return true;
      else
         return false;
   }

   public boolean isFollow() {
      if ( notificationType == 0 )
         return true;
      else
         return false;
   }
}