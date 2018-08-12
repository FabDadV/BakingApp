package ex.com.bakingapp.data.api;

import java.util.ArrayList;

public class RecipeApi {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<StepApi> steps;
    private int servings;
    private String image;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public ArrayList<Ingredient> getIngredients() {return ingredients;}
    public void setIngredients(ArrayList<Ingredient> ingredients) {this.ingredients = ingredients;}

    public ArrayList<StepApi> getSteps() {return steps;}
    public void setSteps(ArrayList<StepApi> steps) {this.steps = steps;}

    public Integer getServings() {return servings;}
    public void setServings(Integer servings) {this.servings = servings;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    public RecipeApi() { }
    public RecipeApi(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<StepApi> steps,
                     int servings, String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }
}
