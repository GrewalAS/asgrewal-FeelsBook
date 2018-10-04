package ca.ualberta.cs.feelsbook.feelsbook;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * This class was created to store all the emotions for the app, this is a data store. It will
 * store the emotions as an ArrayList. It will add new emotions to the end of the array list.
 * It will delete emotions from the array list. It will also sort the emotions from oldest to
 * newest when new emotions are inserted, older ones are deleted or when existing emotions are
 * edited. We can just use the string of the time stamp to sort due to its format.
 */
public class EmotionStorage {
    // Doesn't need to be accessed outside the class, so private.
    private ArrayList<Emotion> emotions;
    // This string does not need to be accessed by anyone else, so we keep it private
    private String fileName;
    private Context context;
    private HashMap<String, Integer> countsOfEmotions;

    /**
     * This method will create an array after a file is read.
     */
    EmotionStorage(Context context, String fileName){
        // Setting the members
        this.fileName = fileName;
        this.context = context;
        emotions = new ArrayList<>();
        countsOfEmotions = new HashMap<>();
        // Reading from the file
        this.readEmotionsFromFile();
        // Updating the emotion count after the emotion have been read from file
        this.updateEmotionCount();
    }

    /**
     * This method will write the emotions array to a file. It will do a rewrite of the whole file
     * because we do not want to figure out which of the Emotion in the file were deleted.
     *
     * This method is also public since it might need to be called after an Emotion is updated but
     * not the List stays the same.
     *
     * This function was heavily influenced by a similar function in the LonelyTwitter app used
     * to demonstrate concepts in class.
     */
    public void writeEmotionsToFile() throws IOException {
        try {
            // Before anything, we need to sort the list of emotions by date
            this.sortEmotionsByDate();
            // Getting the stream which will be used to write to file
            FileOutputStream fos = this.context.openFileOutput(this.fileName, MODE_PRIVATE);

            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(this.emotions, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * This methods reads emotions from a file and stores them in an array.
     *
     * This function was heavily influenced by a similar function in the LonelyTwitter app used
     * to demonstrate concepts in class.
     */
    private void readEmotionsFromFile(){
        try {
            // Getting ready to read from file
            FileInputStream fis = this.context.openFileInput(this.fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // We need to assign a custom reader for Gson
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Emotion.class, new EmotionDeserializer())
                    .create();
            Type listType = new TypeToken<ArrayList<Emotion>>(){}.getType();
            ArrayList<Emotion> intermediate = gson.fromJson(in, listType);
            if (intermediate != null) {
                this.emotions = intermediate;
            }

        } catch (FileNotFoundException e) {
            emotions = new ArrayList<>();
        }
    }

    /**
     * Updates the emotion count after a new emotion has been added or deleted.
     */
    private void updateEmotionCount(){
        // We need to wipe the counts of emotions
        this.countsOfEmotions = new HashMap<>();
        // Init the counts of emotions
        countsOfEmotions.put("Anger", 0);
        countsOfEmotions.put("Fear", 0);
        countsOfEmotions.put("Joy", 0);
        countsOfEmotions.put("Love", 0);
        countsOfEmotions.put("Sadness", 0);
        countsOfEmotions.put("Surprise", 0);
        // Iterating through the list of emotions
        for (int i = 0; i < this.emotions.size(); i++) {
            // This will get the type of the emotion
            String type = this.emotions.get(i).getEmotion();
            int count = this.countsOfEmotions.containsKey(type) ? this.countsOfEmotions.get(type) : 0;
            this.countsOfEmotions.put(type, count + 1);
        }
    }

    /**
     * Adds a new Emotion the emotions array
     */
    public void addEmotion(Emotion newEmotion) throws IOException {
        // Adding to array
        this.emotions.add(newEmotion);
        // Writing to file after update
        this.writeEmotionsToFile();
        // Now we need to update the emotion count
        this.updateEmotionCount();
    }

    /**
     * Deletes the Emotion at a given index number
     */
    public void deleteEmotionAtPosition(int position) throws IOException {
        emotions.remove(position);
        // Writing to file after update
        this.writeEmotionsToFile();
        // Now we need to update the emotion count
        this.updateEmotionCount();
    }

    /**
     * Return the number of emotions that have been logged by the user.
     */
    public Integer getSizeOfEmotionsArray(){
        return this.emotions.size();
    }

    /**
     * Returns the emotion at a particular place in an array
     */
    public Emotion getEmotionAtPosition(int position){
        return this.emotions.get(position);
    }

    /**
     * Returns the count for an emotion
     */
    public HashMap<String, Integer> getCountForAllEmotion() { return this.countsOfEmotions; }

    /**
     * This method will sort the emotions stored in this class using their date. It will be called
     * before we call writeEmotionsToFile.
     * Fetched from https://stackoverflow.com/questions/12542185/sort-a-java-collection-object-based-on-one-field-in-it
     */
    private void sortEmotionsByDate(){
        Collections.sort(this.emotions, new Comparator<Emotion>() {
            @Override
            public int compare(Emotion o1, Emotion o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }
}
