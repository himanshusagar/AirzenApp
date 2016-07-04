package iiitd.airzentest2.network.model;

/**
 * Created by Himanshu Sagar on 23-06-2016.
 */
public class RegisterObject
{

    private boolean pairFound ;
    private String token ;
    private boolean response ;
    private String status ;
    private String deviceId;
    private String passKey;

    private String email;
    public boolean isPairFound() {
        return pairFound;
    }

    public void setPairFound(boolean pairFound) {
        this.pairFound = pairFound;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassKey()
    {
        return passKey;
    }


    public RegisterObject( String deviceId , String passKey ,String email) {
        this.passKey = passKey;
        this.email  = email;
        this.deviceId = deviceId;

    }
    @Override
    public String toString()
    {
        String xx = String.valueOf(this.isPairFound() ) +String.valueOf(this.token) + String.valueOf(this.response) + String.valueOf(this.status);
        return xx;
    }
}
