package ex.com.bakingapp.data.api;

import java.util.List;

import ex.com.bakingapp.data.db.RecipeEntity;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QueryApi {
    @GET("http://go.udacity.com/android-baking-app-json")
    Call<List<RecipeEntity>> getData();
}