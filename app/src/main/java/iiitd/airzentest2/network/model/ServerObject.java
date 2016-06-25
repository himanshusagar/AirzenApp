package iiitd.airzentest2.network.model;

import android.util.Log;

/**
 * Created by Himanshu Sagar on 10-06-2016.
 */
public class ServerObject
{
    private GasSpecific[] gasSpecific;

    private String token;
    private String emailId;
    private String[] inferences;

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



    public ServerObject(GasSpecific[] gasSpecific, String[] inferences)
    {
        this.gasSpecific = gasSpecific;
        this.inferences = inferences;
    }

    public GasSpecific[] getGasSpecific ()
    {
        return gasSpecific;
    }

    public void setGasSpecific (GasSpecific[] gasSpecific)
    {
        this.gasSpecific = gasSpecific;
    }

    public String[] getInferences ()
    {
        return inferences;
    }

    public void setInferences (String[] inferences)
    {
        this.inferences = inferences;
    }

    @Override
    public String toString()
    {
        Log.d("CP [gasSpecific = " ," ");

        for (int i=0 ; i < gasSpecific.length ; i++ )
        {
            gasSpecific[i].toString();
        }
        Log.d(" " , " ");

        for (int i=0 ; i < inferences.length ; i++ )
        {
            Log.d(Integer.toString(i) ,inferences[i] );
        }
        return " ";
    }
}