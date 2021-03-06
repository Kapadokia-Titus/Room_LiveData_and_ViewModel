package kapadokia.nyandoro.room_livedata_and_viewmodel.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import kapadokia.nyandoro.room_livedata_and_viewmodel.R;
import kapadokia.nyandoro.room_livedata_and_viewmodel.data.Word;

public class MainActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public static final String EXTRA_DATA_UPDATE_WORD = "extra_word_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Update the cached copy of the words in the adapter.
        mWordViewModel.getAllWords().observe(this, adapter::setWords);


        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word myWord = adapter.getWordAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleting "+ myWord.getWord(), Toast.LENGTH_LONG).show();

                //delete word
                mWordViewModel.deleteWord(myWord);
            }
        });
        helper.attachToRecyclerView(recyclerView);


       adapter.setOnItemClickListener(new WordListAdapter.ClickListener() {
           @Override
           public void onItemClick(View v, int position) {
               Word word = adapter.getWordAtPosition(position);
               launchUpdateWordActivity(word);
           }

           private void launchUpdateWordActivity(Word word) {
               Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
               intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
               intent.putExtra(EXTRA_DATA_ID, word.getId());
               startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
           }
       });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){

            String wordData = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NewWordActivity.EXTRA_REPLY_ID, -1);
            if (id != -1) {
                mWordViewModel.updateWord(new Word(wordData));
            } else {
                Toast.makeText(MainActivity.this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }

    }

    public void addContent(View view) {
        Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data){
            Toast.makeText(this, "Clearing data ...", Toast.LENGTH_LONG).show();

            //delete all
            mWordViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}