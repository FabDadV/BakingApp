package ex.com.bakingapp;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import ex.com.bakingapp.data.db.AppDB;
import ex.com.bakingapp.data.db.RecipeEntity;
import ex.com.bakingapp.data.db.StepEntity;
/* Repository handling the work with recipes and steps. */
public class DataRepository {
    private static DataRepository sInstance;
    private final AppDB appDB;
    private final MediatorLiveData<List<RecipeEntity>> observableRecipes;

    private DataRepository(final AppDB database) {
        appDB = database;
        observableRecipes = new MediatorLiveData<>();

        observableRecipes.addSource(appDB.recipesDao().loadAll(),
                recipes -> {
                    if (appDB.getDatabaseCreated().getValue() != null) {
                        observableRecipes.postValue(recipes);
                    }
                });
    }
    public static DataRepository getInstance(final AppDB database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }
    /* Get the list of recipes from the database and get notified when the data changes. */
    public LiveData<List<RecipeEntity>> getRecipes() { return observableRecipes; }
    public LiveData<RecipeEntity> loadRecipe(final int recipeId) {
        return appDB.recipesDao().loadById(recipeId);
    }
    public LiveData<List<StepEntity>> loadSteps(final int recipeId) {
        return appDB.stepsDao().loadByParentId(recipeId);
    }
    public MediatorLiveData<List<RecipeEntity>> getObservableRecipes() {
        return observableRecipes;
    }
    public StepEntity loadEntity(final int recipeId, final int stepId) {
        StepEntity step = new StepEntity();
        AppExecutors.getInstance().diskIO().execute(() -> {
            StepEntity entity = new StepEntity();
            entity = appDB.stepsDao().getByRecipeId(recipeId, stepId);
        });
        return step;
    }
}
