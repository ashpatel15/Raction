package wiseowl.com.au.reaction.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anipatel1 on 3/08/16.
 */
public class CandidateModel extends RealmObject {


    private String created;
    private String name;
    @PrimaryKey
    private String filePath;
    private int magnitude;
    private int polarity;
    private String sentiment;



    public CandidateModel() {}

    public CandidateModel(Builder builder) {
        this.created = builder.created;
        this.name = builder.name;
        this.filePath = builder.filePath;
        this.magnitude = builder.magnitude;
        this.polarity = builder.polarity;
        this.sentiment = builder.sentiment;

    }

    public static class Builder {
        private String created;
        private String name;
        private String filePath;
        private int magnitude;
        private int polarity;
        private String sentiment;
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

        public Builder magnitude(int magnitude) {
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
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
    }

    public int getPolarity() {
        return polarity;
    }

    public void setPolarity(int polarity) {
        this.polarity = polarity;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(final Date date) {
////        RealmController.getInstance().getRealm().beginTransaction();
//        this.date = date;
////        RealmController.getInstance().getRealm().commitTransaction();
//    }
//
//    public int getcD4Count() {
//        return cD4Count;
//    }
//
//    public void setcD4Count(final int cD4Count) {
////        RealmController.getInstance().getRealm().beginTransaction();
//        this.cD4Count = cD4Count;
////        RealmController.getInstance().getRealm().commitTransaction();
//    }
//
//    public int getcD4Percent() {
//        return cD4Percent;
//    }
//
//    public void setcD4Percent(final int cD4Percent) {
////        RealmController.getInstance().getRealm().beginTransaction();
//        this.cD4Percent = cD4Percent;
////        RealmController.getInstance().getRealm().commitTransaction();
//    }
//
//
//    public String getUniqueDate() {
//        return uniqueDate;
//    }
//
//    public void setUniqueDate(final String uniqueDate) {
////        RealmController.getInstance().getRealm().beginTransaction();
//        this.uniqueDate = uniqueDate;
//    }
//
//    public int getViralLoad() {
//        return viralLoad;
//    }
//
//    public void setViralLoad(final int viralLoad) {
////        RealmController.getInstance().getRealm().beginTransaction();
//        this.viralLoad = viralLoad;
////        RealmController.getInstance().getRealm().commitTransaction();
//    }
}
