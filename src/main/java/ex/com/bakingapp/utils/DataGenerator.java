package ex.com.bakingapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ex.com.bakingapp.data.db.Recipe;
import ex.com.bakingapp.data.db.RecipeEntity;
import ex.com.bakingapp.data.db.StepEntity;
/**
 * Generates data to test the database
 */
public class DataGenerator {
    private static final String[] FIRST = new String[]{
            "Special edition", "New", "Cheap", "Quality", "Used"};
    private static final String[] SECOND = new String[]{
            "Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"};
    private static final String[] DESCRIPTION = new String[]{
            "is finally here", "is recommended by Stan S. Stanman",
            "is the best sold recipe on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine"};
    private static final String[] INGREDIENTS = new String[]{
            "Ingredient 1: ", "Ingredient 2: ", "Ingredient 3: ", "Ingredient 4: ", "Ingredient 5: ",
            "Ingredient 6: ", "Ingredient 7: ", "Ingredient 8: ", "Ingredient 9: ", "Ingredient 10: ",
            "Ingredient 11: ", "Ingredient 12: ", "Ingredient 13: ", "Ingredient 14: ", "Ingredient 15: "};
    private static final String[] STEPS = new String[]{
            "Step 1", "Step 2", "Step 3", "Step 4", "Step 5",
            "Step 6", "Step 7", "Step 8", "Step 9", "Step 10",
            "Step 11", "Step 12", "Step 13", "Step 14", "Step 15"};
    private static final String[] VIDEOS = new String[]{
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc43_1-melt-choclate-chips-and-butter-brownies/1-melt-choclate-chips-and-butter-brownies.mp4",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc9e_4-sift-flower-add-coco-powder-salt-brownies/4-sift-flower-add-coco-powder-salt-brownies.mp4",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc62_2-mix-egss-with-choclate-butter-brownies/2-mix-egss-with-choclate-butter-brownies.mp4",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcc8_5-mix-wet-and-cry-batter-together-brownies/5-mix-wet-and-cry-batter-together-brownies.mp4",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf4_8-put-brownies-in-oven-to-bake-brownies/8-put-brownies-in-oven-to-bake-brownies.mp4",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf9_9-final-product-brownies/9-final-product-brownies.mp4",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4"
    };
    private static final String[] THUMBNAILS = new String[]{
            "https://upload.wikimedia.org/wikipedia/commons/c/ca/Bosna_mit_2_Bratw%C3%BCrsten.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/4/48/Chivito1.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Club_sandwich.png/800px-Club_sandwich.png",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Steamed_Sandwich%2Ctaken_by_LeoAlmighty.jpg/600px-Steamed_Sandwich%2Ctaken_by_LeoAlmighty.jpg"
    };
    public static List<RecipeEntity> generateRecipes() {
        List<RecipeEntity> recipes = new ArrayList<>(FIRST.length * SECOND.length);
        Random rnd = new Random();
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                RecipeEntity recipe = new RecipeEntity();
                List<String> ingredients = generateIngredientsForRecipes();
                recipe.setId(FIRST.length * i + j + 1);
                recipe.setName(FIRST[i] + " " + SECOND[j]);
                recipe.setServings(rnd.nextInt(8));
                recipe.setImage(recipe.getName() + " " + DESCRIPTION[j]);
                recipe.setIngredients(ingredients);
                recipes.add(recipe);
            }
        }
        return recipes;
    }
    private static List<String> generateIngredientsForRecipes() {
        List<String> ingredients = new ArrayList<>();
        Random rnd = new Random();
        int number = rnd.nextInt(15) + 1;
        for (int i = 0; i < number; i++) {
            ingredients.add(INGREDIENTS[i] + String.valueOf(rnd.nextInt(8)) + " gr;");
        }
        return ingredients;
    }
    public static List<StepEntity> generateStepsForRecipes(
            final List<RecipeEntity> recipes) {
        List<StepEntity> steps = new ArrayList<>();
        Random rnd = new Random();

        for (Recipe recipe : recipes) {
            int stepsNumber = rnd.nextInt(15) + 1;
            for (int i = 0; i < stepsNumber; i++) {
                StepEntity step = new StepEntity();
                step.setRecipeId(recipe.getId());
                step.setShortDescription(STEPS[i]);
                step.setDescription(STEPS[i] + " for " + recipe.getName());
                int k = rnd.nextInt(7);
                String str = (k>5 ? "" : VIDEOS[k]);
                step.setVideoURL(str);
                k = rnd.nextInt(12);
                str = (k>5 ? "" : THUMBNAILS[k]);
                step.setThumbnailURL(str);
                steps.add(step);
            }
        }
        return steps;
    }
}
