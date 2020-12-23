package kapadokia.nyandoro.room_livedata_and_viewmodel.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    int id;
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @Ignore
    public Word( int id, @NonNull String mWord) {
        this.id =id;
        this.mWord = mWord;
    }

    public Word(String word) {
        this.mWord = word;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return mWord;
    }

    public int getId() {
        return id;
    }
}
