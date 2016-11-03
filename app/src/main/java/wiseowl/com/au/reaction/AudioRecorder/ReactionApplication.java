package wiseowl.com.au.reaction.AudioRecorder;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by anish.patel on 1/11/2016.
 */

public class ReactionApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Configure Realm for the application
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("rectDB")
                .build();
//        Realm.deleteRealm(realmConfiguration); // Clean slate
        Realm.setDefaultConfiguration(realmConfiguration); // Make this Realm the default

    }
}
