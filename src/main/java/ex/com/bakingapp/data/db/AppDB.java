package ex.com.bakingapp.data.db;

import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import ex.com.bakingapp.AppExecutors;
import ex.com.bakingapp.data.api.DataResponse;
import ex.com.bakingapp.data.api.Ingredient;
import ex.com.bakingapp.data.api.RecipeApi;
import ex.com.bakingapp.data.api.StepApi;
import ex.com.bakingapp.utils.DataGenerator;

// @TypeConverters(ListConverter.class)
@Database(entities = {RecipeEntity.class, StepEntity.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "baking_app";
    public abstract RecipesDao recipesDao();
    public abstract StepsDao stepsDao();
    private ArrayList<RecipeApi> recipesApis;
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDB getInstance(final Context context, final AppExecutors executors) {
        Log.d("TAG", "DB getInstance");

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
        Log.d("TAG", "buildDatabase");

        AppDB appDB = Room.databaseBuilder(appContext, AppDB.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.d("TAG", "CreateDB");

                        ArrayList<RecipeApi> recipesApi = DataResponse.getInfo();
                        do{addDelay();}while(recipesApi.size()==0);

                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            // Generate the data for pre-population
                            Log.d("TAG", "Create db");
                            AppDB database = AppDB.getInstance(appContext, executors);

                            List<RecipeEntity> recipes = getRecipesList(recipesApi);
                            List<StepEntity> steps = getStepsForRecipes(recipesApi);
/*
                            List<RecipeEntity> recipes = DataGenerator.generateRecipes();
                            List<StepEntity> steps = DataGenerator.generateStepsForRecipes(recipes);
*/
                            insertData(database, recipes, steps);
                            Log.d("TAG", "insertData");
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
        return appDB;
    }
    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }
    private void setDatabaseCreated(){isDatabaseCreated.postValue(true);}

    private static void insertData(final AppDB database, final List<RecipeEntity> recipes,
                                   final List<StepEntity> steps) {
        database.runInTransaction(() -> {
            database.recipesDao().insertAll(recipes);
            database.stepsDao().insertAll(steps);
        });
    }

    private static List<RecipeEntity> getRecipesList(ArrayList<RecipeApi> recipesApi) {
        ArrayList<RecipeEntity> recipes = new ArrayList<>();
        int l = recipesApi.size();
        for (int i = 0; i < l; i++) {
            RecipeApi recipeApi = recipesApi.get(i);
            RecipeEntity recipe = new RecipeEntity();
            recipe.setId(recipeApi.getId());
            recipe.setName(recipeApi.getName());
            recipe.setServings(recipeApi.getServings());
            recipe.setImage(recipeApi.getImage());
            ArrayList<Ingredient> ingList = new ArrayList<>();
            ingList = recipeApi.getIngredients();
            StringBuilder sb = new StringBuilder();
            for (Ingredient ing : ingList) {
                sb.append(ing.getQuantity()).append(" ").append(ing.getMeasure()).append(" ")
                  .append(ing.getIngredient()).append("\n");
            }
            recipe.setIngredients(sb.toString());
            recipes.add(recipe);
        }
        Log.d("TAG", " getRecipesList");
        return recipes;
    }
    private static List<StepEntity> getStepsForRecipes(ArrayList<RecipeApi> recipesApi) {
        ArrayList<StepEntity> steps = new ArrayList<>();
        int recipeId;
        int id = 1;
        int l = recipesApi.size();
        for (int i = 0; i < l; i++) {
            RecipeApi recipeApi = recipesApi.get(i);
            recipeId = recipeApi.getId();
            ArrayList<StepApi> stepList = new ArrayList<>();
            stepList = recipeApi.getSteps();
            for (StepApi stepApi : stepList) {
                StepEntity step = new StepEntity();
                step.setId(id);
                step.setRecipeId(recipeId);
                step.setStepId(stepApi.getId());
                step.setShortDescription(stepApi.getShortDescription());
                step.setDescription(stepApi.getDescription());
                step.setVideoURL(stepApi.getVideoURL());
                step.setThumbnailURL(stepApi.getThumbnailURL());
                id++;
                steps.add(step);
            }
        }
        Log.d("TAG", " getStepsForRecipes");
        return steps;
    }


    private static void addDelay() {
        try {
            Log.d("TAG", "addDelay()");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
    public LiveData<Boolean> getDatabaseCreated() { return isDatabaseCreated; }
}
