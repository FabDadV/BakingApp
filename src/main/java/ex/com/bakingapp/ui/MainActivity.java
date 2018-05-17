package ex.com.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ex.com.bakingapp.R;
import ex.com.bakingapp.data.db.Recipe;

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
                    .add(R.id.fragment_container, fragment, ListFragment.TAG)
                    .commit();
        }
    }
    /** Shows the recipe detail fragment */
    public void show(Recipe recipe) {
        if(findViewById(R.id.right_container) == null) {
            StepsFragment stepsFragment = StepsFragment.forRecipe(recipe.getId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("recipe")
                    .replace(R.id.fragment_container, stepsFragment, null)
                    .commit();
        }
        else {
            StepsFragment stepsFragment = StepsFragment.forRecipe(recipe.getId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("recipe")
                    .replace(R.id.right_container, stepsFragment, null)
                    .commit();
        }
    }
}