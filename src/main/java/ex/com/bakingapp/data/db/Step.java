package ex.com.bakingapp.data.db;

public interface Step {
    int getId();
    int getRecipeId();
    String getShortDescription();
    String getDescription();
    String getVideoURL();
    String getThumbnailURL();
}
