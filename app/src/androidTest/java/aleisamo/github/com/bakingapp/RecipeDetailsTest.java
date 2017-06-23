package aleisamo.github.com.bakingapp;


import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsTest {
    private static final String description = "Recipe Introduction";


    @Rule
    public ActivityTestRule<RecipesDetail>mActivityTestRule = new ActivityTestRule<>(RecipesDetail.class);

    @Test
    public void  clickrRecycleviewItem_Pass_viddeo_description(){
        SystemClock.sleep(3000);

        ViewInteraction recycleViewSteps = onView(
                allOf(withId(R.id.recipe_details_step_recycleView),
                        withParent(allOf(withId(android.R.id.content))),
                isDisplayed()));
        recycleViewSteps.perform(actionOnItemAtPosition(0,click()));
        SystemClock.sleep(3000);

        intended(allOf(
                hasAction(Intent.ACTION_SENDTO),
                hasExtra(Intent.EXTRA_TEXT, description)));
    }
}
