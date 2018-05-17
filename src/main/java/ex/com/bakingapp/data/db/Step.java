package ex.com.bakingapp.data.db;

public interface Step {
    int getId();
    int getRecipeId();
    int getStepId();
    String getShortDescription();
    String getDescription();
    String getVideoURL();
    String getThumbnailURL();
}
