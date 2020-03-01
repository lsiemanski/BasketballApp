package android.basketballapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName="drills", foreignKeys =
@ForeignKey(entity = Category.class,
        parentColumns = "category_id",
        childColumns = "categoryId",
        onDelete = ForeignKey.CASCADE))
public class Drill {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="drill_id")
    public int drillId;

    @NonNull
    @ColumnInfo(name = "drillName")
    public String name;

    @NonNull
    @ColumnInfo(name = "htmlFile")
    public String htmlFile;

    @ColumnInfo(name = "defaultShots")
    public int defaultShots;

    @NonNull
    @ColumnInfo(name="categoryId")
    public int categoryId;

    public Drill(@NonNull String name, @NonNull String htmlFile, int defaultShots, @NonNull int categoryId) {
        this.name = name;
        this.htmlFile = htmlFile;
        this.defaultShots = defaultShots;
        this.categoryId = categoryId;
    }
}
