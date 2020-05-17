package com.cgty.denemeins.adapter;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.model.User;
import com.cgty.denemeins.ProfileFragment;
import com.cgty.denemeins.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.cgty.denemeins.model.Notification;

import android.content.Context;
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
 * A class for notification adapter
 * @author Yağız Yaşar
 * @version 17.5.20
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

   private Context mContext;
   private List<Notification> mNotification;

   public NotificationAdapter(Context mContext, List<Notification> mNotification) {
      this.mContext = mContext;
      this.mNotification = mNotification;
   }

   /**
    * Returns the number of notifications
    */
   @Override
   public int getItemCount() {
      return mNotification.size();
   }

   /**
    * Creates the notification element which will be held in the notification fragment
    * @param viewGroup - group of the view
    * @param i - type of view
    * @return view of notification
    */
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i) {
      View view = LayoutInflater.from(mContext).inflate(R.layout.notification_element,viewGroup, false);
      return new NotificationAdapter.ViewHolder( view);
   }

   /**
    * Sets texts and images for notification, and click listeners
    * @param viewHolder
    * @param i
    */
   public void onBindViewHolder( @NonNull ViewHolder viewHolder, int i) {

      final Notification notification = mNotification.get(i);

      viewHolder.text.setVisibility(View.VISIBLE);

      viewHolder.text.setText( notification.getText() );
      getUserInfo( viewHolder.profilePicture, viewHolder.username, notification.getUserId() );

      viewHolder.itemView.setOnClickListener( new View.OnClickListener() {

            @Override
              public void onClick( View view) {
               if ( notification.isEvent() ) {
                  SharedPreferences.Editor editor = mContext.getSharedPreferences( "PREPS", Context.MODE_PRIVATE).edit();
                  editor.putString( "eventId", notification.getEventId() );
                  editor.apply();

                 // ( (FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,
                 //         new EventFragment() ).commit(); //TODO: Event fragment
               } else {
               SharedPreferences.Editor editor = mContext.getSharedPreferences( "PREPS", Context.MODE_PRIVATE).edit();
               editor.putString( "userId", notification.getUserId());
               editor.apply();

                  ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                          new ProfileFragment() ).commit();
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
   private void getUserInfo(final ImageView imageView, final TextView username, String userId) {
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
      reference.addValueEventListener(new ValueEventListener() {

         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue( User.class);
            username.setText( user.getUsername() );
            Glide.with(mContext).load( user.getPpURL() ).into( imageView);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
      });
   }

   /*private void getEventInfo(final TextView textView, String eventId) {
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "Events").child( eventId);
      reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Event event = dataSnapshot.getValue( Event.class);
            Glide.with(mContext).load( event.getTitle()).into(textView);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
      });
   } */


   /**
    * inner class for view holder
    * @author Yağız Yaşar
    * @version 17.5.20
    */
   public class ViewHolder extends RecyclerView.ViewHolder {

      public ImageView profilePicture;
      public TextView username, text;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);

         profilePicture = itemView.findViewById(R.id.profilePictureElement);
         username = itemView.findViewById(R.id.notificationUsername);
         text = itemView.findViewById(notificationText);
      }
   }
}
