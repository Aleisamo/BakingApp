package aleisamo.github.com.bakingapp;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Slide {
    public static void slide_down(Context context, View view){
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.slide_down);
        if(animation != null){
            animation.reset();
            if(view!= null){
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }

    }

    public static void slide_up(Context context, View view){
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.slide_up);
        if(animation != null){
            animation.reset();
            if(view!= null){
                view.clearAnimation();
                view.startAnimation(animation);
            }
        }

    }

}
