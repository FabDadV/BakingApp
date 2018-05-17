package ex.com.bakingapp.viewmodel;

import java.util.List;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import ex.com.bakingapp.BakingApp;
import ex.com.bakingapp.DataRepository;
import ex.com.bakingapp.data.db.RecipeEntity;
import ex.com.bakingapp.data.db.StepEntity;

public class ItemViewModel extends AndroidViewModel {
    private final LiveData<RecipeEntity> observableRecipe;
    public ObservableField<RecipeEntity> recipe = new ObservableField<>();
    private final LiveData<List<StepEntity>> observableSteps;

    public ItemViewModel(@NonNull Application application, DataRepository repository, final int recipeId) {
        super(application);
//        int recipeId = recipe_id; // why???
        observableSteps = repository.loadSteps(recipeId);
        observableRecipe = repository.loadRecipe(recipeId);
    }
    /**
     * Expose the LiveData Steps query so the UI can observe it.
     */
    public LiveData<List<StepEntity>> getSteps() { return observableSteps; }
    public LiveData<RecipeEntity> getObservableRecipe() { return observableRecipe; }
    public void setRecipe(RecipeEntity recipe) { this.recipe.set(recipe); }
    /**
     * A creator is used to inject the recipe ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the recipe ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final int recipeId;
        private final DataRepository repository;

        public Factory(@NonNull Application application, int recipeId) {
            this.application = application;
            this.recipeId = recipeId;
            repository = ((BakingApp) application).getRepository();
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, repository, recipeId);
        }
    }

}
