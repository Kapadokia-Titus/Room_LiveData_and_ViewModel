package kapadokia.nyandoro.room_livedata_and_viewmodel.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kapadokia.nyandoro.room_livedata_and_viewmodel.data.Word;
import kapadokia.nyandoro.room_livedata_and_viewmodel.data.WordRepository;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }


    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public void insert(Word word) { mRepository.insert(word); }
    public void deleteAll(){ mRepository.deleteAll();}
    public void deleteWord(Word word){ mRepository.deleteWord(word);}
    public void updateWord(Word word){ mRepository.updateWord(word);}
}