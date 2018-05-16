package fdv.ba.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fdv.ba.data.db.Recipe;
import fdv.ba.data.db.RecipeEntity;
import fdv.ba.data.db.StepEntity;
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
                step.setVideoURL(STEPS[i] + " VideoURL for " + recipe.getName());
                step.setThumbnailURL(STEPS[i] + " ThumbnailURL for " + recipe.getName());
                steps.add(step);
            }
        }
        return steps;
    }
}
