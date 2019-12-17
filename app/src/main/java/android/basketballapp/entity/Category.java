package android.basketballapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="category_id")
    public int id;

    @NonNull
    @ColumnInfo(name = "categoryName")
    public String name;

    @NonNull
    @ColumnInfo(name = "image")
    public String image;

    public Category(@NonNull String name, @NonNull String image) {
        this.name = name;
        this.image = image;
    }
}
