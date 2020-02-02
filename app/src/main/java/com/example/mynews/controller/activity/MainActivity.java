package com.example.mynews.controller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.mynews.controller.fragment.AlertDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import static com.example.mynews.controller.fragment.SearchFragment.SEARCH_PARAM;

/**
 * In the main activity we defined :
 * 1- The ViewPager that propose different lists of articles of the NewYork Times.
 * 2- A Toolbar that allows to launch some other activity like the research of articles...
 * 3- A Navigation Drawer that allows to navigate more easily in the application.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        AlertDialogFragment.AlertDialogClickEvent {

    public static final String ACTIVITY = "ACTIVITY";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();

        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), getResources()));
        setTabLayout();
        setActionBar();
        mNavigationView.setNavigationItemSelectedListener(this);

        resetSearchPreferences();
    }

    /**
     * Clear the Shared Preferences of search parameters to have a
     * clean search interface.
     */
    private void resetSearchPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCH_PARAM, MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.clear().apply();
    }

    /**
     * Define the action bar of the main activity.
     */
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

    /**
     * Define Tabs of the ViewPager.
     */
    private void setTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    /**
     * perform all findViewById
     */
    private void bindView() {
        mViewPager = findViewById(R.id.main_activity_viewpager);
        mTabLayout = findViewById(R.id.main_activity_tablayout);
        mToolbar = findViewById(R.id.main_activity_toolbar);
        mDrawerLayout = findViewById(R.id.main_activity_drawer_layout);
        mNavigationView = findViewById(R.id.main_activity_navigation_drawer);
    }

    /**
     * Create the options Menu of the main activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    /**
     * Define actions to perform when we clicked on an item of the menu.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_search_item:
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

    /**
     * Define actions to perform when we clicked on an item of
     * the Navigation Drawer's menu.
     */
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

    /**
     * This function launch an activity that depend of the boolean passed in parameters.
     *
     * @param searchActivity if this parameters is true then we start the SearchActivity else
     *                       we start the InformationActivity.
     * @param activity       This boolean is passed to the activity via an intent.
     *                       For each activity, this boolean will be determined which "subactivity" launch.
     *                       (Search article or notification for SearchActivity and
     *                       About or Help for InformationActivity.)
     */
    public void startNewActivity(boolean searchActivity, boolean activity) {
        Intent intent;
        if (searchActivity) intent = new Intent(this, SearchActivity.class);
        else intent = new Intent(this, InformationActivity.class);

        intent.putExtra(ACTIVITY, activity);
        startActivity(intent);
    }

    /**
     * This defined the comportment of the Back button if the
     * Navigation Drawer is open.
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }


    @Override
    public void doPositiveClick() {

    }

    @Override
    public void doNegativeClick() {

    }
}


