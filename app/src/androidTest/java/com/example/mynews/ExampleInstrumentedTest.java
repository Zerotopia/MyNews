package com.example.mynews;

import android.content.Context;
import android.widget.DatePicker;
//import android.content.Intent;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.contrib.ViewPagerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mynews.controller.Fragment.ApiFragment;
import com.example.mynews.controller.Activity.MainActivity;
import com.example.mynews.controller.Activity.WebActivity;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(ApiFragment.getCount());
    }


    @Test
    public void basicTestOnRecyclerVew() {
        Intents.init();
        Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.fragment_api_recyclerview),
                ViewMatchers.withParent(ViewMatchers.withId(R.id.main_activity_viewpager)),
                ViewMatchers.isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(8))
                .perform(RecyclerViewActions.actionOnItemAtPosition(8, ViewActions.click()));
        Intents.intended(IntentMatchers.hasComponent(WebActivity.class.getName()));
        Intents.release();
    }


    @Test
    public void navigationTestOnViewPager() {
        Espresso.onView(ViewMatchers.withId(R.id.main_activity_viewpager))
                .perform(ViewPagerActions.scrollRight());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(AllOf.allOf(ViewMatchers.isSelected(),
                ViewMatchers.withClassName(Matchers.equalTo(AppCompatTextView.class.getName()))))
                .check(ViewAssertions.matches(ViewMatchers.withText("MOST POPULAR")));
        Espresso.onView(ViewMatchers.withId(R.id.main_activity_viewpager))
                .perform(ViewPagerActions.scrollRight());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.main_activity_viewpager))
                .perform(ViewPagerActions.scrollRight());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.main_activity_viewpager))
                .perform(ViewPagerActions.scrollRight());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withText("FOOD"))
                .perform(ViewActions.click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.main_activity_viewpager))
                .perform(ViewPagerActions.scrollLeft());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(AllOf.allOf(ViewMatchers.isSelected(),
                ViewMatchers.withClassName(Matchers.equalTo(AppCompatTextView.class.getName()))))
                .check(ViewAssertions.matches(ViewMatchers.withText("TRAVEL")));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchActivityTest() {
        Espresso.onView(ViewMatchers.withId(R.id.menu_main_search_item))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search_editText))
                .perform(ViewActions.click())
                .perform(ViewActions.typeTextIntoFocusedView("Wine"))
                .perform(ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search_begin_date))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(1980, 8, 14));
        Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search_end_date))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2010, 3, 24));
        Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search_topic5))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search_topic6))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_search_button))
                .perform(ViewActions.click());
        Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.fragment_api_recyclerview), ViewMatchers.isDisplayed()))
                .check(new RecyclerViewTestSize.ItemCount(10));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.mynews", appContext.getPackageName());
    }

}
