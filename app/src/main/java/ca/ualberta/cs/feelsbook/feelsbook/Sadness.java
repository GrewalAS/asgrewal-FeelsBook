package ca.ualberta.cs.feelsbook.feelsbook;

/**
 * Class was created to store the Sadness Emotion. Sub-class of Emotion. Only the constructors
 * are defined, nothing else needs to be defined.
 */
public class Sadness extends Emotion {
    /**
     * Constructor with date passed in. Calls super constructor that has date passed in.
     * Then sets the Emotion to Sadness.
     */
    Sadness(String date){
        super(date);
        this.setEmotion("Sadness");
    }
}
