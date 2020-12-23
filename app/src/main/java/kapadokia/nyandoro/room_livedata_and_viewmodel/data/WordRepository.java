package kapadokia.nyandoro.room_livedata_and_viewmodel.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;


    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }
    public void deleteAll(){
        new deleteAllWordAsyncTask(mWordDao).execute();
    }



    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;



        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllWordAsyncTask extends AsyncTask<Void, Void, Void>{
        private WordDao mAsyncTaskDao;

        deleteAllWordAsyncTask(WordDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    // asyncTask for deleting a single word
    public static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void>{

        private WordDao mWordDao;

        deleteWordAsyncTask(WordDao dao){
            mWordDao =dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mWordDao.deleteWord(words[0]);
            return null;
        }
    }


    //add the deleteWord() method to invoke the AsyncTask you defined.

    public void deleteWord(Word word){
        new deleteWordAsyncTask(mWordDao).execute(word);
    }
}
