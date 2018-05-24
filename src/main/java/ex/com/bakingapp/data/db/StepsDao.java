package ex.com.bakingapp.data.db;

import java.util.List;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
@Dao
public interface StepsDao {
    @Query("SELECT * FROM steps WHERE recipeId = :recipeId")
    LiveData<List<StepEntity>> loadByParentId(int recipeId);

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId")
    List<StepEntity> getByParentId(int recipeId);

    @Query("SELECT * FROM steps WHERE id = :id")
    StepEntity getById(int id);

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId & stepId = :stepId")
    StepEntity getByRecipeId(int recipeId, int stepId);

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId & stepId = :stepId")
    LiveData<StepEntity> loadByRecipeId(int recipeId, int stepId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StepEntity> stepList);
}
