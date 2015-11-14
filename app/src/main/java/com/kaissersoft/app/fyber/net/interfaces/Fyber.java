package com.kaissersoft.app.fyber.net.interfaces;

import com.kaissersoft.app.fyber.net.response.OfferListResponse;
import com.kaissersoft.app.fyber.utils.Util;

import java.util.Map;


import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by eefret on 13/11/15.
 */
public interface Fyber {

    @GET("/feed/v1/offers.json")
    Call<OfferListResponse> offers(@QueryMap Map<String, String> params);
}
