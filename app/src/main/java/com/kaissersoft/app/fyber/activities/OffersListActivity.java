package com.kaissersoft.app.fyber.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kaissersoft.app.fyber.R;
import com.kaissersoft.app.fyber.net.interfaces.Fyber;
import com.kaissersoft.app.fyber.net.response.OfferListResponse;
import com.kaissersoft.app.fyber.ui.OffersAdapter;
import com.kaissersoft.app.fyber.utils.Util;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.TreeMap;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by eefret on 11/11/15.
 */
@EActivity(R.layout.activity_offers_list)
public class OffersListActivity extends AppCompatActivity {

    //EXTRAS

    @Extra
    String format = "json";

    @Extra
    String locale = "de";

    @Extra
    String offerTypes = "112";

    @Extra
    String appID;

    @Extra
    String uid;

    @Extra
    String apiKey;

    @Extra
    String pub0;

    //VIEWS

    @ViewById(R.id.pb)
    ProgressBar pbLoading;

    @ViewById(R.id.rv)
    RecyclerView recyclerView;

    @ViewById(R.id.tv_empty)
    TextView tvEmpty;

    //Fields
    private LinearLayoutManager mLlm;
    private OffersAdapter mAdapter;
    public static final int REQUEST_CODE = 13543;

    @AfterViews
    public void afterViews() {
        //Views
        tvEmpty.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
        pbLoading.setIndeterminate(true);

        mLlm = new LinearLayoutManager(this);
        mLlm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLlm);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //NETWORK
        fetchParams();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Background
    public void fetchParams() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put(Util.RequestParams.FORMAT, format);
        params.put(Util.RequestParams.APP_ID, appID);
        params.put(Util.RequestParams.UID, uid);
        params.put(Util.RequestParams.OFFER_TYPES, offerTypes);
        params.put(Util.RequestParams.LOCALE, locale);
        params.put(Util.RequestParams.OS_VERSION, Util.getAndroidOSVersion());
        params.put(Util.RequestParams.IP, Util.getIPAddress(true));
        params.put(Util.RequestParams.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        params.put(Util.RequestParams.GOOGLE_AD_ID, Util.getAdvertisingIdClient(this));
        params.put(Util.RequestParams.GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED, String.valueOf(Util.getLimitAdTrackingEnabled(this)));

        String hashKey = Util.generateHashKey(params, apiKey);
        params.put(Util.RequestParams.HASH_KEY, hashKey);
        callRequest(params, hashKey);
    }

    @UiThread
    public void callRequest(TreeMap<String, String> params, String hashKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.server_domain))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Fyber f = retrofit.create(Fyber.class);

        f.offers(params).enqueue(new OfferListRequestListener(hashKey));
    }

    private class OfferListRequestListener implements Callback<OfferListResponse> {
        String hashKey;

        public OfferListRequestListener(String hashKey) {
            this.hashKey = hashKey;
        }

        @Override
        public void onResponse(Response<OfferListResponse> response, Retrofit retrofit) {
            pbLoading.setVisibility(View.GONE);
            String hashKeyConfirm = response.headers().get(Util.HASHKEY_HEADER);
            if (this.hashKey.equals(hashKeyConfirm) && response.isSuccess()) { //Hash key validation
                if (response.body().offers.size() == 0) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                if (mAdapter == null) {
                    mAdapter = new OffersAdapter(getBaseContext(), response.body().offers);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.addOffers(response.body().offers);
                }
                mAdapter.notifyDataSetChanged();
                setResult(RESULT_OK);
            } else {
                tvEmpty.setVisibility(View.VISIBLE);
                setResult(RESULT_CANCELED);
                Logger.e("bad request");
            }
        }

        @Override
        public void onFailure(Throwable t) {
            pbLoading.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            Logger.e(t, "Error while requesting data");
            setResult(RESULT_CANCELED);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
