package com.example.languagetranslator.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.languagetranslator.fragment.GameFragment;
import com.example.languagetranslator.fragment.HistoryFragment;
import com.example.languagetranslator.fragment.HomeFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new HomeFragment();
            case 1:return new HistoryFragment();
            case 2:return new GameFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
