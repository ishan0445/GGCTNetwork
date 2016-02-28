package com.fuzzybrain.ishan0445.ggctnetwork;


import android.app.Application;

import com.buddy.sdk.Buddy;
import com.quickblox.core.QBSettings;

public class GGCTNetwork extends Application {

    static final String APP_ID = "35277";
    static final String AUTH_KEY = "xE5-QhumT27uR2Z";
    static final String AUTH_SECRET = "bvmLkWCjWOZ7JYe";
    static final String ACCOUNT_KEY = "NiJzLoZPnuxMQ17mYxJv";


    @Override
    public void onCreate() {
        super.onCreate();
        Buddy.init(getApplicationContext(), "bbbbbc.MdpFcPqnNcBdc", "9dafed5a-e77f-d122-45e4-b41cfb02988e");

        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}
