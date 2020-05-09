/**
package com.cgty.denemeins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
//    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        bottomNavigationView = findViewById(R.id.bottomNav);
//        bottomNavigationView.setOnNavigationItemSelectedListener( bottomNavMethod);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod= new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch( item.getItemId())
            {
                case R.id.navHome;



            }

            return false;
        }
    };

}

 */


package com.cgty.denemeins;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

import com.cgty.denemeins.HomeFragment;
import com.cgty.denemeins.NearbyFragment;
import com.cgty.denemeins.NotificationsFragment;
import com.cgty.denemeins.ProfileFragment;
import com.cgty.denemeins.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{

    private Button settingsButton;
    //    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener( navListener);

//        bottomNavigationView = findViewById(R.id.bottomNav);
//        bottomNavigationView.setOnNavigationItemSelectedListener( bottomNavMethod);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {
            Fragment selectedFragment = null;

            switch ( menuItem.getItemId())
            {
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
                    selectedFragment = new ProfileFragment();                                                          //part3 ekle (cagatay)
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };
}