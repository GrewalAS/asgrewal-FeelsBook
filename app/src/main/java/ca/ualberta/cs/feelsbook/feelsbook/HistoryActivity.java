package ca.ualberta.cs.feelsbook.feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * Class was created to make the views in HistoryActivity work properly
 */
public class HistoryActivity extends AppCompatActivity {
    // Need to store the emotions
    private EmotionStorage emotionStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    /**
     * Loading the emotions here. Setting the adapter and creating a click listener for the list view.
     */
    @Override
    protected void onResume(){
        super.onResume();
        // Getting the intent and the file we need to read to fill emotion storage
        Intent intent = getIntent();
        // Getting the file name
        final String fileName = intent.getExtras().getString("fileName");
        // Getting the emotion
        this.emotionStorage = new EmotionStorage(getApplicationContext(), fileName);
        // Getting the value of all the emotion stats, creating and Adapter and setting it
        HistoryEmotionStorageAdapter adapter = new HistoryEmotionStorageAdapter(this.emotionStorage);
        // Getting the list view
        ListView listView = findViewById(R.id.emotionsHistoryListView);
        listView.setAdapter(adapter);
        // Also need to set the click listener on the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(HistoryActivity.this, EmotionsDetailAndEditActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("fileName", fileName);
                startActivity(intent);
            }
        });
    }

    /**
     * Destroying the instance of the emotions storage, deleting the adapter and deleting the click listener for the list view.
     */
    @Override
    protected void onPause(){
        super.onPause();
        // Setting the emotionStorage to null. New instance is created onResume
        this.emotionStorage = null;
        // Getting rid of the adapter as well, will be set onResume again
        ListView listView = findViewById(R.id.emotionsHistoryListView);
        listView.setAdapter(null);
        // Also need to delete the click listener on the listView
        listView.setOnItemClickListener(null);
    }
}
