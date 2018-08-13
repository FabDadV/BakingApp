package ex.com.bakingapp.data.api;

import android.util.Log;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataResponse {
    private static final String BASE_URL = "http://go.udacity.com/";
    private static boolean f = false;

    static Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    public static ArrayList<RecipeApi> getInfo() {
        ArrayList<RecipeApi> recipesApi = new ArrayList<>();
        Log.d("TAG", " getInfo");

        retrofit.create(QueryApi.class).getData().enqueue(new Callback<ArrayList<RecipeApi>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeApi>> call, Response<ArrayList<RecipeApi>> response) {
                if (response.isSuccessful()) {
                    f = response.isSuccessful();
                    Log.d("TAG", " R: " + String.valueOf(f) + response.body().get(1).getName());
                    recipesApi.addAll(response.body());
                } else {
                    Log.e("TAG", "response code " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RecipeApi>> call, Throwable t) {
                //Проверка на ошибку
                Log.d("TAG", "Error" + t.toString());
            }
        });
//        addDelay();
//        if(!f) Log.d("TAG", " R: " + " return 0");
        return recipesApi;
    }

    private static void addDelay() {
        try {
            Log.d("TAG", " Delay getInfo");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
