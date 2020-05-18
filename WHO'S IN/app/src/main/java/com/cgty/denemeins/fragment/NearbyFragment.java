package com.cgty.denemeins.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgty.denemeins.R;
import com.cgty.denemeins.adapter.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gökberk Keskinkılıç, Cagatay Safak
 */
public class NearbyFragment extends Fragment
{

    //properties
    private RecyclerView recyclerView;
    private List<com.cgty.denemeins.model.User> mUsers;
    private UserAdapter userAdapter;
    EditText searchBar;

    public NearbyFragment()
    {
        //required empty public constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);

        recyclerView = view.findViewById(R.id.nearbyRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        userAdapter = new UserAdapter( getContext(), mUsers);
        searchBar = view.findViewById(R.id.nearbySearchEditText);

        recyclerView.setAdapter(userAdapter);

        readUsers();

        searchBar.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                searchUser(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        return view;
    }

    private void searchUser(String str)
    {
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("username").startAt(str).endAt(str+"\uf8ff");

        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mUsers.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    com.cgty.denemeins.model.User user = snapshot.getValue(com.cgty.denemeins.model.User.class);
                    mUsers.add(user);
                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void readUsers()
    {
        DatabaseReference userPath = FirebaseDatabase.getInstance().getReference("Users");

        userPath.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (searchBar.getText().toString().equals(""))
                {
                    mUsers.clear();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        com.cgty.denemeins.model.User user = snapshot.getValue(com.cgty.denemeins.model.User.class);
                        mUsers.add(user);

                        userAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}