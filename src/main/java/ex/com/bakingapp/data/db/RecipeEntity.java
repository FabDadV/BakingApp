package ex.com.bakingapp.data.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeEntity implements Recipe {
    @PrimaryKey
    private int id;
    private String name;
    private String ingredients;
    private int servings;
    private String image;

    @Override
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    @Override
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Override
    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }
    @Override
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    @Override
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    @Ignore
    public RecipeEntity() { }
    public RecipeEntity(int id, String name, String ingredients, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.servings = servings;
        this.image = image;
    }
    public RecipeEntity(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.ingredients = recipe.getIngredients();
        this.servings = recipe.getServings();
        this.image = recipe.getImage();
    }
}
