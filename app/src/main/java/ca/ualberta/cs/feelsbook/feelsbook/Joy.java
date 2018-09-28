package ca.ualberta.cs.feelsbook.feelsbook;

/**
 * Class was created to store the Joy Emotion. Sub-class of Emotion. Only the constructors are
 * defined, nothing else needs to be defined.
 */
public class Joy extends Emotion {


    /**
     * Constructor with date passed in. Calls super constructor that has date passed in.
     * Then sets the Emotion to Joy.
     */
    Joy(String date){
        super(date);
        this.setEmotion("Joy");
    }
}
