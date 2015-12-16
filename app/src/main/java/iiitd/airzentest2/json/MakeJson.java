package iiitd.airzentest2.json;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class MakeJson {
    public static JSONObject wrapPreferences(int yearOfBirth, Set defects, String deviceID){
        JSONObject data = new JSONObject();
        String defectsArray = "";
        if(defects != null) {
            defectsArray = new Gson().toJson(new ArrayList<String>(defects));
        }
        try {
            data.put("age", yearOfBirth);
            data.put("defectPreferences", defectsArray);
            data.put("deviceID", deviceID);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    public static JSONObject wrapRegistration(String email, String deviceId, String passkey){
        JSONObject data = new JSONObject();
/*        String defectsArray = "";
        if(defects != null) {
            defectsArray = new Gson().toJson(new ArrayList<String>(defects));
        }*/
        try {
            data.put("email", email);
            data.put("deviceId", deviceId);
            data.put("passkey", passkey);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }
}
