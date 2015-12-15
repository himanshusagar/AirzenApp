package iiitd.airzentest2;

import android.app.Application;

import iiitd.airzentest2.db.DbSingleton;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DbSingleton.init(this);
    }
}
