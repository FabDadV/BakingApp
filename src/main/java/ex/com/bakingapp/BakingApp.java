package ex.com.bakingapp;

import android.app.Application;
import com.facebook.stetho.Stetho;

import ex.com.bakingapp.data.api.QueryApi;
import ex.com.bakingapp.data.db.AppDB;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BakingApp extends Application {
    private static final String BASE_URL = "http://go.udacity.com/";
    private AppExecutors appExecutors;
    private static QueryApi queryApi;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();

        Stetho.initializeWithDefaults(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер JSON'а в объекты Gson
                .build();
        queryApi = retrofit.create(QueryApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }
    public static QueryApi getApi() { return queryApi; }
    public AppDB getDatabase() { return AppDB.getInstance(this, appExecutors);}
    public DataRepository getRepository() { return DataRepository.getInstance(getDatabase());}
}
