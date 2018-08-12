package ex.com.bakingapp.data.api;

import java.util.ArrayList;
import java.util.List;

public class RecipesData {
        private ArrayList<RecipeApi> items;

        public RecipesData(ArrayList<RecipeApi> items) {this.items = items;}
        public ArrayList<RecipeApi> getItems() {return items;}
}