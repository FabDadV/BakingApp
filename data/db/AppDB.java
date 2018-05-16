package fdv.ba.data.db;

import java.util.List;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import fdv.ba.AppExecutors;
import fdv.ba.utils.DataGenerator;

@TypeConverters(ListConverter.class)
@Database(entities = {RecipeEntity.class, StepEntity.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "baking-app";
    public abstract RecipesDao recipesDao();
    public abstract StepsDao stepsDao();
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDB getInstance(final Context context, final AppExecutors executors) {
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
    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDB buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDB.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
//                            addDelay();
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
