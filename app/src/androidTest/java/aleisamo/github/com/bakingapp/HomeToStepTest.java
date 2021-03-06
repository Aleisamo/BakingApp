package aleisamo.github.com.bakingapp;


import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeToStepTest {

    @Rule
    public ActivityTestRule<MainBaking> mActivityTestRule = new ActivityTestRule<>(MainBaking.class);

    @Test
    public void homeToStepFlow() {

        SystemClock.sleep(3000);

        onView(allOf(withId(R.id.recipe_recycleView),
                        isDisplayed())).perform(actionOnItemAtPosition(0,click()));

        SystemClock.sleep(3000);

        onView(allOf(withId(R.id.recipe_details_step_recycleView),isDisplayed()))
                .perform(actionOnItemAtPosition(0,click()));
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4948);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.text_description), withText("Recipe Introduction"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Recipe Introduction")));
        Context targetContext = InstrumentationRegistry.getTargetContext();
        boolean isTwoPane = targetContext.getResources().getBoolean(R.bool.isTwoPane);

        if (!isTwoPane) {
            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.next), withText("Next"),
                            withParent(allOf(withId(R.id.description),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            appCompatButton.perform(click());

            SystemClock.sleep(3000);

            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.text_description), withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan."),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            1),
                                    1),
                            isDisplayed()));
            textView2.check(matches(withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.")));

            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.back), withText("Back"),
                            withParent(allOf(withId(R.id.description),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            appCompatButton2.perform(click());

            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pressBack();

            ViewInteraction recyclerView3 = onView(
                    withId(R.id.recipe_details_step_recycleView));
            recyclerView3.perform(actionOnItemAtPosition(6, click()));

            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
