package com.cgty.denemeins;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.R;
import com.cgty.denemeins.adapter.UserAdapter;
import com.cgty.denemeins.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
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
	private StorageTask uploadTask;
	private Uri mImageUri;
	StorageReference storagePath;
	
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
		
		currentUser = FirebaseAuth.getInstance().getCurrentUser();
		storagePath = FirebaseStorage.getInstance().getReference("uploads");
		
		DatabaseReference userPath;
		userPath = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
		userPath.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot)
			{
				User user = dataSnapshot.getValue( User.class);
				
				materialEditTextAge.setText(user.getAge());
				materialEditTextUsername.setText(user.getUsername());
				materialEditTextBio.setText(user.getBio());
				
				Glide.with(getApplicationContext()).load(user.getPicurl()).into(imageViewProfile);
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError)
			{
			
			}
		});
		
		//closing the activity
		imageViewClose.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		
		//open gallery
			//through the 'Change Photo' TextView
		textViewChangePhoto.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//                   cropping ratio                              cropping shape                             context
				CropImage.activity().setAspectRatio(1,1).setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);
			}
		});
		
			//through the 'Profile Picture' ImageView
		imageViewProfile.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//                   cropping ratio                              cropping shape                             context
				CropImage.activity().setAspectRatio(1,1).setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);
			}
		});
		
		//save
		textViewChangeSave.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{   //            un, age, bio
				updateProfile(materialEditTextUsername.getText().toString(), materialEditTextAge.getText().toString(), materialEditTextBio.getText().toString());
			}
		});
	}
	
	private void updateProfile(String theUsername, String theAge, String theBio)
	{
		DatabaseReference updatePath;
		updatePath = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
		
		//using Hashmap in order to send data from Studio to Firebase.
		HashMap<String,Object> updateUserHashMap;
		updateUserHashMap = new HashMap<>();
		
		updateUserHashMap.put( "username", theUsername);
		updateUserHashMap.put( "age", theAge);
		updateUserHashMap.put( "bio", theBio);
		
		updatePath.updateChildren(updateUserHashMap);
	}
	
	private String getFilenameExtension( Uri uri)
	{
		ContentResolver contentResolver = getContentResolver();
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
		return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType( uri));
	}
	
	private void uploadPhoto()
	{
		final ProgressDialog pd;
		pd = new ProgressDialog(this);
		
		pd.setMessage("Uploading...");
		pd.show();
	
		if ( mImageUri != null)  //trying to avoid NullPointer...
		{
			final StorageReference filePath;
			filePath = storagePath.child(System.currentTimeMillis() + "." + getFilenameExtension(mImageUri));
			
			uploadTask = filePath.putFile(mImageUri);
			uploadTask.continueWithTask( new Continuation()
			{
				@Override
				public Object then(@NonNull Task task) throws Exception
				{
					if (!task.isSuccessful())
					{
						throw task.getException();
					}
					
					return filePath.getDownloadUrl();
				}
			}).addOnCompleteListener(new OnCompleteListener<Uri>()
			{
				@Override
				public void onComplete(@NonNull Task<Uri> task)
				{
					if (task.isSuccessful())
					{
						//variables
						Uri uriDownload;
						String myUri;
						DatabaseReference userPath;
						HashMap<String,Object> imageHashMap;
						
						uriDownload = task.getResult();
						myUri = uriDownload.toString();
						
						userPath = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
						
						imageHashMap = new HashMap<>();
						imageHashMap.put("picurl", "" + myUri);
						
						userPath.updateChildren(imageHashMap);
						
						pd.dismiss();
					}
					else
					{
						Toast.makeText(EditProfileActivity.this, "Upload was unsuccessful!", Toast.LENGTH_LONG).show();
					}
				}
			}).addOnFailureListener(new OnFailureListener()
			{
				@Override
				public void onFailure(@NonNull Exception e)
				{
					Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
		}
		else
		{
			Toast.makeText(EditProfileActivity.this, "No image is selected!", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if ( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
		{
			CropImage.ActivityResult result;
			result = CropImage.getActivityResult(data);
			
			mImageUri = result.getUri();
			
			uploadPhoto();
		}
		else
		{
			Toast.makeText(EditProfileActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
		}
	}
}