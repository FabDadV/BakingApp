package ex.com.bakingapp.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ex.com.bakingapp.R;
import ex.com.bakingapp.databinding.ListFragmentBinding;
import ex.com.bakingapp.viewmodel.ListViewModel;

public class ListFragment extends Fragment {
    private static final String TAG = ListFragment.class.getSimpleName();
    private static final int DEFAULT_SIZE = 320;
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
        viewModel.getRecipes().observe(this, recipes -> {
            if (recipes != null) {
                listBinding.setIsLoading(false);
                recipeAdapter.setRecipeList(recipes);
            } else {
                listBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes sync.
            listBinding.executePendingBindings();
        });
    }
    private final RecipeClickCallback recipeClickCallback = recipe -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(recipe);
        }
    };
    /* https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns
     * calculate number of columns in GridLayoutManager
     */
    private static int calculateColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / DEFAULT_SIZE);
    }
}
