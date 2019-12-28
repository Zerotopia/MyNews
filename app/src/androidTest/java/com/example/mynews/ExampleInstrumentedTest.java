package com.example.mynews;

import android.content.Context;
//import android.content.Intent;

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

import com.example.mynews.controller.ApiFragment;
import com.example.mynews.controller.MainActivity;
import com.example.mynews.controller.WebActivity;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.BeforeClass;
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
/*
    @BeforeClass
    public void setUp() {
        IdlingRegistry.getInstance().register(ApiFragment.getCount());
    }

/*
    @Test
    public void basicTestOnRecyclerVew() {
        RecyclerView recyclerView = actualRecyclerView();
        int lastItem = recyclerView.getAdapter().getItemCount() - 1;

        Espresso.onView(ViewMatchers.withId(R.id.fragment_api_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(lastItem));

        Intents.init();
        Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.fragment_api_recyclerview), ViewMatchers.isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(lastItem, ViewActions.click()));
        Intents.intended(IntentMatchers.hasComponent(WebActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void navigationTestOnViewPager() {
        Espresso.onView(ViewMatchers.withText("TOP STORIES"))
                .perform(ViewPagerActions.scrollRight())
                .check(ViewAssertions.matches(
                        ViewMatchers.withText(
                                Matchers.containsString("MOST POPULAR"))));
        Espresso.onView(ViewMatchers.withText("TECHNOLOGY"))
                .perform(ViewActions.click())
                .perform(ViewPagerActions.scrollLeft())
                .check(ViewAssertions.matches(
                        ViewMatchers.withText(
                                Matchers.containsString("SCIENCE"))));
    }
/*
    @Test
    public void searchActivityTest() {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_search_item))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.search_activity_edit_text))
                .perform(ViewActions.click())
                .perform(ViewActions.typeTextIntoFocusedView("chocolat"));
        Espresso.onView(ViewMatchers.withId(R.id.search_activity_begin_date))
                .perform(ViewActions.click())
                .perform(PickerActions.setDate(1980,8,14));
        Espresso.onView(ViewMatchers.withId(R.id.search_activity_end_date))
                .perform(ViewActions.click())
                .perform(PickerActions.setDate(2010,3,24));
        Espresso.onView(ViewMatchers.withId(R.id.search_activity_food_checkbox))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.search_activity_health_checkbox))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.search_activity_button))
                .perform(ViewActions.click());

        RecyclerView recyclerView = actualRecyclerView();
        int sizeRecycler = recyclerView.getAdapter().getItemCount();
        assertEquals(10,sizeRecycler);
    }
*/
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.mynews", appContext.getPackageName());
    }

    private RecyclerView actualRecyclerView() {
        return activityTestRule.getActivity().findViewById(R.id.fragment_api_recyclerview);
    }

}
