package com.launcher.informsmanage.ApiManager;

import retrofit.RestAdapter;

/**
 * Created by chen on 14-7-24.
 */
public class MainApiManager {

    public static String path = "http://padadmin-env-ycg2e5ye9m.elasticbeanstalk.com/api";
    public static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setServer(path)
            .build();
}
