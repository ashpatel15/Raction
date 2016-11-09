package wiseowl.com.au.reaction.model;

/**
 * Created by anish.patel on 7/11/2016.
 */

public class PostModel {


    private String message;
    private int polarity;
    private double magnitude;
//    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public PostModel withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * @return The polarity
     */
    public int getPolarity() {
        return polarity;
    }

    /**
     * @param polarity The polarity
     */
    public void setPolarity(int polarity) {
        this.polarity = polarity;
    }

    public PostModel withPolarity(int polarity) {
        this.polarity = polarity;
        return this;
    }

    /**
     * @return The magnitude
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * @param magnitude The magnitude
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public PostModel withMagnitude(Double magnitude) {
        this.magnitude = magnitude;
        return this;
    }
}

