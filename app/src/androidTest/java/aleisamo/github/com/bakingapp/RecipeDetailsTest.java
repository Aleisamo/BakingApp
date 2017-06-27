package aleisamo.github.com.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsTest {
    private static final String RECIPE_NAME = "Nutella Pie";


    @Rule
    public ActivityTestRule<MainBaking> mActivityTestRule = new ActivityTestRule<MainBaking>(MainBaking.class){
        @Override
        protected Intent getActivityIntent() {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            //CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.title);
            Intent intentToRecipes = new Intent(context,RecipesDetail.class);
            intentToRecipes.putExtra("recipe_name",RECIPE_NAME);
            return super.getActivityIntent();
        }
    };

    @Test
    public void toolbarTitle(){
        onView(allOf(withText(RECIPE_NAME),isDisplayed()));
    }
}
