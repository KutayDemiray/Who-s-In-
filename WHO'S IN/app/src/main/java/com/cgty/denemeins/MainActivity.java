/**
 * Main activity
 * @author Gökberk Keskinkılıç
 * @version 1.0
 */
package com.cgty.denemeins;

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

/**
 * Main Activity class which has Home, Nearby, Notifications and Profile Fragments.
 * @author Çağatay Safak
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private Button settingsButton;
    //    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        BottomNavigationView bottomNav = findViewById( R.id.bottom_navigation );
        bottomNav.setOnNavigationItemSelectedListener( navListener );
        getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, new HomeFragment() ).commit();

//        bottomNavigationView = findViewById(R.id.bottomNav);
//        bottomNavigationView.setOnNavigationItemSelectedListener( bottomNavMethod);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected( @NonNull MenuItem menuItem ) {
            Fragment selectedFragment = null;

            switch ( menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_nearby:
                    selectedFragment = new NearbyFragment();
                    break;
                case R.id.nav_notifications:
                    selectedFragment = new NotificationsFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, selectedFragment ).commit();

            return true;
        }
    };
}