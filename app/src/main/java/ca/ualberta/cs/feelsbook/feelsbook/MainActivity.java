package ca.ualberta.cs.feelsbook.feelsbook;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Called on create of the activity, we will set it up so that tapping the buttons calls a
 * method that will add the Emotion to the list of emotions that already exist.
 */
public class MainActivity extends AppCompatActivity {

    // We need to create a name for the file that is passed to EmotionStorage
    private String storageFileName = "EmotionStorageFile";
    // Where the emotions are stored
    private EmotionStorage emotionStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * We need to override this function so this menu loads properly.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creating the menu options from the xml file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    /**
     * This is the function called when a menu item is selected in the app.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Getting the id of the menu item selected
        int id = item.getItemId();
        // Executing code depending on which item is selected
        switch (id){
            case R.id.statistics:
                // Need to show the statistics activity
                // Creating the intent to start an activity
                intent = new Intent(MainActivity.this, StatisticsActivity.class);
                // Passing in the name of the file
                intent.putExtra("fileName", this.storageFileName);
                // Starting the activity
                startActivity(intent);
                return true;
            case R.id.history:
                // Need to show the history activity
                // Creating the intent to start an activity
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                // Passing in the name of the file
                intent.putExtra("fileName", this.storageFileName);
                // Starting the activity
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Wiping out the storage class and the on click listeners when the activity goes on pause.
     */
    @Override
    protected void onPause()
    {
        super.onPause();

        // Creating an HashMap of string and button ids
        final HashMap<String, Integer> emotionsHash = new HashMap<>();
        //Adding emotions to array
        emotionsHash.put("anger", R.id.anger);
        emotionsHash.put("fear", R.id.fear);
        emotionsHash.put("joy", R.id.joy);
        emotionsHash.put("love", R.id.love);
        emotionsHash.put("sadness", R.id.sadness);
        emotionsHash.put("surprise", R.id.surprise);
        // Iterating through the list
        for (Object o : emotionsHash.entrySet()) {
            final Map.Entry pair = (Map.Entry) o;
            // Adding click listeners to buttons
            // Getting the buttons
            Button button = findViewById((Integer) pair.getValue());
            // Deleting the click listener
            button.setOnClickListener(null);
        }
        // Clearing emotion storage
        this.emotionStorage = null;
    }

    /**
     * I have decided to override this function and load everything required for the app to work properly here because Android does not make it easy
     * to pass classes between activities, so we need to load the emotion storage each time this activity becomes the main activity again and unload
     * the store when it another activity takes over.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        // Filling in values for members and reading the emotions from file
        this.emotionStorage = new EmotionStorage(getApplicationContext(), storageFileName);

        // Getting the date field
        final EditText dateText = findViewById(R.id.dateTextView);
        dateText.setText(ISO8601.now());
        // Getting the comment field
        final EditText commentText = findViewById(R.id.comment);


        // Creating an HashMap of string and button ids
        final HashMap<String, Integer> emotionsHash = new HashMap<>();
        //Adding emotions to array
        emotionsHash.put("anger", R.id.anger);
        emotionsHash.put("fear", R.id.fear);
        emotionsHash.put("joy", R.id.joy);
        emotionsHash.put("love", R.id.love);
        emotionsHash.put("sadness", R.id.sadness);
        emotionsHash.put("surprise", R.id.surprise);
        // Iterating through the list
        for (Object o : emotionsHash.entrySet()) {
            final Map.Entry pair = (Map.Entry) o;
            // Adding click listeners to buttons
            // Getting the buttons
            Button button = findViewById((Integer) pair.getValue());
            // Adding the click listener
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    boolean emotionCommentTooLongNotThrown = false;
                    // Getting the date
                    String date = dateText.getText().toString();
                    // Creating an emotion
                    Emotion newEmotion = null;
                    // Getting the right type of emotion
                    switch (pair.getKey().toString()) {
                        case "anger":
                            newEmotion = new Anger(date);
                            break;
                        case "fear":
                            newEmotion = new Fear(date);
                            break;
                        case "joy":
                            newEmotion = new Joy(date);
                            break;
                        case "love":
                            newEmotion = new Love(date);
                            break;
                        case "sadness":
                            newEmotion = new Sadness(date);
                            break;
                        case "surprise":
                            newEmotion = new Surprise(date);
                            break;
                    }

                    // Getting the comment
                    String comment = commentText.getText().toString();
                    try {
                        newEmotion.setComment(comment);
                        emotionCommentTooLongNotThrown = true;
                    } catch (EmotionCommentTooLong emotionCommentTooLong) {
                        // Telling the user the comment was too long
                        Toast.makeText(MainActivity.this, "Comment is more 100 characters long, please shorten the comment.",
                                Toast.LENGTH_SHORT).show();
                    }
                    if (emotionCommentTooLongNotThrown){
                        // Adding to emotion storage
                        try {
                            MainActivity.this.emotionStorage.addEmotion(newEmotion);
                        } catch (IOException e) {
                            // Telling the user that emotions were not saved to file.
                            Toast.makeText(MainActivity.this, "Emotion not written to file, something went wrong while trying to open file.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // Now we clear the date and comment fields
                        commentText.setText("");
                        dateText.setText(ISO8601.now());

                        // Telling the user the emotion has been created
                        Toast.makeText(MainActivity.this, (newEmotion.getEmotion() + " Emotion Logged"),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
