package com.example.dhruvkalia.newproject.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dhruvkalia.newproject.Fragment.FavouriteFragment;
import com.example.dhruvkalia.newproject.Fragment.HistoryFragment;
import com.example.dhruvkalia.newproject.Fragment.HomeFragment;
import com.example.dhruvkalia.newproject.Fragment.ProfileFragment;
import com.example.dhruvkalia.newproject.R;
import com.example.dhruvkalia.newproject.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener, FavouriteFragment.OnListFragmentInteractionListener, HistoryFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;

    private String TAG = "NFC";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentFragment = HomeFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,currentFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_favourite:
                    currentFragment = FavouriteFragment.newInstance(1);

                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.frame_layout,currentFragment);
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_history:
                    currentFragment = HistoryFragment.newInstance(1);

                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.frame_layout,currentFragment);
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_profile:
                    Log.e(TAG,"Selected profile");
                    currentFragment = ProfileFragment.newInstance();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.frame_layout,currentFragment);
                    fragmentTransaction3.commit();
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,HomeFragment.newInstance());
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
