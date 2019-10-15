package com.example.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mynews.controller.MainActivity;
import com.example.mynews.controller.RecyclerFragment;
import com.example.mynews.controller.WebActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollRight;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.mynews", appContext.getPackageName());
    }

    @Test
    public void checkCount() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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


