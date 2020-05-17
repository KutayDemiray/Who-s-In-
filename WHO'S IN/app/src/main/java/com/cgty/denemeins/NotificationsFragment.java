package com.cgty.denemeins;

/**
 * @author Yağız Yaşar
 */
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgty.denemeins.Adapter.NotificationAdapter;
import com.cgty.denemeins.Model.Notification;
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
 */
public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    public NotificationsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.notificationsRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext());
        recyclerView.setLayoutManager( linearLayoutManager);

        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter( getContext(), notificationList);
        recyclerView.setAdapter( notificationAdapter);

        readNotifications();

        return view;
    }

    /**
     * Reading the values and getting the references of notifications from the realtime database
     */
    private void readNotifications() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child( firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            /**
             * Reading every notification with DataSnapshot
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                notificationList.clear();

                for ( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                    Notification notification = snapshot.getValue( Notification.class);
                    notificationList.add( notification);
                }
                Collections.reverse( notificationList);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
