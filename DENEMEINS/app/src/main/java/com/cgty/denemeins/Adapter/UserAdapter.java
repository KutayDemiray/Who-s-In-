package com.cgty.denemeins.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.Model.User;
import com.cgty.denemeins.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private Context mContext;
    private List<User> mUsers;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<User> mUsers)
    {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.user_element, parent, false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = mUsers.get(position);

        holder.addFriend.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        holder.nameSurname.setText(user.getNameSurname());
        Glide.with(mContext).load(user.getPpURL()).into(holder.pp);
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
}