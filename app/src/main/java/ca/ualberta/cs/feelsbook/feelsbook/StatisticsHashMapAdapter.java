/**
 * https://stackoverflow.com/questions/19466757/hashmap-to-listview
 */
package ca.ualberta.cs.feelsbook.feelsbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class was fetched from https://stackoverflow.com/questions/19466757/hashmap-to-listview.
 * It will act as the adapter between the HashMap of stats and the list used to display the stats.
 */

public class StatisticsHashMapAdapter extends BaseAdapter {
    private final ArrayList mData;

    public StatisticsHashMapAdapter(Map<String, Integer> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Integer> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_stat_listview_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, Integer> item = getItem(position);

        ((TextView) result.findViewById(R.id.emotion_item_text)).setText(item.getKey());
        ((TextView) result.findViewById(R.id.emotion_count_item_text)).setText(item.getValue().toString());

        return result;
    }
}