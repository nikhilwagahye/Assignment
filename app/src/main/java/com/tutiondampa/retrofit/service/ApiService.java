package com.tutiondampa.retrofit.service;

import com.tutiondampa.models.LoginResult;
import com.tutiondampa.models.OrganizationResult;
import com.tutiondampa.retrofit.ApiConstants;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // Check Organization API - POST
    @POST(ApiConstants.CHECK_ORGANIZATION_API)
    Call<OrganizationResult> checkOrganization(@Body RequestBody body);


    // Login API - POST
    @POST(ApiConstants.LOGIN_API)
    Call<LoginResult> submitLogin(@Body RequestBody body);


}
