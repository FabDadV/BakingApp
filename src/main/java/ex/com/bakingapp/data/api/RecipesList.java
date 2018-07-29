package ex.com.bakingapp.data.api;

import java.util.List;
import ex.com.bakingapp.data.db.RecipeEntity;

public class RecipesList {
    private List<RecipeEntity> recipes;
    public List<RecipeEntity> getList() { return recipes; }
}
