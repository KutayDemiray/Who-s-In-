package com.cgty.denemeins.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.MainActivity;
import com.cgty.denemeins.fragment.ProfileFragment;
import com.cgty.denemeins.model.User;
import com.cgty.denemeins.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

/**
 * User Adapter class. Used in User Search in Nearby Fragment.
 * @author Cagatay Safak
 * @version 1.0
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    //properties
    private Context mContext;
    private List<User> mUsers;
    private FirebaseUser firebaseUser;
    private boolean isFragment;

    //constructors
    public UserAdapter( Context mContext, List<User> mUsers, boolean isFragment ) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.isFragment = isFragment;
    }

    //methods
    /**
     * Creates the user_element. Locates the user_element into NearbyFragment's RecyclerView and ProfileFragment's Follower and Following Lists.
     *
     * @param parent Group of view.
     * @param viewType Type of view.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view;
        view = LayoutInflater.from( mContext ).inflate( R.layout.user_element, parent, false );

        return new UserAdapter.ViewHolder( view );
    }
    
    /**
     * Creates the user_element. Locates the user_element into NearbyFragment's RecyclerView and ProfileFragment's Follower and Following Lists.
     *
     * @param holder The ViewHolder instance in which we implement the changes of user_element.
     * @param position Index.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position ) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = mUsers.get( position );

        holder.addFriend.setVisibility( View.VISIBLE );
        holder.username.setText( user.getUsername() );
        holder.age.setText( user.getAge() );
        Glide.with( mContext ).load( user.getPicurl() ).into( holder.pp );
        follows( user.getId(), holder.addFriend );

        if ( user.getId().equals( firebaseUser.getUid() ) )
            holder.addFriend.setVisibility( View.GONE );

        holder.itemView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                if ( isFragment ) {
                    SharedPreferences.Editor editor;
                    editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();

                    editor.putString("profileid", user.getId());
                    editor.apply();
    
                    ( (FragmentActivity) mContext ).getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, new ProfileFragment() ).commit();   //fragment to fragment (cagatay)
                } else {
                    Intent intent;
                    intent = new Intent( mContext, MainActivity.class );
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("publisherId", user.getId() );
                    mContext.startActivity( intent );
                }
            }
        });

        holder.addFriend.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ( holder.addFriend.getText().toString().equals( "   Follow   " ) ) {
                    FirebaseDatabase.getInstance().getReference().child( "Follow" ).child( firebaseUser.getUid() ).child( "following" ).child( user.getId() ).setValue( true );
                    FirebaseDatabase.getInstance().getReference().child( "Follow" ).child( user.getId() ).child( "followers" ).child( firebaseUser.getUid() ).setValue( true );
                    addNotifications( user.getId(), position );
                } else {
                    FirebaseDatabase.getInstance().getReference().child( "Follow" ).child( firebaseUser.getUid() ).child( "following" ).child( user.getId() ).removeValue();
                    FirebaseDatabase.getInstance().getReference().child( "Follow" ).child( user.getId() ).child( "followers" ).child( firebaseUser.getUid() ).removeValue();
                }
            }
        });
    }
    
    /**
     * gets the mUsers size.
     *
     * @return number of elements in List mUsers.
     */
    @Override
    public int getItemCount() {
        return mUsers.size();
    }
    
    /**
     * inner class
     * @author Cagatay Safak
     * @version 12 MAY 20
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView pp;
        public TextView username;
        public TextView age;
        public Button addFriend;

        public ViewHolder( @NonNull View itemView ) {
            super(itemView);

            pp = itemView.findViewById( R.id.profilePictureElement );
            username = itemView.findViewById( R.id.textViewUsernameElement );
            age = itemView.findViewById( R.id.textViewAgeElement );
            addFriend = itemView.findViewById( R.id.buttonAddFriendElement );
        }
    }

    /**
     * Checks if the current user follows the other user with parameter userID.
     * Makes some changes in design and database.
     *
     * @param userID for a final String which declares ID of the user
     * @param button for a final Button which declares the Follow / Unfollow button.
     */
    private void follows( final String userID, final Button button ) {
        DatabaseReference followPath;
        followPath = FirebaseDatabase.getInstance().getReference().child( "Follow" ).child( firebaseUser.getUid() ).child( "following" );

        followPath.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                if (dataSnapshot.child(userID).exists())
                    button.setText("   Followed   ");
                else
                    button.setText("   Follow   ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * Adding notification to follow button so that if someone is followed, he or she will be notified
     * that they are being started to follow, and setting the notification according to this
     * @author Yağız Yaşar
     * @param userId
     */
    private void addNotifications( String userId, int i) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications" ).child( userId );
        final User user = mUsers.get( i );

        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put( "userId", firebaseUser.getUid() );
        hashMap.put( "mentionedUserId", user.getId() );
        hashMap.put( "text", " started following you" );
        hashMap.put( "eventId", "" );
        hashMap.put( "isEvent", false );

        reference.push().setValue( hashMap );
    }
}