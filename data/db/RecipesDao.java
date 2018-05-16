package fdv.ba.data.db;

import java.util.List;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
@Dao
public interface RecipesDao {
    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeEntity>> loadAll();

    @Query("SELECT * FROM recipes WHERE id = :id")
    LiveData<RecipeEntity> loadById(int id);

    @Query("SELECT * FROM recipes WHERE id = :id")
    RecipeEntity getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecipeEntity> recipeList);
}
