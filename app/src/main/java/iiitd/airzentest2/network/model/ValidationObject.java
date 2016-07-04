package iiitd.airzentest2.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Himanshu Sagar on 25-06-2016.
 */
public class ValidationObject
{

    @SerializedName("token")
    @Expose private String token;

    @SerializedName("emailId")
    @Expose private String emailId;

    @SerializedName("isValidated")
    @Expose private boolean isValidated;


    public ValidationObject(String token, String emailId , boolean isValidated) {
        this.emailId = emailId;
        this.token = token;
        this.isValidated = isValidated;
    }

    public boolean isValidated()
    {
        return isValidated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
