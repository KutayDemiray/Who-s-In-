package com.cgty.denemeins;

import android.os.Bundle;
import android.widget.ImageView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgty.denemeins.R;
import com.cgty.denemeins.adapter.UserAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity
{
	// properties
	ImageView imageViewClose;
	ImageView imageViewProfile;
	TextView textViewChangePhoto;
	TextView textViewChangeSave;
	MaterialEditText materialEditTextUsername;
	MaterialEditText materialEditTextAge;
	MaterialEditText materialEditTextBio;
	
	FirebaseUser currentUser;
	//private
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		//instead view.findViewById since this is an activity, not a fragment.
		imageViewClose = findViewById(R.id.imageViewCloseEditProfile);
		imageViewProfile = findViewById(R.id.ppEditProfile);
		textViewChangePhoto = findViewById(R.id.textViewChangePhotoEditProfile);
		textViewChangeSave = findViewById(R.id.textViewSaveEditProfile);
		materialEditTextUsername = findViewById(R.id.materialEditTextChangeUsernameEditProfile);
		materialEditTextAge = findViewById(R.id.materialEditTextChangeAgeEditProfile);
		materialEditTextBio = findViewById(R.id.materialEditTextChangeBioEditProfile);
	}
}
