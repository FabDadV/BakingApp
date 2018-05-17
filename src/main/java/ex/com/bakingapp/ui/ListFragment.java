package ex.com.bakingapp.ui;

import java.util.List;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ex.com.bakingapp.R;
import ex.com.bakingapp.databinding.ListFragmentBinding;
import ex.com.bakingapp.data.db.Recipe;
import ex.com.bakingapp.data.db.RecipeEntity;
import ex.com.bakingapp.viewmodel.ListViewModel;

public class ListFragment extends Fragment {
    public static final String TAG = "ListViewModel";
    private RecipeAdapter recipeAdapter;
    private ListFragmentBinding listBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        listBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        recipeAdapter = new RecipeAdapter(recipeClickCallback);
        listBinding.rvList.setAdapter(recipeAdapter);
        return listBinding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListViewModel viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        subscribeUi(viewModel);
    }
    private void subscribeUi(ListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getRecipes().observe(this, new Observer<List<RecipeEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntity> recipes) {
                if (recipes != null) {
                    listBinding.setIsLoading(false);
                    recipeAdapter.setRecipeList(recipes);
                } else {
                    listBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                listBinding.executePendingBindings();
            }
        });
    }
    private final RecipeClickCallback recipeClickCallback = new RecipeClickCallback() {
        @Override
        public void onClick(Recipe recipe) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(recipe);
            }
        }
    };
}