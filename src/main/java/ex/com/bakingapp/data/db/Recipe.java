package ex.com.bakingapp.data.db;

import java.util.List;

public interface Recipe {
    int getId();
    String getName();
    int getServings();
    String getImage();
    List<String> getIngredients();
}
