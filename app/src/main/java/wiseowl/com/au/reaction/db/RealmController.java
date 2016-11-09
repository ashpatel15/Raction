package wiseowl.com.au.reaction.db;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    private RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController getInstance() {
        if (instance == null)
            instance = new RealmController();

        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //-------------------- GENERIC REALM --------------------------------
    public <T extends RealmObject> RealmResults<T> findAll(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    public <T extends RealmObject> RealmResults<T> findAllAsync(Class<T> clazz) {
        return realm.where(clazz).findAllAsync();
    }

    public <T extends RealmObject> T add(final T model) {

        realm.beginTransaction();
        T ret = realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();

        return ret;
    }

    public <T extends RealmObject> T addAsync(final T model) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(final Realm realm) {
                realm.copyToRealmOrUpdate(model);
            }
        });

        return model;
    }

    public void clearDatabase() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }


    public <T extends RealmObject> void deleteObject(Class<T> clazz, String id) {
        realm.beginTransaction();
        realm.where(clazz).equalTo("created", id).findAll().deleteFirstFromRealm();
        realm.commitTransaction();
    }


    public <T extends RealmObject> T getSingle(Class<T> clazz, String columname, String id) {
        return realm.where(clazz).equalTo(columname, id).findFirst();
    }
}
