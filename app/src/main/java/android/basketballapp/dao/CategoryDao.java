package android.basketballapp.dao;

import android.basketballapp.entity.Category;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM categories WHERE category_id=:id")
    Category getCategory(int id);

    @Query("SELECT * FROM categories WHERE categoryName=:name")
    Category getCategory(String name);

    @Query("DELETE FROM categories")
    void deleteAll();
}
