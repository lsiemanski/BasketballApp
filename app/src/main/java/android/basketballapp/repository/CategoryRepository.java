package android.basketballapp.repository;

import android.app.Application;
import android.basketballapp.dao.CategoryDao;
import android.basketballapp.database.BasketballAppRoomDatabase;
import android.basketballapp.entity.Category;
import android.basketballapp.entity.Player;

public class CategoryRepository {
    private CategoryDao categoryDao;

    public CategoryRepository(Application application) {
        BasketballAppRoomDatabase db = BasketballAppRoomDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
    }

    public Category getCategory(int id) {
        return categoryDao.getCategory(id);
    }

    public void insert(Category category) {
        BasketballAppRoomDatabase.databaseWriterExecutor.execute(() -> {
            categoryDao.insert(category);
        });
    }
}
