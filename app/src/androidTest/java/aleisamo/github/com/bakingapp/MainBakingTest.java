package aleisamo.github.com.bakingapp;


import android.os.SystemClock;
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
public class MainBakingTest {

    private static final String TITLE = "Recipe Ingredients";

    @Rule
    public ActivityTestRule<MainBaking> mActivityTestRule = new ActivityTestRule<>(MainBaking.class);

    @Test
    public void clickRecycleViewItem_OpenRecipeDetails() {
        SystemClock.sleep(3000);

        onView(allOf(withId(R.id.recipe_recycleView), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));

        //on view check Title
        SystemClock.sleep(3000);
        onView(allOf(withId(R.id.recipes_ingredients), isDisplayed()))
                .check(matches(withText(TITLE)));
    }

}
