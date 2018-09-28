package ca.ualberta.cs.feelsbook.feelsbook;

public class Love extends Emotion {
    /**
     * Class was created to store the Love Emotion. Sub-class of Emotion. Only the constructors are
     * defined, nothing else needs to be defined.
     */

    /**
     * Constructor with date passed in. Calls super constructor that has date passed in.
     * Then sets the Emotion to Love.
     */
    Love(String date){
        super(date);
        this.setEmotion("Love");
    }
}
