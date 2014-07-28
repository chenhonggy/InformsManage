package com.launcher.informsmanage.ApiManager;

import com.launcher.informsmanage.Model.InformData;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by chen on 14-7-26.
 */
public class InformAPiInterface {
    public interface GetInforms {
        @GET("/informs")
        List<InformData> getIforms();
    }

    public interface GetInform {
        @GET("/informs/{id}")
        InformData getIform(@Path("id") String id);
    }

}
