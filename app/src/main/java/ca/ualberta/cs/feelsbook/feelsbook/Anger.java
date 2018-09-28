package ca.ualberta.cs.feelsbook.feelsbook;

public class Anger extends Emotion {
    /**
     * Class was created to store the Anger Emotion. Sub-class of Emotion. Only the constructors are
     * defined, nothing else needs to be defined.
     */
    // Nothing to declare

    /**
     * Constructor with date passed in. Calls super constructor that has date passed in.
     * Then sets the Emotion to Anger
     */
    Anger(String date){
        super(date);
        this.setEmotion("Anger");
    }
}
