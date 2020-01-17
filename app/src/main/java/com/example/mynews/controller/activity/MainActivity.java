package com.example.mynews.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mynews.R;
import com.example.mynews.controller.adapteur.PageAdapter;
import com.example.mynews.controller.fragment.ApiFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import static com.example.mynews.controller.fragment.SearchFragment.SEARCH_PARAM;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ApiFragment.NumberOfResultsListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    // private static final String CHANNEL = "NotificationChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.main_activity_viewpager);
        mTabLayout = findViewById(R.id.main_activity_tablayout);
        mToolbar = findViewById(R.id.main_activity_toolbar);
        mDrawerLayout = findViewById(R.id.main_activity_drawer_layout);
        mNavigationView = findViewById(R.id.main_activity_navigation_drawer);

        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), getResources()));
        setTabLayout();
        setActionBar();
        mNavigationView.setNavigationItemSelectedListener(this);

        resetSearchPreferences();
    }

    private void resetSearchPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCH_PARAM, MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.clear().apply();
    }

    private void setActionBar() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_search_item:
                Log.d("TAG", "onOptionsItemSelected: ");
                startNewActivity(true, false);
                return true;
            case R.id.menu_main_notification_item:
                startNewActivity(true, true);
                return true;
            case R.id.menu_main_help_item:
                startNewActivity(false, true);
                return true;
            case R.id.menu_main_about_item:
                startNewActivity(false, false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_nd_search_item:
                startNewActivity(true, false);
                break;
            case R.id.menu_nd_notification_item:
                startNewActivity(true, true);
                break;
            case R.id.menu_nd_topic1_item:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.menu_nd_topic2_item:
                mViewPager.setCurrentItem(3, true);
                break;
            case R.id.menu_nd_topic3_item:
                mViewPager.setCurrentItem(4, true);
                break;
            case R.id.menu_nd_topic4_item:
                mViewPager.setCurrentItem(5, true);
                break;
            case R.id.menu_nd_topic5_item:
                mViewPager.setCurrentItem(6, true);
                break;
            case R.id.menu_nd_topic6_item:
                mViewPager.setCurrentItem(7, true);
                break;
            case R.id.menu_nd_help_item:
                startNewActivity(false, true);
                break;
            case R.id.menu_nd_about_item:
                startNewActivity(false, false);
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startNewActivity(boolean searchActivity, boolean activity) {
        Intent intent;
        Log.d("TAG", "startNewActivity: AVANTIF");
        if (searchActivity) {
            intent = new Intent(this, SearchActivity.class);
            Log.d("TAG", "startNewActivity: ifT serch ");
        } else {
            intent = new Intent(this, InformationActivity.class);
            Log.d("TAG", "startNewActivity: ifF info ");
        }
        intent.putExtra("ACTIVITY", activity);
        Log.d("TAG", "startNewActivity: putExtra ok ");
        startActivity(intent);
        Log.d("TAG", "startNewActivity: start ok");
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    @Override
    public void onNumberOfResultsChange(int numberOfResults) {

    }
}


