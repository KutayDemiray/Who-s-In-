/**
 * Main activity
 * @author Gökberk Keskinkılıç
 * @version 1.0
 */
package com.cgty.denemeins;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cgty.denemeins.fragment.HomeFragment;
import com.cgty.denemeins.fragment.NearbyFragment;
import com.cgty.denemeins.fragment.NotificationsFragment;
import com.cgty.denemeins.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Main Activity class which has Home, Nearby, Notifications and Profile Fragments.
 * @author Çağatay Safak
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private Button settingsButton;
    BottomNavigationView bottomNav;
    Fragment selectedFragment = null;
    //    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        bottomNav = findViewById( R.id.bottom_navigation );
        bottomNav.setOnNavigationItemSelectedListener( navListener );
        
        Bundle intent = getIntent().getExtras();
        
        if ( intent != null ) {
            String publisher;
            publisher = intent.getString("publisherId" );
            
            SharedPreferences.Editor editor;
            editor = getSharedPreferences("PREFS", MODE_PRIVATE ).edit();
            editor.putString("profileid", publisher );
            editor.apply();
            
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, new ProfileFragment() ).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, new HomeFragment() ).commit();
        }

//        bottomNavigationView = findViewById(R.id.bottomNav);
//        bottomNavigationView.setOnNavigationItemSelectedListener( bottomNavMethod);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected( @NonNull MenuItem menuItem ) {
            switch ( menuItem.getItemId() ) {
                case R.id.nav_home:
                    //call home fragment
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_nearby:
                    //call nearby fragment
                    selectedFragment = new NearbyFragment();
                    break;
                case R.id.nav_notifications:
                    //call notification fragment
                    selectedFragment = new NotificationsFragment();
                    break;
                case R.id.nav_profile:
                    SharedPreferences.Editor editor;
                    editor = getSharedPreferences( "PREFS", MODE_PRIVATE ).edit();
                    editor.putString( "profileid", FirebaseAuth.getInstance().getCurrentUser().getUid() );
                    editor.apply();
                    
                    //call profile fragment
                    selectedFragment = new ProfileFragment();
                    break;
            }

            if ( selectedFragment != null ) {  //trying to avoid NullPointer...
                getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, selectedFragment ).commit();
            }
            
            return true;
        }
    };
}