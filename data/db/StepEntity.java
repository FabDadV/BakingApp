package fdv.ba.data.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "steps",
        foreignKeys = @ForeignKey(entity = RecipeEntity.class,
                parentColumns = "id", childColumns = "recipeId", onDelete = ForeignKey.CASCADE),
                indices = {@Index(value = {"recipeId"})})
public class StepEntity implements Step {
    @PrimaryKey
    private int id;
    private int recipeId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    @Override
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    @Override
    public int getRecipeId() {return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
    @Override
    public String getShortDescription() { return shortDescription;}
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    @Override
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    @Override
    public String getVideoURL() { return videoURL; }
    public void setVideoURL(String videoURL) { this.videoURL = videoURL; }
    @Override
    public String getThumbnailURL() { return thumbnailURL; }
    public void setThumbnailURL(String thumbnailURL) { this.thumbnailURL = thumbnailURL; }

    @Ignore
    public StepEntity() { }
    public StepEntity(int id, int recipeId, String shortDescription, String description,
                      String videoURL, String thumbnailURL) {
        this.id = id;
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }
    public StepEntity(Step step) {
        this.id = step.getId();
        this.recipeId = step.getRecipeId();
        this.shortDescription = step.getShortDescription();
        this.description = step.getDescription();
        this.videoURL = step.getVideoURL();
    }
}