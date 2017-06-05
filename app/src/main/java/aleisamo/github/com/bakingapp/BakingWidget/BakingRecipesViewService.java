package aleisamo.github.com.bakingapp.BakingWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingRecipesViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingRecipesList(this.getApplicationContext());
    }


}



