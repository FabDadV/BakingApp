package ex.com.bakingapp.viewmodel;

import java.util.List;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import ex.com.bakingapp.BakingApp;
import ex.com.bakingapp.data.db.RecipeEntity;

public class ListViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<RecipeEntity>> observableRecipes;
    public ListViewModel(Application application) {
        super(application);
        observableRecipes = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableRecipes.setValue(null);
        // Livedata
        LiveData<List<RecipeEntity>> recipes = ((BakingApp) application).getRepository().getRecipes();
        // observe the changes of the recipes from the database and forward them
        observableRecipes.addSource(recipes, observableRecipes::setValue);
    }
    /* Expose the LiveData Recipes query so the UI can observe it. */
    public LiveData<List<RecipeEntity>> getRecipes() { return observableRecipes; }
}
