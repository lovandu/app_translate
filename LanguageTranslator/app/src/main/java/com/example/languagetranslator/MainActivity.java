package com.example.languagetranslator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.languagetranslator.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

//    private TextView register

    private BottomNavigationView navigationView;
    private ViewPager viewPager;

//    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        register=(TextView) findViewById(R.id.register);
//        register.setOnClickListener(this);
        setContentView(R.layout.activity_main);

//        drawerLayout=findViewById(R.id.)

        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mHistory).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mHistory).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mHistory:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mGame:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }
}
