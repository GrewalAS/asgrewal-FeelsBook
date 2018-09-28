package ca.ualberta.cs.feelsbook.feelsbook;

/**
 * Class was created to store the Fear Emotion. Sub-class of Emotion. Only the constructors are
 * defined, nothing else needs to be defined.
 */
public class Fear extends Emotion {
    /**
     * Constructor with date passed in. Calls super constructor that has date passed in.
     * Then sets the Emotion to Fear
     */
    Fear(String date){
        super(date);
        this.setEmotion("Fear");
    }
}
