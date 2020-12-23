package kapadokia.nyandoro.room_livedata_and_viewmodel.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

    // a method to get a single word.
    // no need to return a liveData.
    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    // deleting a single item
    @Delete
    void deleteWord(Word word);

    // update word
    @Update
    void update(Word... word);
}
