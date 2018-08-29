package ex.com.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ex.com.bakingapp.R;
import ex.com.bakingapp.data.db.RecipeEntity;

import static ex.com.bakingapp.ui.StepsFragment.EXTRA_INGREDIENTS;
import static ex.com.bakingapp.ui.StepsFragment.EXTRA_RECIPE;
import static ex.com.bakingapp.ui.StepsFragment.KEY_RECIPE_ID;
import static ex.com.bakingapp.ui.StepsFragment.RECIPE_NAME;

public class RecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        /** Creates recipe fragment for specific recipe ID */
//        Fragment fragment = new Fragment();
        StepsFragment fragment = new StepsFragment();
//        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("recipe")
                    .replace(R.id.fragment_container, fragment, null)
                    .commit();
//        }
    }
}
