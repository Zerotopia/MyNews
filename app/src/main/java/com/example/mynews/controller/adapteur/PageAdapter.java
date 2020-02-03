package com.example.mynews.controller.adapteur;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mynews.R;
import com.example.mynews.controller.fragment.ApiFragment;

/**
 * Page adapter for the View Pager.
 */
public class PageAdapter extends FragmentPagerAdapter {

    private Resources mResources;

    public PageAdapter(FragmentManager fm, Resources resources) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mResources = resources;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        return ApiFragment.newInstance(position, new String[4]);
    }

    @Override
    public int getCount() {
        return mResources.getStringArray(R.array.subjects).length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] title = mResources.getStringArray(R.array.subjects);
        return title[position].toUpperCase();
    }
}
