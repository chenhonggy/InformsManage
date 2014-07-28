package com.launcher.informsmanage.ApiManager;

import com.launcher.informsmanage.Model.InformData;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by chen on 14-7-19.
 */
public class InformApiManager extends MainApiManager {
//    private interface InformNetWorkService {
//        @GET("/informs")
//        List<InformData> getIforms();
//    }


    private static final InformAPiInterface.GetInforms informsNetwork = restAdapter.create(InformAPiInterface.GetInforms.class);
    public static Observable<List<InformData>> getInformsData() {
        return Observable.create(new Observable.OnSubscribeFunc<List<InformData>>() {
            @Override
            public Subscription onSubscribe(Observer<? super List<InformData>> observer) {
                try {
                    observer.onNext(informsNetwork.getIforms());
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final InformAPiInterface.GetInform informNetwork = restAdapter.create(InformAPiInterface.GetInform.class);

    public static Observable<InformData> getInformData(final String id) {
        return Observable.create(new Observable.OnSubscribeFunc<InformData>() {
            @Override
            public Subscription onSubscribe(Observer<? super InformData> observer) {
                try {
                    observer.onNext(informNetwork.getIform(id));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }
}
