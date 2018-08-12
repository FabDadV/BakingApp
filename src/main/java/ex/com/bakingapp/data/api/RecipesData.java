package ex.com.bakingapp.data.api;

import java.util.ArrayList;

public class RecipesData {
        private ArrayList<RecipeApi> items;
        private ArrayList<Ingredient> ingredients;
        private ArrayList<StepApi> steps;

        public RecipesData(ArrayList<RecipeApi> items) {
            this.items = items;
        }
        public ArrayList<RecipeApi> getItems() {
            return items;
        }

    public class RecipeApi {
        public int id;
        public String name;
        public ArrayList<Ingredient> ingredients = null;
        public ArrayList<StepApi> stepsApi = null;
        public int servings;
        public String image;

        /* No args constructor for use in serialization */
        public RecipeApi() {
        }
        /* @param ingredients
         * @param id
         * @param servings
         * @param name
         * @param image
         * @param steps
         */
        public RecipeApi(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<StepApi> stepsApi,
                         int servings, String image) {
            super();
            this.id = id;
            this.name = name;
            this.ingredients = ingredients;
            this.stepsApi = stepsApi;
            this.servings = servings;
            this.image = image;
        }
    }

    public class Ingredient {
        public int quantity;
        public String measure;
        public String ingredient;

        public Ingredient() { }
        public Ingredient(int quantity, String measure, String ingredient) {
            super();
            this.quantity = quantity;
            this.measure = measure;
            this.ingredient = ingredient;
        }
    }

    public class StepApi {
        public int id;
        public String shortDescription;
        public String description;
        public String videoURL;
        public String thumbnailURL;

        public StepApi() { }
        public StepApi(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
            super();
            this.id = id;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
            this.thumbnailURL = thumbnailURL;
        }
    }
}