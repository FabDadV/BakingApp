package ex.com.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ex.com.bakingapp.R;
import ex.com.bakingapp.databinding.StepsFragmentBinding;
import ex.com.bakingapp.viewmodel.ItemViewModel;
import ex.com.bakingapp.widget.BackingWidget;

public class StepsFragment extends Fragment {
    private static final String KEY_RECIPE_ID = "recipe_id";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String EXTRA_INGREDIENTS = "extra_ings";
    private StepsFragmentBinding binding;
    private StepsAdapter stepsAdapter;
    private String name;
    private String text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
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
    private final StepClickCallback stepClickCallback = step -> {
//                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).showStep(step);
//                }
   };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = getArguments().getString(RECIPE_NAME);
        text = getArguments().getString(EXTRA_INGREDIENTS);
        ItemViewModel.Factory factory = new ItemViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_RECIPE_ID));
        final ItemViewModel model = ViewModelProviders.of(this, factory)
                .get(ItemViewModel.class);
        binding.setItemViewModel(model);
        subscribeToModel(model);
    }
    private void subscribeToModel(final ItemViewModel model) {
        // Observe recipe data
        model.getObservableRecipe().observe(this, recipeEntity -> model.setRecipe(recipeEntity));
        // Observe steps
        model.getSteps().observe(this, steps -> {
            if (steps != null) {
                binding.setIsLoading(false);
                stepsAdapter.setStepList(steps);
            } else {
                binding.setIsLoading(true);
            }
        });
    }
    /** Creates recipe fragment for specific recipe ID */
    public static StepsFragment forRecipe(int recipeId, String name, String text) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID, recipeId);
        args.putString(RECIPE_NAME, name);
        args.putString(EXTRA_INGREDIENTS, text);
        fragment.setArguments(args);
        return fragment;
    }
    // Creating menu: Favorite & Add Widget
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.detail_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_widget:
//                Log.d("TAG", "updateWidgets: " + name + " : " + text);
                Context context = getContext();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BackingWidget.class));
                //Now update all widgets
                BackingWidget.updateWidgets(context, appWidgetManager, name, text, appWidgetIds);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
