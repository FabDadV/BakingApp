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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ex.com.bakingapp.AppExecutors;
import ex.com.bakingapp.BakingApp;
import ex.com.bakingapp.utils.DataGenerator;

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
        Log.d("TAG", " buildDatabase");

        return Room.databaseBuilder(appContext, AppDB.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        ArrayList<RecipeEntity> recipes = new ArrayList<RecipeEntity>();

                        BakingApp.getApi().getData().enqueue(new retrofit2.Callback<List<RecipeEntity>>() {
                            @Override
                            public void onResponse(Call<List<RecipeEntity>> call, Response<List<RecipeEntity>> response) {
                                recipes.addAll(response.body());
                            }
                            @Override
                            public void onFailure(Call<List<RecipeEntity>> call, Throwable t) {
                                //Проверка на ошибку
                                Log.d("TAG", "Error" + t.toString());
                            }
                        });
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            Log.d("TAG", " CreateDB");
                            addDelay();
                            Log.d("TAG", "addDelay()");


                            // Generate the data for pre-population
                            AppDB database = AppDB.getInstance(appContext, executors);
//                            recipes = DataGenerator.generateRecipes();
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
