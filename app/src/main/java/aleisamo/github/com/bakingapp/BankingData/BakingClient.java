package aleisamo.github.com.bakingapp.BankingData;

import java.util.List;

import aleisamo.github.com.bakingapp.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingClient {

    String url = "android-baking-app-json";

    @GET(url)
    Call<List<Recipe>> recipes();

}
