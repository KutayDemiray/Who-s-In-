package com.cgty.denemeins.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgty.denemeins.R;
import com.cgty.denemeins.adapter.NotificationAdapter;
import com.cgty.denemeins.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NotificationsFragment class for the view of the fragment
 * @author Yağız Yaşar
 * @version 15.5.20
 */
public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    public NotificationsFragment() {
       // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.fragment_notifications, container, false );

        recyclerView = view.findViewById( R.id.notificationsRecyclerView );
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext() );
        recyclerView.setLayoutManager( linearLayoutManager );

        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter( getContext(), notificationList );
        recyclerView.setAdapter( notificationAdapter );

        readNotifications(); //retracting notifications from database

        return view;
    }

    /**
     * Reading the notifications and retracting them from the database so that they can be
     * seen in the notifications fragment for the current user
     */
    private void readNotifications() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications" ).child( firebaseUser.getUid() );

        reference.addValueEventListener(new ValueEventListener() {

            /**
             * Reading every notification of the current user with DataSnapshot
             * @param dataSnapshot
             */
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {

                notificationList.clear(); //clearing the whole notifications

                for ( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                    Notification notification = snapshot.getValue( Notification.class );
                    notificationList.add( notification);
                }
                Collections.reverse( notificationList ); //reversing the notifications so that the newest will appear at top
                notificationAdapter.notifyDataSetChanged(); //notifying the adapter when the data has changed
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        });
    }
}
