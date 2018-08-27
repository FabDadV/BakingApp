package ex.com.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ex.com.bakingapp.R;
import ex.com.bakingapp.data.db.Recipe;
import ex.com.bakingapp.data.db.Step;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Add recipe list fragment if this is first creation
        if (savedInstanceState == null) {
            ListFragment fragment = new ListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit();
        }
    }
    /** Shows the recipe detail fragment */
    public void show(Recipe recipe) {
            StepsFragment stepsFragment = StepsFragment.forRecipe(recipe.getId(), recipe.getName(), recipe.getIngredients());
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("recipe")
                    .replace(R.id.fragment_container, stepsFragment, null)
                    .commit();
    }
    /** Shows the recipe detail fragment */
    public void showStep(Step step) {
        MakeFragment makeFragment = MakeFragment.forStep(step);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("step")
                .replace(R.id.fragment_container, makeFragment, null)
                .commit();
    }
}
