package com.cgty.denemeins;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.cgty.denemeins.fragment.ProfileFragment;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity
{
	// properties
	ImageView imageViewClose;
	ImageView imageViewProfile;
	TextView textViewChangeSave;
	TextView textViewChangePhoto;
	MaterialEditText materialEditTextUsername;
	MaterialEditText materialEditTextAge;
	MaterialEditText materialEditTextBio;
    private static final int IMAGE_REQUEST = 1;
	
	//firebase
	FirebaseUser currentUser;
	private StorageTask uploadTask;  //try public
	// private Uri mImageUri;
	// StorageReference storagePath;
	private Uri imageUri;
	StorageReference storageReference;
	DatabaseReference reference;
	
	// methods
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		/** instead view.findViewById since this is an activity, not a fragment.*/
		
		//ImageViews
		imageViewClose = findViewById(R.id.imageViewCloseEditProfile);
		imageViewProfile = findViewById(R.id.ppEditProfile);
		
		//TextViews
		textViewChangePhoto = findViewById(R.id.textViewChangePhotoEditProfile);
		textViewChangeSave = findViewById(R.id.textViewSaveEditProfile);
		
		//MaterialEditTexts
		materialEditTextUsername = findViewById(R.id.materialEditTextChangeUsernameEditProfile);
		materialEditTextAge = findViewById(R.id.materialEditTextChangeAgeEditProfile);
		materialEditTextBio = findViewById(R.id.materialEditTextChangeBioEditProfile);
		
		//Firebase
		currentUser = FirebaseAuth.getInstance().getCurrentUser();
		// storagePath = FirebaseStorage.getInstance().getReference("uploads");
		
		DatabaseReference userPath;
		userPath = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        reference = FirebaseDatabase.getInstance().getReference( "Users").child(currentUser.getUid());
		userPath.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot)
			{
				User user;
				user = dataSnapshot.getValue( User.class);
				
				materialEditTextAge.setText(user.getAge());
				materialEditTextUsername.setText(user.getUsername());
				materialEditTextBio.setText(user.getBio());
				
				Glide.with(getApplicationContext()).load(user.getPicurl()).into(imageViewProfile);/////////////////////////////////////////////////////////////////////////////////
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


		
		//save
		textViewChangeSave.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				updateProfile( materialEditTextUsername.getText().toString(), materialEditTextAge.getText().toString(), materialEditTextBio.getText().toString());   // un, age, bio
				
				//go to edit profile screen
				
				Toast.makeText(EditProfileActivity.this, "PARABÉNS! CONGRATS! TEBRIKLER! \nUPDATED SUCCESSFULLY :) ", Toast.LENGTH_SHORT ).show();
				finish();
				
				/**
				 * It also works but it is too slow.
				 *
				 * Fragment fragment = new ProfileFragment();
				 * FragmentManager fragmentManager = getSupportFragmentManager();
				 * fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
				 */
			}
		});

        textViewChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                materialEditTextUsername.setText(user.getUsername());
                if( user.getPicurl().equals("https://firebasestorage.googleapis.com/v0/b/deneme-ins.appspot.com/o/femalePP.jpg?alt=media&token=caf1f449-bba5-430f-a738-843873166082"))
                    imageViewProfile.setImageResource(R.mipmap.ic_launcher);
                else
                if ( EditProfileActivity.this != null )
                    Glide.with( EditProfileActivity.this).load(user.getPicurl()).into(imageViewProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
		
		updatePath.updateChildren( updateUserHashMap);
	}



    /**
     * for profile image upload
     * @author Gökberk
     */
    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog( EditProfileActivity.this);
        pd.setMessage("Uploading...");
        pd.show();

        if ( imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "-" +getFileExtension( imageUri));

            uploadTask = fileReference.getFile( imageUri);
            uploadTask.continueWith(new Continuation <UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if( !task.isSuccessful())
                        throw task.getException();
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if( task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("picurl", mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    }
                    else{
                        Toast.makeText( EditProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else{
            Toast.makeText( EditProfileActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * for profile picture uplaod
     * @param requestCode
     * @param resultCode
     * @param data
     * @author Gökberk
     */
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data){
        super.onActivityResult( requestCode, resultCode, data );

        if( requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();

            if ( uploadTask != null && uploadTask.isInProgress())
                Toast.makeText(EditProfileActivity.this, "Upload is in progress", Toast.LENGTH_SHORT).show();
            else{
                uploadImage();
            }
        }
    }

    /**
     * for profile image upload
     * @author Gökberk
     */
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult( intent, IMAGE_REQUEST);
    }

    /**
     * for profile image upload
     * @param uri
     * @return String
     * @author Gökberk
     */
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = EditProfileActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /*
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

		 */

    //through the 'Profile Picture' CircleImageView
        /*
		imageViewProfile.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//                   cropping ratio                              cropping shape                             context
				CropImage.activity().setAspectRatio(1,1).setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);
			}
		});

         */

        /*
	private String getFilenameExtension( Uri theUri)
	{
		ContentResolver contentResolver = getContentResolver();
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

		return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType( theUri));
	}

	 */

	/*
	private void uploadPhoto()
	{
		final ProgressDialog pdEditProfile;
		pdEditProfile= new ProgressDialog( EditProfileActivity.this );
		pdEditProfile.setMessage("Uploading...");
		pdEditProfile.show();

		if ( mImageUri != null)  //trying to avoid NullPointer...
		{
			final StorageReference filePath;
			filePath = storagePath.child( System.currentTimeMillis() + "-" + getFilenameExtension( mImageUri) );

			uploadTask = filePath.putFile( mImageUri);
			uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
			{
				@Override
				public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
				{
					if ( !task.isSuccessful() )
					{
						throw task.getException();
					}

					return filePath.getDownloadUrl();
				}
			}).addOnCompleteListener( new OnCompleteListener<Uri>()
			{
				@Override
				public void onComplete(@NonNull Task<Uri> task)
				{
					if (task.isSuccessful())
					{
						Uri downloadUri;
						downloadUri = task.getResult();

						String myUri;
						myUri = downloadUri.toString();

						DatabaseReference userPath;
						userPath = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

						HashMap<String,Object> imageHashMap;
						imageHashMap = new HashMap<>();

						imageHashMap.put("picurl", "" + myUri);

						userPath.updateChildren(imageHashMap);

						pdEditProfile.dismiss();
					}
					else
					{
						Toast.makeText(EditProfileActivity.this, "Upload was unsuccessful!", Toast.LENGTH_LONG).show();
						pdEditProfile.dismiss();
					}
				}
			}).addOnFailureListener(new OnFailureListener()
			{
				@Override
				public void onFailure(@NonNull Exception e)
				{
					Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
					pdEditProfile.dismiss();

				}
			});
		}
		else
		{
			Toast.makeText(this, "No image is selected!", Toast.LENGTH_SHORT).show();
		}
	}

	 */

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if ( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
		{
			CropImage.ActivityResult result = CropImage.getActivityResult(data);

			mImageUri = result.getUri();

			uploadPhoto();
		}
		else
		{
			Toast.makeText(EditProfileActivity.this, "weirdo error!!!", Toast.LENGTH_LONG).show();
		}
	}

	 */

}