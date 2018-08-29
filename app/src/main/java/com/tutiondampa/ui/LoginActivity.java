package com.tutiondampa.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tutiondampa.NetworkUtil;
import com.tutiondampa.R;
import com.tutiondampa.models.LoginResult;
import com.tutiondampa.models.OrganizationResult;
import com.tutiondampa.retrofit.ApiConstants;
import com.tutiondampa.retrofit.service.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmailId;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private ProgressDialog progressDialogForAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {

        editTextEmailId = (EditText) findViewById(R.id.editTextEmailId);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignIn:
                if (NetworkUtil.hasConnectivity(LoginActivity.this)) {

                    try {
                        if (editTextEmailId.getText().toString().length() > 0) {
                            if (editTextPassword.getText().toString().length() > 0) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("email", editTextEmailId.getText().toString());
                                jsonObject.put("password", editTextPassword.getText().toString());


                                progressDialogForAPI = new ProgressDialog(this);
                                progressDialogForAPI.setCancelable(false);
                                progressDialogForAPI.setIndeterminate(true);
                                progressDialogForAPI.setMessage("Please wait...");
                                progressDialogForAPI.show();

                                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
                                callLoginAPI(body);
                            } else {
                                Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Please enter email Id", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callLoginAPI(RequestBody body) {
        Call<LoginResult> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).submitLogin(body);
        requestCallback.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    LoginResult result = response.body();
                    if (result.getSuccess().getToken() != null) {
/*
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();*/
                        Toast.makeText(LoginActivity.this, " UserId:" + result.getSuccess().getUserId() + "Token::::  " + result.getSuccess().getToken() , Toast.LENGTH_SHORT).show();

                    }
                }  else {
                    // Response code is 401
                    Toast.makeText(LoginActivity.this, "Unauthorized User", Toast.LENGTH_SHORT).show();
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

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
