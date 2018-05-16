package fdv.ba.data;

import java.util.List;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import fdv.ba.data.db.AppDB;
import fdv.ba.data.db.RecipeEntity;
import fdv.ba.data.db.StepEntity;
/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {
    private static DataRepository sInstance;
    private final AppDB appDB;
    private MediatorLiveData<List<RecipeEntity>> observableRecipes;

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
    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<RecipeEntity>> getRecipes() { return observableRecipes; }
    public LiveData<RecipeEntity> loadRecipe(final int recipeId) {
        return appDB.recipesDao().loadById(recipeId);
    }
    public LiveData<List<StepEntity>> loadSteps(final int recipeId) {
        return appDB.stepsDao().loadByParentId(recipeId);
    }
}
