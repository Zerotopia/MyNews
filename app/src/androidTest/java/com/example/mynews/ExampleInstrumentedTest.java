package com.example.mynews;

import android.content.Context;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.mynews.controller.MainActivity;
import com.example.mynews.controller.RecyclerFragment;
import com.example.mynews.controller.WebActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.ViewPagerActions.scrollRight;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
        IdlingRegistry.getInstance().register(RecyclerFragment.getCount());
    }
//        onView(withId(R.id.btn))
//               .perform(click());
//
//    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.mynews", appContext.getPackageName());
    }

    @Test
    public void checkCount() {

        onView(withId(R.id.viewpager)).perform(scrollRight()).perform(scrollRight());
        onView(allOf(withId(R.id.recyclerview), isDisplayed()))
                .check(new RecyclerViewUtils.ItemCount(10));
    }

    @Test
    public void checkNavigation() {
        onView(withText("TOP STORIES")).perform(click());
        onView(allOf(withId(R.id.recyclerview), isDisplayed()))
                .check(new RecyclerViewUtils.ItemCount(10));
        onView(withText("POPULAR")).perform(click());
        onView(allOf(withId(R.id.recyclerview), isDisplayed()))
                .check(new RecyclerViewUtils.ItemCount(10));
        onView(withText("SEARCH")).perform(click());
        onView(allOf(withId(R.id.recyclerview), isDisplayed()))
                .check(new RecyclerViewUtils.ItemCount(10));

    }

    @Test
    public void checkClick() {

        init();
        onView(allOf(withId(R.id.recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(WebActivity.class.getName()));
    }

}


