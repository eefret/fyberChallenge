package com.kaissersoft.app.fyber.net;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.kaissersoft.app.fyber.activities.MainActivity_;
import com.kaissersoft.app.fyber.net.interfaces.Fyber;
import com.kaissersoft.app.fyber.net.response.OfferListResponse;
import com.kaissersoft.app.fyber.utils.Util;
import com.orhanobut.logger.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;


import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by eefret on 14/11/15.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class FyberRequestTests {
    @Rule
    public ActivityTestRule<MainActivity_> mActivityRule = new ActivityTestRule<>(MainActivity_.class);

    @Test
    public void OfferListRequest(){
        Logger.init();


        String apiKey = "1c915e3b5d42d05136185030892fbb846c278927";

        TreeMap<String, String> params = new TreeMap<>();
        params.put(Util.RequestParams.FORMAT, "json");
        params.put(Util.RequestParams.APP_ID, "2070");
        params.put(Util.RequestParams.UID, "spiderman");
        params.put(Util.RequestParams.OFFER_TYPES,"112");
        params.put(Util.RequestParams.LOCALE, "de");
        params.put(Util.RequestParams.OS_VERSION, Util.getAndroidOSVersion());
        params.put(Util.RequestParams.IP, "109.235.143.113");
        params.put(Util.RequestParams.TIMESTAMP, String.valueOf(new Date().getTime()));
        params.put(Util.RequestParams.GOOGLE_AD_ID, Util.getAdvertisingIdClient(mActivityRule.getActivity()));
        params.put(Util.RequestParams.GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED, String.valueOf(Util.getLimitAdTrackingEnabled(mActivityRule.getActivity())));

        String hashKey = Util.generateHashKey(params, apiKey);
        params.put(Util.RequestParams.HASH_KEY, hashKey);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Fyber f = retrofit.create(Fyber.class);

        try {
            Response<OfferListResponse> response = f.offers(params).execute();
            Logger.d(response.raw().request().httpUrl().toString());
            Logger.d(response.isSuccess()+"");
            Logger.d(response.errorBody().string());
            Logger.d(response.code()+"");
            Logger.d(response.body().pages+"");

            Logger.d(response.body().code+"");
            Logger.d(response.body().message);;

            assertTrue(response.isSuccess());
            assertEquals(200, response.code());
            assertEquals(hashKey, response.headers().get(Util.HASHKEY_HEADER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
