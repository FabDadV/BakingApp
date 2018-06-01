package ex.com.bakingapp.data.db;

import java.util.ArrayList;
import java.util.List;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.widget.Toast;

import ex.com.bakingapp.AppExecutors;
import ex.com.bakingapp.BakingApp;
import ex.com.bakingapp.data.api.RecipesList;
import ex.com.bakingapp.utils.DataGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// @TypeConverters(ListConverter.class)
@Database(entities = {RecipeEntity.class, StepEntity.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "baking-app";
    public abstract RecipesDao recipesDao();
    public abstract StepsDao stepsDao();
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDB getInstance(final Context context, final AppExecutors executors) {
        Log.d("TAG", " getInstance");

        if (sInstance == null) {
            synchronized (AppDB.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }
    /* Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDB buildDatabase(final Context appContext, final AppExecutors executors) {
//        posts = new ArrayList<>();
        Log.d("TAG", " buildDatabase");

        return Room.databaseBuilder(appContext, AppDB.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            Log.d("TAG", " CreateDB");
                            addDelay();
                            Log.d("TAG", "addDelay()");

/*
                            BakingApp.getApi().loadRecipesList().enqueue(new Callback<RecipesList>() {
                                @Override
                                public void onResponse(Call<RecipesList> call, Response<RecipesList> response) {
                                    RecipesList recipesList = response.body();

                                }
                                @Override
                                public void onFailure(Call<RecipesList> call, Throwable t) {
                                    //Проверка на ошибку
                                    Toast.makeText(getContext(), "An error occurred during networking",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
*/


                           // Generate the data for pre-population
                            AppDB database = AppDB.getInstance(appContext, executors);
                            List<RecipeEntity> recipes = DataGenerator.generateRecipes();
                            List<StepEntity> steps = DataGenerator.generateStepsForRecipes(recipes);
                            insertData(database, recipes, steps);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }
    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }
    private void setDatabaseCreated(){ isDatabaseCreated.postValue(true);}

    private static void insertData(final AppDB database, final List<RecipeEntity> recipes,
                                   final List<StepEntity> steps) {
        // Constant for logging
        String TAG = "called AppDB ";
        Log.d(TAG, "insertData");

        database.runInTransaction(() -> {
            database.recipesDao().insertAll(recipes);
            database.stepsDao().insertAll(steps);
        });
    }
    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }
    public LiveData<Boolean> getDatabaseCreated() { return isDatabaseCreated; }
}
