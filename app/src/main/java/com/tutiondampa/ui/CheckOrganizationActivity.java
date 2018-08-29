package com.tutiondampa.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tutiondampa.NetworkUtil;
import com.tutiondampa.R;
import com.tutiondampa.models.OrganizationResult;
import com.tutiondampa.retrofit.ApiConstants;
import com.tutiondampa.retrofit.service.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOrganizationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextOrganzationName;
    private Button buttonCheck;
    private ProgressDialog progressDialogForAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_organization);

        initViews();
    }

    private void initViews() {
        editTextOrganzationName = (EditText) findViewById(R.id.editTextOrganzationName);
        buttonCheck = (Button) findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCheck:
                if (NetworkUtil.hasConnectivity(CheckOrganizationActivity.this)) {

                    try {
                        if (editTextOrganzationName.getText().toString().length() > 0) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("workspace", editTextOrganzationName.getText().toString());


                            progressDialogForAPI = new ProgressDialog(this);
                            progressDialogForAPI.setCancelable(false);
                            progressDialogForAPI.setIndeterminate(true);
                            progressDialogForAPI.setMessage("Please wait...");
                            progressDialogForAPI.show();

                            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
                            callCheckOrganizationAPI(body);
                        } else {
                            Toast.makeText(CheckOrganizationActivity.this, "Please enter organization name", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CheckOrganizationActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callCheckOrganizationAPI(RequestBody body) {
        Call<OrganizationResult> requestCallback = RestClient.getApiService(ApiConstants.CHECK_ORGANIZATION_BASE_URL).checkOrganization(body);
        requestCallback.enqueue(new Callback<OrganizationResult>() {
            @Override
            public void onResponse(Call<OrganizationResult> call, Response<OrganizationResult> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    OrganizationResult result = response.body();
                    if (result.getSuccess() == true) {

                        Intent intent = new Intent(CheckOrganizationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } else {
                    // Response code is 401

                    Toast.makeText(CheckOrganizationActivity.this, "Your organization is not found", Toast.LENGTH_SHORT).show();
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<OrganizationResult> call, Throwable t) {

                if (t != null) {

                    if (progressDialogForAPI != null) {
                        progressDialogForAPI.cancel();
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });


    }


}
