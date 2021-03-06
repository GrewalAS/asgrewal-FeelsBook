package ca.ualberta.cs.feelsbook.feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * This activity was created to load the Emotions deatails from the list and to let the user edit past emotion/delete them as well.
 */

public class EmotionsDetailAndEditActivity extends AppCompatActivity {
    // Need to store the emotions
    private EmotionStorage emotionStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotions_detail_and_edit);
    }

    /**
     * Loading the emotions here
     */
    @Override
    protected void onResume(){
        super.onResume();
        // Getting the intent and the file we need to read to fill emotion storage
        Intent intent = getIntent();
        // Getting the file name
        String fileName = intent.getExtras().getString("fileName");
        // Getting the position of the element to load
        final int position = intent.getExtras().getInt("position");
        // Getting the emotions
        this.emotionStorage = new EmotionStorage(getApplicationContext(), fileName);
        // Getting the emotion to load
        Emotion emotion = emotionStorage.getEmotionAtPosition(position);
        // Setting the data to the views
        // Emotion
        TextView titleView = findViewById(R.id.emotionDetails);
        titleView.setText(titleView.getText() + ": " + emotion.getEmotion());
        // Date
        final TextView dateView = findViewById(R.id.emotionDetailsDateTextView);
        dateView.setText(emotion.getDate());
        // Comment
        final TextView commentView = findViewById(R.id.emotionDetailsComment);
        commentView.setText(emotion.getComment());

        // Now setting up the buttons to perform actions
        // Save
        Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean emotionCommentTooLongNotThrown = false;
                // Getting the emotion
                Emotion emotion = emotionStorage.getEmotionAtPosition(position);
                // Saving the emotion data for the new data
                try {
                    emotion.setComment(commentView.getText().toString());
                    emotionCommentTooLongNotThrown = true;
                } catch (EmotionCommentTooLong emotionCommentTooLong) {
                    // Telling the user the comment was too long
                    Toast.makeText(EmotionsDetailAndEditActivity.this, "Comment is more 100 characters long, please shorten the comment.",
                            Toast.LENGTH_SHORT).show();
                }
                if(emotionCommentTooLongNotThrown){
                    emotion.setDate(dateView.getText().toString());
                    // Telling the Emotion Storage to write data to file again.
                    try {
                        emotionStorage.writeEmotionsToFile();
                    } catch (IOException e) {
                        // Telling the user that emotions were not saved to file.
                        Toast.makeText(EmotionsDetailAndEditActivity.this, "Emotion not written to file, something went wrong while trying to open file.",
                                Toast.LENGTH_SHORT).show();
                    }
                    // Cleaning up the activity
                    EmotionsDetailAndEditActivity.this.finish();
                }
            }
        });

        // Delete
        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Deleting the emotion
                try {
                    emotionStorage.deleteEmotionAtPosition(position);
                } catch (IOException e) {
                    // Telling the user that emotions were not saved to file.
                    Toast.makeText(EmotionsDetailAndEditActivity.this, "Emotion not written to file, something went wrong while trying to open file.",
                            Toast.LENGTH_SHORT).show();
                }
                // Telling the Emotion Storage to write data to file again.
                try {
                    emotionStorage.writeEmotionsToFile();
                } catch (IOException e) {
                    // Telling the user that emotions were not saved to file.
                    Toast.makeText(EmotionsDetailAndEditActivity.this, "Emotion not written to file, something went wrong while trying to open file.",
                            Toast.LENGTH_SHORT).show();
                }
                // Cleaning up the activity
                EmotionsDetailAndEditActivity.this.finish();
            }
        });
    }

    /**
     * Destroying the instance of the emotions storage
     */
    @Override
    protected void onPause(){
        super.onPause();
        // Setting the emotionStorage to null. New instance is created onResume
        this.emotionStorage = null;
        //
    }
}
