package com.kaissersoft.app.fyber.activities;

import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaissersoft.app.fyber.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @ViewById(R.id.tv_uid)
    public EditText tvUid;

    @ViewById(R.id.tv_api_key)
    public EditText tvApiKey;

    @ViewById(R.id.tv_app_id)
    public EditText tvAppId;

    @ViewById(R.id.tv_pub0)
    public EditText tvPub0;

    @ViewById(R.id.defaults)
    public Button btnDefaults;

    private View firstErrorView = null;

    @Click(R.id.defaults)
    public void setdefault(){
        tvPub0.setText("idkwhattoputhere");
        tvAppId.setText("2070");
        tvApiKey.setText("e95a21621a1865bcbae3bee89c4d4f84");
        tvUid.setText("spiderman");
    }

    @UiThread
    public void cleanParams() {
        tvPub0.setText("");
        tvAppId.setText("");
        tvApiKey.setText("");
        tvUid.setText("");
        firstErrorView = null;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.a_main, menu);

        return super.onPrepareOptionsMenu(menu);
    }

    @UiThread
    public boolean validateFields() {
        boolean passed = true;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.a_main_field_err_msg_pre));
        if (tvUid.getText().toString().trim().length() <= 0) {
            sb.append(tvUid.getHint());
            sb.append(", ");
            if (firstErrorView == null) {
                firstErrorView = tvUid;
            }
            passed = false;
        }
        if (tvApiKey.getText().toString().trim().length() <= 0) {
            sb.append(tvApiKey.getHint());
            sb.append(", ");
            if (firstErrorView == null) {
                firstErrorView = tvApiKey;
            }
            passed = false;
        }
        if (tvAppId.getText().toString().trim().length() <= 0) {
            sb.append(tvAppId.getHint());
            sb.append(", ");
            if (firstErrorView == null) {
                firstErrorView = tvAppId;
            }
            passed = false;
        }
        if (tvPub0.getText().toString().trim().length() <= 0) {
            sb.append(tvPub0.getHint());
            sb.append(", ");
            if (firstErrorView == null) {
                firstErrorView = tvPub0;
            }
            passed = false;
        }
        if (!passed) {
            String errorTest = sb.toString().substring(0, sb.toString().length() - 2);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, errorTest, Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstErrorView.requestFocus();
                }
            });
            snackbar.show();
        }
        return passed;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_send:
                if (validateFields()) {
                    OffersListActivity_.intent(this)
                            .apiKey(tvApiKey.getText().toString())
                            .appID(tvAppId.getText().toString())
                            .uid(tvUid.getText().toString())
                            .pub0(tvPub0.getText().toString())
                            .format("json")
                            .locale("de")
                            .startForResult(OffersListActivity.REQUEST_CODE);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OffersListActivity.REQUEST_CODE:
                if (resultCode != RESULT_OK) {
                    cleanParams();
                    tvUid.requestFocus();
                }
                break;
        }
    }
}
