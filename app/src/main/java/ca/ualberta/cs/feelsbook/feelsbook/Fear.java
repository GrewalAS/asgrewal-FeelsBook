package ca.ualberta.cs.feelsbook.feelsbook;

public class Fear extends Emotion {
    /**
     * Class was created to store the Fear Emotion. Sub-class of Emotion. Only the constructors are
     * defined, nothing else needs to be defined.
     */
    Fear(){
        /**
         * Constructor with no date passed in. Calls super constructor that has no date passed in.
         * Then sets the Emotion to Fear
         */
        super();
        this.setEmotion("Fear");
    }

    Fear(String date){
        /**
         * Constructor with date passed in. Calls super constructor that has date passed in.
         * Then sets the Emotion to Fear
         */
        super(date);
        this.setEmotion("Fear");
    }
}
