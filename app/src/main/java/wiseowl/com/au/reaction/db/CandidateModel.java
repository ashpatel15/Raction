package wiseowl.com.au.reaction.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anipatel1 on 3/11/16.
 */
public class CandidateModel extends RealmObject {

    @PrimaryKey
    private String filePath;
    private String created;
    private String name;
    private double magnitude;
    private int polarity;
    private String sentiment;
    private String rating;


    public CandidateModel() {
    }

    public CandidateModel(Builder builder) {
        this.created = builder.created;
        this.name = builder.name;
        this.filePath = builder.filePath;
        this.magnitude = builder.magnitude;
        this.polarity = builder.polarity;
        this.sentiment = builder.sentiment;
        this.rating = builder.rating;

    }

    public static class Builder {
        private String created;
        private String name;
        private String filePath;
        private double magnitude;
        private int polarity;
        private String sentiment;
        private String rating;

        public Builder() {
            Long tsLong = System.currentTimeMillis() / 1000;

            this.created = tsLong.toString();
        }

        public Builder created(String created) {
            this.created = created;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder magnitude(double magnitude) {
            this.magnitude = magnitude;
            return this;
        }

        public Builder polarity(int polarity) {
            this.polarity = polarity;
            return this;
        }

        public Builder sentiment(String sentiment) {
            this.sentiment = sentiment;
            return this;
        }

        public Builder rating(String rating) {
            this.rating = rating;
            return this;
        }


        public CandidateModel build() {
            return new CandidateModel(this);
        }


    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(final String created) {
//        RealmController.getInstance().getRealm().beginTransaction();
        this.created = created;
//        RealmController.getInstance().getRealm().commitTransaction();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        RealmController.getInstance().getRealm().beginTransaction();
        this.name = name;
        RealmController.getInstance().getRealm().commitTransaction();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        RealmController.getInstance().getRealm().beginTransaction();
        this.filePath = filePath;
        RealmController.getInstance().getRealm().commitTransaction();
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        RealmController.getInstance().getRealm().beginTransaction();
        this.magnitude = magnitude;
        RealmController.getInstance().getRealm().commitTransaction();
    }

    public int getPolarity() {
        return polarity;
    }

    public void setPolarity(int polarity) {
        RealmController.getInstance().getRealm().beginTransaction();
        this.polarity = polarity;
        RealmController.getInstance().getRealm().commitTransaction();
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        RealmController.getInstance().getRealm().beginTransaction();
        this.sentiment = sentiment;
        RealmController.getInstance().getRealm().commitTransaction();
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        RealmController.getInstance().getRealm().beginTransaction();
        this.rating = rating;
        RealmController.getInstance().getRealm().commitTransaction();
    }


}
