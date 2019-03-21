package com.lazday.bukatokoonline;

import android.app.Application;
import android.util.Log;

import com.lazday.bukatokoonline.data.database.PrefsManager;
import com.lazday.bukatokoonline.data.database.SQLiteHelper;


public class App extends Application {

    public static PrefsManager prefsManager;
    public static SQLiteHelper sqLiteHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        prefsManager = new PrefsManager(this);
        sqLiteHelper = new SQLiteHelper(this);

        Log.e("LogBase","testing OK");
    }
}
