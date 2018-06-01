package ex.com.bakingapp.data.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipesList {
        private List<RecipeApi> items;
        @SerializedName("ingredients")
        private List<Ingredient> ingredients;
        private List<StepApi> stepsApi;

        public RecipesList(List<RecipeApi> items) {
            this.items = items;
        }
        public List<RecipeApi> getItems() {
            return items;
        }

    public class RecipeApi {
        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("ingredients")
        @Expose
        public List<Ingredient> ingredients = null;
        @SerializedName("steps")
        @Expose
        public List<StepApi> stepsApi = null;
        @SerializedName("servings")
        @Expose
        public int servings;
        @SerializedName("image")
        @Expose
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
        public RecipeApi(int id, String name, List<Ingredient> ingredients, List<StepApi> stepsApi,
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
        @SerializedName("quantity")
        @Expose
        public int quantity;
        @SerializedName("measure")
        @Expose
        public String measure;
        @SerializedName("ingredient")
        @Expose
        public String ingredient;

        /* No args constructor for use in serialization */
        public Ingredient() {
        }
        /* @param measure
         * @param ingredient
         * @param quantity
         */
        public Ingredient(int quantity, String measure, String ingredient) {
            super();
            this.quantity = quantity;
            this.measure = measure;
            this.ingredient = ingredient;
        }
    }

    public class StepApi {
        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("shortDescription")
        @Expose
        public String shortDescription;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("videoURL")
        @Expose
        public String videoURL;
        @SerializedName("thumbnailURL")
        @Expose
        public String thumbnailURL;

        /* No args constructor for use in serialization */
        public StepApi() {
        }
        /* @param id
         * @param shortDescription
         * @param description
         * @param videoURL
         * @param thumbnailURL
         */
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