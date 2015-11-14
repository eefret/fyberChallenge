package com.kaissersoft.app.fyber.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import com.kaissersoft.app.fyber.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by eefret on 14/11/15.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity_> mActivityRule = new ActivityTestRule<>(MainActivity_.class);



    @Test
    public void validateFieldsTest() {
        onView(withId(R.id.tv_uid))
                .perform(typeText("spiderman"));
        onView(withId(R.id.tv_app_id))
                .perform(typeText(""));
        onView(withId(R.id.tv_api_key))
                .perform(typeText("1c915e3b5d42d05136185030892fbb846c278927"));
        onView(withId(R.id.tv_pub0))
                .perform(typeText("pub0Sample"));

        assertFalse("validation succedded when supposed to fail", mActivityRule.getActivity().validateFields());
        onView(withText(R.string.ok))
                .perform(click());
        assertTrue(mActivityRule.getActivity().tvAppId.hasFocus());
        mActivityRule.getActivity().cleanParams();

        onView(withId(R.id.tv_uid))
                .perform(typeText("spiderman"));
        onView(withId(R.id.tv_app_id))
                .perform(typeText("2070"));
        onView(withId(R.id.tv_api_key))
                .perform(typeText("1c915e3b5d42d05136185030892fbb846c278927"));
        onView(withId(R.id.tv_pub0))
                .perform(typeText("pub0Sample"));

        assertTrue("validation succedded when supposed to fail", mActivityRule.getActivity().validateFields());
        mActivityRule.getActivity().cleanParams();
    }

    @Test
    public void completeRunTest(){
        //Filling fields
        onView(withId(R.id.tv_uid))
                .perform(typeText("spiderman"));
        onView(withId(R.id.tv_app_id))
                .perform(typeText("2070"));
        onView(withId(R.id.tv_api_key))
                .perform(typeText("1c915e3b5d42d05136185030892fbb846c278927"));
        onView(withId(R.id.tv_pub0))
                .perform(typeText("pub0Sample"));

        //passing to next activity
        onView(withId(R.id.action_send))
                .perform(click());

    }
}
