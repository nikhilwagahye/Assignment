package com.tutiondampa.models;

import com.google.gson.annotations.SerializedName;

public class OrganizationResult {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("workspace")
    private String workspace;
    @SerializedName("company_name")
    private String companyName;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
