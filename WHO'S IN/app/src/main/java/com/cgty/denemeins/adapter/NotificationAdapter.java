package com.cgty.denemeins.adapter;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.EventActivity;
import com.cgty.denemeins.model.User;
import com.cgty.denemeins.fragment.ProfileFragment;
import com.cgty.denemeins.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.cgty.denemeins.model.Notification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.cgty.denemeins.R.id.notificationText;

/**
 * A class for notification adapter so that the notifications can be regulated according to the data
 * in database
 * @author Yağız Yaşar
 * @version 17.5.20
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

   private Context mContext;
   private List<Notification> mNotification; //List of notifications

   public NotificationAdapter( Context mContext, List<Notification> mNotification ) {
      this.mContext = mContext;
      this.mNotification = mNotification;
   }

   /**
    * Returns the number of notifications that user have
    */
   @Override
   public int getItemCount() {
      return mNotification.size();
   }

   /**
    * Creates notification adapters in the fragment at first
    * @param viewGroup - group of the view
    * @param i - type of view
    * @return view of notification
    */
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ) {
      View view = LayoutInflater.from( mContext ).inflate( R.layout.notification_element,viewGroup, false );
      return new NotificationAdapter.ViewHolder( view );
   }

   /**
    * Creates and changes notification adapters in the fragment live
    * @param viewHolder
    * @param i
    */
   public void onBindViewHolder( @NonNull ViewHolder viewHolder, int i ) {

      final Notification notification = mNotification.get( i ); //getting the notification in the position of i

      viewHolder.text.setVisibility( View.VISIBLE );

      getUserInfo( viewHolder.profilePicture, viewHolder.text, notification.getUserId(), i );

      /**
       * Setting a click on listener so that users can open others' profiles or event pages with one click
       * from notification tab
       */
      viewHolder.itemView.setOnClickListener( new View.OnClickListener() {

            @Override
              public void onClick( View view ) {
               if (  notification.getEventId().equals( "" ) ) {
                  // if eventId is empty, it is a notification related to follow so that if
                  // they click on notification they go to an user fragment
                  SharedPreferences.Editor editor = mContext.getSharedPreferences( "PREPS", Context.MODE_PRIVATE ).edit();
                  editor.putString( "userId", notification.getUserId() );
                  editor.apply();

                  ( (FragmentActivity) mContext ).getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                          new ProfileFragment() ).commit();
               } else {
                  // if eventId is empty, it is a notification related to follow so that if
                  // they click on notification they go to an user fragment
                  SharedPreferences.Editor editor = mContext.getSharedPreferences( "PREPS", Context.MODE_PRIVATE ).edit();
                  editor.putString( "eventId", notification.getEventId() );
                  editor.apply();

                  Intent fromNotificationToEvent = new Intent( mContext, EventActivity.class );
                  fromNotificationToEvent.putExtra( "eventId", notification.getEventId() );
                  mContext.startActivity( fromNotificationToEvent );
               }
            }
         });
      }

   /**
    * Helper method for onBindViewHolder that initializes some of the variables and creates
    * event listeners for it
    * @param imageView
    * @param username
    * @param userId
    */
   private void getUserInfo( final ImageView imageView, final TextView notificationText, String userId, int i) {
      final Notification notification = mNotification.get( i );
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users" ).child( userId );
      reference.addValueEventListener( new ValueEventListener() {

         @Override
         public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
            User user = dataSnapshot.getValue( User.class );
            notificationText.setText( user.getUsername() + notification.getText() );
            Glide.with( mContext ).load( user.getPicurl() ).into( imageView );
         }

         @Override
         public void onCancelled( @NonNull DatabaseError databaseError ) {

         }
      });
   }

   /**
    * inner class for view holder
    * @author Yağız Yaşar
    * @version 17.5.20
    */
   public class ViewHolder extends RecyclerView.ViewHolder {

      public ImageView profilePicture;
      public TextView text;

      public ViewHolder( @NonNull View itemView ) {
         super( itemView );

         profilePicture = itemView.findViewById( R.id.profilePictureElement );
         text = itemView.findViewById( notificationText );
      }
   }
}
