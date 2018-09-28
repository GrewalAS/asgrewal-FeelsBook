package ca.ualberta.cs.feelsbook.feelsbook;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * We created this class so that we can successfully deserializer emotion into their subclasses when reading from file.
 */
public class EmotionDeserializer implements JsonDeserializer<Emotion> {
    @Override
    public Emotion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // First getting the JSON object
        JsonObject jsonObject = json.getAsJsonObject();
        // Not getting the types of each object
        JsonElement emotion = jsonObject.get("emotion");
        // Now we check to see if type was actually fetched
        if (emotion != null){
            // If it was fetched, we start demoralizing data properly
            switch (emotion.getAsString()){
                // Now we deal with each type of emotion
                case "Anger":
                    return context.deserialize(jsonObject, Anger.class);
                case "Fear":
                    return context.deserialize(jsonObject, Fear.class);
                case "Joy":
                    return context.deserialize(jsonObject, Joy.class);
                case "Love":
                    return context.deserialize(jsonObject, Love.class);
                case "Sadness":
                    return context.deserialize(jsonObject, Sadness.class);
                case "Surprise":
                    return context.deserialize(jsonObject, Surprise.class);
            }
        }
        return null;
    }
}
