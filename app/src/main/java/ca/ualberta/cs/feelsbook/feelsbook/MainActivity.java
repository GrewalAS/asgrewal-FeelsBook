package ca.ualberta.cs.feelsbook.feelsbook;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /**
     * Called on create of the activity, we will set it up so that tapping the buttons calls a
     * method that will add the Emotion to the list of emotions that already exist.
     */

    // We need to create a name for the file that is passed to EmotionStorage
    private String storageFileName = "EmotionStorage";
    // Where the emotions are stored
    public EmotionStorage emotionStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    newEmotion.setComment(comment);
                    // Adding to emotion storage
                    MainActivity.this.emotionStorage.addEmotion(newEmotion);

                    // Now we clear the date and comment fields
                    commentText.setText("");
                    dateText.setText(ISO8601.now());

                    // Telling the user the emotion has been created
                    Toast.makeText(MainActivity.this, (newEmotion.getEmotion() + " Emotion Logged"),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                // Starting the activity
                startActivity(intent);
                return true;
            case R.id.history:
                // Need to show the history activity
                // Creating the intent to start an activity
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                // Starting the activity
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
