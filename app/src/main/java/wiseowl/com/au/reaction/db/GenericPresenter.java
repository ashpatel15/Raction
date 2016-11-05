package wiseowl.com.au.reaction.db;

import io.realm.RealmObject;
import io.realm.RealmResults;


public class GenericPresenter {

    RealmController realmController;

    public GenericPresenter() {
        realmController = RealmController.getInstance();
    }

    public <T extends RealmObject> T add(T model) {
        return realmController.add(model);
    }

    public <T extends RealmObject> T addAsync(T model) {
        return realmController.addAsync(model);
    }


    public <T extends RealmObject> void delete(T model) {
        realmController.getRealm().beginTransaction();
        model.deleteFromRealm();
        realmController.getRealm().commitTransaction();
    }

    public <T extends RealmObject> void delete(Class<T> c, String id) {
        realmController.deleteObject(c,id);
    }

    public <T extends RealmObject> RealmResults<T> getList(Class<T> c) {
        return realmController.findAll(c);
    }
    public <T extends RealmObject> RealmResults<T> getListAsync(Class<T> c) {
        return realmController.findAllAsync(c);
    }

    public <T extends RealmObject> T getSingle(Class<T> c, String id) {
        return realmController.getSingle(c, id);
    }

    public <T extends RealmObject> T getSingle(Class<T> c, String field, String id) {
        return realmController.getSingle(c, field, id);
    }

//    public <T extends RealmObject> RealmResults<T> getListBetweenDates(Class<T> c, String datefield, Date from, Date to) {
//        return realmController.getListBetweenDates(c, datefield, from, to);
//    }
//
//    public <T extends RealmObject> RealmResults<T> getListBetweenDatesAsync(Class<T> c, String datefield, Date from, Date to) {
//        return realmController.getListsBetweenDatesAsync(c, datefield, from, to);
//    }
//
//    public <T extends RealmObject> RealmResults<T> getListFromDate(Class<T> c, String datefield, Date from) {
//        return realmController.findAllFromDate(c, datefield, from);
//    }

    public <T extends RealmObject> void deleteAll(Class<T> c) {
        realmController.getRealm().beginTransaction();
        realmController.findAll(c).deleteAllFromRealm();
        realmController.getRealm().commitTransaction();
    }
}
