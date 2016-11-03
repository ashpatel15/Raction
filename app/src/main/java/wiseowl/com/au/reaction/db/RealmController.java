package wiseowl.com.au.reaction.db;

import java.util.Date;

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
        if(instance == null)
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

    public <T extends RealmObject> RealmResults<T> findAllFromDate(Class<T> clazz, String datefield, Date from) {
        return realm.where(clazz).greaterThan(datefield, from).findAll();
    }

    public <T extends RealmObject> RealmResults<T> findAllFromDateAsync(Class<T> clazz, String datefield, Date from) {
        return realm.where(clazz).greaterThan(datefield, from).findAllAsync();
    }

    public <T extends RealmObject> T add(final T model) {

        realm.beginTransaction();
        T ret = realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();

        return ret;
    }

    public <T extends RealmObject> T addAsync(final T model) {
//        realm.beginTransaction();
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


    public <T extends RealmObject> T getSingle(Class<T> clazz, String id) {
        return realm.where(clazz).equalTo("created", id).findFirst();
    }

    public  <T extends RealmObject> void deleteObject(Class<T> clazz, String id) {
        realm.beginTransaction();
        realm.where(clazz).equalTo("created", id).findAll().deleteFirstFromRealm();
        realm.commitTransaction();
    }

    public  <T extends RealmObject> RealmResults<T> getListsBetweenDatesAsync(Class<T> clazz, String fieldname, Date from, Date to) {
        return realm.where(clazz).between(fieldname, from, to).findAllAsync();
    }

    public <T extends RealmObject> RealmResults<T> getListBetweenDates(Class<T> c, String datefield, Date from, Date to) {
        return realm.where(c).between(datefield, from, to).findAll();
    }

    public <T extends RealmObject> T getSingle(Class<T> clazz, String columname, String id) {
        return realm.where(clazz).equalTo(columname, id).findFirst();
    }


//
//    //-------------------- HEALTH CARE REALM --------------------------------
//
//    public RealmResults<HealthCareModel> getDoctor(String id) {
//        return realm.where(HealthCareModel.class)
//                .equalTo("created", id)
//                .findAll();
//    }
//
//
//    //-------------------- ACCOUNT REALM --------------------------------
//    public RealmResults<AccountModel> getAccountList() {
//        return realm.where(AccountModel.class).findAll();
//    }
//
//    public RealmResults<AccountModel> getAccount(String id) {
//        return realm.where(AccountModel.class)
//                .equalTo("created", id)
//                .findAll();
//    }
//
//    //-------------------- PLANNER REALM --------------------------------
//
//    public RealmResults<PlannerModel> getTodayEventList(boolean isFlagActive) {
//
//        DateTime today = new DateTime().withTimeAtStartOfDay();
//        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();
//
//        Date dtoday = today.toDate();
//        Date dTomorrow = tomorrow.toDate();
//
//        if (isFlagActive) {
//
//            RealmResults<PlannerModel> result = realm.where(PlannerModel.class)
//                    .between("eventDate", dtoday, dTomorrow)
//                    .equalTo("flagged", isFlagActive)
//                    .findAllSorted("eventDate", Sort.ASCENDING);
//
//
//            return result;
//        } else {
//
//            RealmResults<PlannerModel> result = realm.where(PlannerModel.class)
//                    .between("eventDate", dtoday, dTomorrow)
//                    .findAllSorted("eventDate", Sort.ASCENDING);
//
//            return result;
//        }
//
//    }
//
//
//    public RealmResults<PlannerModel> getTomorrowEventList(boolean isFlagActive) {
//
//        DateTime today = new DateTime().withTimeAtStartOfDay();
//        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();
//
//
//        Date dTomorrow = tomorrow.toDate();
//
//        if (isFlagActive) {
//
//            RealmResults<PlannerModel> result = realm.where(PlannerModel.class)
//                    .greaterThan("eventDate", dTomorrow)
//                    .equalTo("flagged", isFlagActive)
//                    .findAllSorted("eventDate", Sort.ASCENDING);
//
//            return result;
//        } else {
//
//            RealmResults<PlannerModel> result = realm.where(PlannerModel.class)
//                    .greaterThan("eventDate", dTomorrow)
//                    .findAllSorted("eventDate", Sort.ASCENDING);
//
//            return result;
//        }
//    }
//
//    //-------------------- QOL REALM --------------------------------
//    public RealmResults<QOLModel> getQOLList() {
//        return realm.where(QOLModel.class).findAll();
//    }
//
//    public RealmResults<QOLModel> getQOL(String id) {
//        return realm.where(QOLModel.class)
//                .equalTo("created", id)
//                .findAll();
//    }
//
//    public RealmResults<QOLModel> getQOLListInRange(Date from, Date to) {
//        return realm.where(QOLModel.class)
//                .between("date", from, to)
//                .findAll();
//    }
//
//
//    //-------------------- BLOOD REALM --------------------------------
//    public RealmResults<BloodModel> getBloodListInRange(final Date from, final Date to) {
//        return realm.where(BloodModel.class)
//                .between("date", from, to)
//                .findAllSorted("date");
////                .findAll();
//    }
}
