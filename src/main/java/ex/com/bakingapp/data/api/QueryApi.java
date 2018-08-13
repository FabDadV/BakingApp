package ex.com.bakingapp.data.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QueryApi {
    @GET("http://go.udacity.com/android-baking-app-json")
    Call<ArrayList<RecipeApi>> getData();
}