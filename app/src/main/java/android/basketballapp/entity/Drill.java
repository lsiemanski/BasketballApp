package android.basketballapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="drills")
public class Drill {

    @PrimaryKey(autoGenerate = true)
    public int drillId;

    @NonNull
    @ColumnInfo(name = "drillName")
    public String name;

    @NonNull
    @ColumnInfo(name = "htmlFile")
    public String htmlFile;

    @Embedded
    public Category category;

    public Drill(@NonNull String name, @NonNull String htmlFile, Category category) {
        this.name = name;
        this.htmlFile = htmlFile;
        this.category = category;
    }
}
