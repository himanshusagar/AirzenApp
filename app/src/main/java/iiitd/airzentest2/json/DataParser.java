package iiitd.airzentest2.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import iiitd.airzentest2.db.DbSingleton;
import iiitd.airzentest2.network.model.ServerObject;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class DataParser
{
    //Unused
    public static void parseJsonStr(String jsonStr) throws JSONException
    {
        JSONObject mainObj = new JSONObject(jsonStr);
        String gasSpecific = mainObj.getString("gasSpecific");
        Log.d("Gas Specific", gasSpecific);
        JSONArray gases = new JSONArray(gasSpecific);

        DbSingleton db=DbSingleton.getInstance();

        db.create24HourTable(gases);
        db.createWeekTable(gases);
        db.createMonthTable(gases);
        db.createYearTable(gases);
        db.createAQITable(gases);
        //SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase("AirPure.db",MODE_PRIVATE,null);
    }


    public static void initialiseTables(ServerObject serverObject)
    {



        DbSingleton db=DbSingleton.getInstance();

        db.generate24HourTable(serverObject);
        db.generateWeekTable(serverObject);
        db.generateMonthTable(serverObject);
        db.generateYearTable(serverObject);
        db.generateAQITable(serverObject);
        db.updateTimeStamp();
    }
}
