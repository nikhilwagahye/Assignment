package com.tutiondampa.models;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("success")
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }


    public static class Success {

        @SerializedName("token")
        private String token;
        @SerializedName("userId")
        private Integer userId;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

    }
}
