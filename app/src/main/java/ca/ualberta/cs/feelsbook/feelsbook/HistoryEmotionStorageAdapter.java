package ca.ualberta.cs.feelsbook.feelsbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class was created so that we could hook up our emotion storage class to an adapter to view the history of an emotion
 */

public class HistoryEmotionStorageAdapter extends BaseAdapter {
    // Creating a private variable to store emotion storage
    private EmotionStorage emotionStorage;

    /**
     * We had to write a custom constructor for this because we need to store the emotion storage in a member.
     */
    public HistoryEmotionStorageAdapter(EmotionStorage newEmotionStorage) {
        this.emotionStorage = newEmotionStorage;
    }

    /**
     * Fetches the emotions array in the
     */
    @Override
    public int getCount() {
        return this.emotionStorage.getSizeOfEmotionsArray();
    }

    @Override
    public Object getItem(int position) {
        return this.emotionStorage.getEmotionAtPosition(position);
    }

    /**
     * We do not associate ids with the emotions, so no need to implement this. This is here to shut the compiler up.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Gives a view back that is populated by the information that we need to display.
     * Influenced by: https://stackoverflow.com/a/31752281 by user named Zanna
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_history_listview_item, parent, false);
        } else {
            result = convertView;
        }

        Emotion emotion = this.emotionStorage.getEmotionAtPosition(position);

        ((TextView) result.findViewById(R.id.emotion_name)).setText(emotion.getEmotion());
        ((TextView) result.findViewById(R.id.emotion_date)).setText(emotion.getDate());

        return result;
    }
}
