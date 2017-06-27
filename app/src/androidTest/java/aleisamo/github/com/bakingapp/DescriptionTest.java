package aleisamo.github.com.bakingapp;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DescriptionTest {

    @Rule
    public ActivityTestRule<MainBaking> mActivityTestRule = new ActivityTestRule<>(MainBaking.class);

    @Test
    public void test_basic_path_until_show_video_and_description() {
        SystemClock.sleep(3000);

        onView(allOf(withId(R.id.recipe_recycleView),
                isDisplayed())).perform(actionOnItemAtPosition(0, click()));

        SystemClock.sleep(3000);

        onView(withId(R.id.recipe_details_step_recycleView)).
                perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4957);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.text_description), withText("Recipe Introduction"),
                isDisplayed())).check(matches(withText("Recipe Introduction")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.next), withText("Next"),
                        isDisplayed()));
        appCompatButton.perform(click());
        SystemClock.sleep(2000);


    }

}
