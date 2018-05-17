package ex.com.bakingapp.ui;

import java.util.List;
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
import ex.com.bakingapp.databinding.StepsFragmentBinding;
import ex.com.bakingapp.data.db.Step;
import ex.com.bakingapp.data.db.StepEntity;
import ex.com.bakingapp.data.db.RecipeEntity;
import ex.com.bakingapp.viewmodel.ItemViewModel;

public class StepsFragment extends Fragment {
    private static final String KEY_RECIPE_ID = "recipe-id";
    private StepsFragmentBinding binding;
    private StepsAdapter stepsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.steps_fragment, container, false);
        // Create and set the adapter for the RecyclerView.
        stepsAdapter = new StepsAdapter(stepClickCallback);
        binding.detailsList.setAdapter(stepsAdapter);
        return binding.getRoot();
    }
    private final StepClickCallback stepClickCallback = new StepClickCallback() {
        @Override
        public void onClick(Step step) {
            // no-op
        }
    };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ItemViewModel.Factory factory = new ItemViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_RECIPE_ID));
        final ItemViewModel model = ViewModelProviders.of(this, factory)
                .get(ItemViewModel.class);
        binding.setItemViewModel(model);
        subscribeToModel(model);
    }
    private void subscribeToModel(final ItemViewModel model) {
        // Observe recipe data
        model.getObservableRecipe().observe(this, new Observer<RecipeEntity>() {
            @Override
            public void onChanged(@Nullable RecipeEntity recipeEntity) {
                model.setRecipe(recipeEntity);
            }
        });
        // Observe steps
        model.getSteps().observe(this, new Observer<List<StepEntity>>() {
            @Override
            public void onChanged(@Nullable List<StepEntity> steps) {
                if (steps != null) {
                    binding.setIsLoading(false);
                    stepsAdapter.setStepList(steps);
                } else {
                    binding.setIsLoading(true);
                }
            }
        });
    }
    /** Creates recipe fragment for specific recipe ID */
    public static StepsFragment forRecipe(int recipeId) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }
}