package ex.com.bakingapp.data.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import ex.com.bakingapp.data.db.Recipe;

public interface QueryApi {
    @GET("http://go.udacity.com/android-baking-app-json")
    Call<RecipesList> loadRecipesList();
}

