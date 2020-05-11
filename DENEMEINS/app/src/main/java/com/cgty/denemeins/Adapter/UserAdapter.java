package com.cgty.denemeins.Adapter;

import android.content.Context;
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
import com.cgty.denemeins.ProfileFragment;
import com.cgty.denemeins.model.User;
import com.cgty.denemeins.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    //properties
    private Context mContext;
    private List<User> mUsers;
    private FirebaseUser firebaseUser;

    //constructors
    public UserAdapter(Context mContext, List<User> mUsers)
    {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    //methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.user_element, parent, false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = mUsers.get(position);

        holder.addFriend.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        holder.nameSurname.setText(user.getNameSurname());
        Glide.with(mContext).load(user.getPpURL()).into(holder.pp);
        follows(user.getId(), holder.addFriend);

        if (user.getId().equals(firebaseUser.getUid()))
            holder.addFriend.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor;
                editor = mContext.getSharedPreferences( "PREFS" , Context.MODE_PRIVATE).edit();

                editor.putString("profileid", user.getId());
                editor.apply();

                ( (FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            }
        });

        holder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (holder.addFriend.getText("   Follow   "))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following").child(user.getId()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId()).child("followers").child(firebaseUser.getUid()).setValue(true);
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following").child(user.getId()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId()).child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView pp;
        public TextView username;
        public TextView nameSurname;
        public Button addFriend;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            pp = itemView.findViewById(R.id.profilePictureElement);
            username = itemView.findViewById(R.id.textViewUsernameElement);
            nameSurname = itemView.findViewById(R.id.textViewNameSurnameElement);
            addFriend = itemView.findViewById(R.id.buttonAddFriendElement);
        }
    }

    private void follows(final String userID, final Button button)
    {
        DatabaseReference followPath;
        followPath = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following");

        followPath.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(userID).exists())
                    button.setText("   Followed   ");                  //3 bosluk koymayinca cirkin gozukuyor button (cagatay)
                else
                    button.setText("   Follow   ");                    //3 bosluk koymayinca cirkin gozukuyor button (cagatay) (bunu degistirmeyin!!!!!!!!)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}