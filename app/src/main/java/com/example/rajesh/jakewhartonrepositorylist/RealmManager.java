package com.example.rajesh.jakewhartonrepositorylist;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmManager {
    private static final String TAG = "RealmManager";

    private static Realm realm;

    private static RealmConfiguration realmConfiguration;

    public static void initializeRealmConfig() {
        if(realmConfiguration == null) {
            Log.d(TAG, "Initializing Realm configuration.");
            setRealmConfiguration(new RealmConfiguration.Builder() //
                                          .initialData(new RealmInitialData())
                                          .deleteRealmIfMigrationNeeded()
                                          .build());
        }
    }

    public static void setRealmConfiguration(RealmConfiguration realmConfiguration) {
        RealmManager.realmConfiguration = realmConfiguration;
        Realm.setDefaultConfiguration(realmConfiguration);
    }


    public static Realm getRealm() {
        if(realm != null)
        {

        }
        else
        {
            realm = Realm.getDefaultInstance();
        }

        return realm;
    }

}
