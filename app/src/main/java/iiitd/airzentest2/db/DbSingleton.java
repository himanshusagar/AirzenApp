package iiitd.airzentest2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import iiitd.airzentest2.network.model.ServerObject;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class DbSingleton {
    private static DbSingleton mInstance;
    private Context mContext;
    private static DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    private DbSingleton(Context context){
        mContext = context;
        mDbHelper = new DbHelper(mContext);
    }

    public DbSingleton(SQLiteDatabase mDb, Context context, DbHelper helper) {
        this.mDb = mDb;
        mContext = context;
        mDbHelper = helper;
    }

    public static void init(Context context) {
        if (mInstance == null) {
            mInstance = new DbSingleton(context);
            Log.d("db instance created", "xxxxxxxxxxxxxxxxx");
        }
        else{
            Log.d("Database Present","sasasasasasas");
        }
    }

    public static DbSingleton getInstance() {
        return mInstance;
    }

    private void getReadOnlyDatabase() {
        if ((mDb == null) || (!mDb.isReadOnly())) {
            mDb = mDbHelper.getReadableDatabase();
        }
    }

    private void getWritableDatabase(){
        mDb=mDbHelper.getWritableDatabase();

    }

    public ArrayList<Integer> getPast24Hour(String gas){
        ArrayList<Integer> val=new ArrayList<Integer>();
        getReadOnlyDatabase();
        Cursor cur = mDb.rawQuery("SELECT "
                + gas
                + " FROM "
                + DbContract.Past24Hours.TABLE_NAME,null);

        cur.moveToFirst();
        while(!cur.isAfterLast()){
            val.add(cur.getInt(cur.getColumnIndex(gas)));
            cur.moveToNext();
        }
        cur.close();

        return val;
    }
    public ArrayList<Integer> getPastWeek(String gas){
        ArrayList<Integer> val=new ArrayList<Integer>();
        getReadOnlyDatabase();
        Cursor cur = mDb.rawQuery("SELECT "
                + gas
                + " FROM "
                + DbContract.PastWeek.TABLE_NAME,null);

        cur.moveToFirst();
        while(!cur.isAfterLast()){
            val.add(cur.getInt(cur.getColumnIndex(gas)));
            cur.moveToNext();
        }
        cur.close();
        return val;
    }
    public ArrayList<Integer> getPastMonth(String gas){
        ArrayList<Integer> val=new ArrayList<Integer>();
        getReadOnlyDatabase();
        Cursor cur = mDb.rawQuery("SELECT "
                + gas
                + " FROM "
                + DbContract.PastMonth.TABLE_NAME, null);

        cur.moveToFirst();
        while(!cur.isAfterLast()){
            val.add(cur.getInt(cur.getColumnIndex(gas)));
            cur.moveToNext();
        }
        cur.close();
        return val;
    }
    public ArrayList<Integer> getPastYear(String gas){
        ArrayList<Integer> val=new ArrayList<Integer>();
        getReadOnlyDatabase();
        Cursor cur = mDb.rawQuery("SELECT "
                + gas
                + " FROM "
                + DbContract.PastYear.TABLE_NAME, null);

        cur.moveToFirst();
        while(!cur.isAfterLast()){
            val.add(cur.getInt(cur.getColumnIndex(gas)));
            cur.moveToNext();
        }
        cur.close();
        return val;
    }

    public int getAqi(String gas){
        int val = -1;
        getReadOnlyDatabase();
        Cursor cur = mDb.rawQuery("SELECT "
                + gas
                + " FROM "
                + DbContract.Aqi.TABLE_NAME, null);

        cur.moveToFirst();
        if(!cur.isAfterLast())
            val = cur.getInt(cur.getColumnIndex(gas));
        cur.close();
        return val;
    }

    public void updateDefects(Set<String> defectsSet){
        getWritableDatabase();
        mDb.delete(DbContract.DefectPreferences.TABLE_NAME, null, null);
        int i = 0;
        List<String> curr = new ArrayList<String>(defectsSet);

        mDb.delete(DbContract.DefectPreferences.TABLE_NAME, null, null);

        while(i < curr.size()){
            ContentValues values = new ContentValues();
            values.put(DbContract.DefectPreferences.DEFECT, curr.get(i));
            //mDb.insert(DbContract.DefectPreferences.TABLE_NAME, null, values);

            String abc = "INSERT INTO " +
                    DbContract.DefectPreferences.TABLE_NAME +
                    " VALUES ( '" + curr.get(i) + "' );";
            //mDb.rawQuery(abc, null);
            mDb.insert(DbContract.DefectPreferences.TABLE_NAME, null, values);
            Log.d("inserting", abc);
            i++;
        }

    }

    public Set<String> getDefects(){
        Set<String> current = new HashSet<String>();
        Cursor cur;
        try {

            cur = mDb.rawQuery("SELECT "
                    + DbContract.DefectPreferences.DEFECT
                    + " FROM "
                    + DbContract.DefectPreferences.TABLE_NAME,null);
            cur.moveToFirst();
        }
        catch (NullPointerException e)
        {
            return null;
        }

        while(!cur.isAfterLast()){
            Log.d("value", String.valueOf(cur.getString(cur.getColumnIndex(DbContract.DefectPreferences.DEFECT))));
            current.add(String.valueOf(cur.getString(cur.getColumnIndex(DbContract.DefectPreferences.DEFECT))));
            cur.moveToNext();
        }
        cur.close();
        return current;
    }

    public class gas{
        int aqi;
        String gasName;
    }

    public Map<String, Integer> getRawAqi(){
        gas[] values = new gas[5];
        getWritableDatabase();
        Map<String,Integer> gasData = new HashMap<>();
        if(mDb==null){
            Log.e("mDb","mDb is null");
        }
        Cursor cur = mDb.rawQuery("SELECT *"
                + " FROM "
                + DbContract.Aqi.TABLE_NAME, null);

        cur.moveToFirst();
        for(int i=0; i < cur.getColumnCount();i++)
        {
            if(cur.getColumnName(i).equals("aqi") ){}
            else {
                if(cur.getCount() > 0)
                {
                    gasData.put(cur.getColumnName(i), cur.getInt(i));
                }
                else{
                    Log.e("gasDataEmpty","x");
                }

            }
        }

        Map<String, Integer> sortedMap = sortByComparator(gasData);
        cur.close();
        return sortedMap;
    }

    public Map<String, Integer> getAqi(){
        gas[] values = new gas[5];
        Map<String,Integer> gasData = new HashMap<>();
        Cursor cur = mDb.rawQuery("SELECT *"
                + " FROM "
                + DbContract.Aqi.TABLE_NAME,null);

        cur.moveToFirst();
        if(!cur.isAfterLast()) {
            for (int i = 0; i < cur.getColumnCount(); i++) {
                if (cur.getColumnName(i).equals("aqi")) {
                } else {
                    gasData.put(cur.getColumnName(i), cur.getInt(i));
                }
            }
            Map<String, Integer> cleanMap = clean(gasData);
            Map<String, Integer> sortedMap = sortByComparator(cleanMap);

            cur.close();
            return sortedMap;
        }
        cur.close();
        return null;
    }

    private Map<String, Integer> clean(Map<String, Integer> gasData) {
        Map<String,Integer> cleanMap = new HashMap<>();
        cleanMap.put("Nitrogen Dioxide", gasData.get("nitrogenDioxide"));
        cleanMap.put("Carbon Monoxide", gasData.get("carbonMonoxide"));
        cleanMap.put("Ozone", gasData.get("ozone"));
        cleanMap.put("PM 2.5", gasData.get("pm25"));
        cleanMap.put("PM 10", gasData.get("pm10"));
        return cleanMap;
    }

    private int[] jsonToIntArray(JSONArray jsonArray) throws JSONException {
        int[] arr = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            arr[i]=jsonArray.getInt(i);
        }
        return arr;
    }

    public void setYearOfBirth(int year){

    }


    public void generateAQITable(ServerObject sO)
    {
        Log.d("Gas Specific aqi(aaa)", "XOXO");

        getWritableDatabase();
        Map<String,Integer> gasData = new HashMap<>();
        int currentAqi;
        int gasSpecSize = sO.getGasSpecific().length;
        for (int i = 0 ; i < gasSpecSize ; i++)
        {
            currentAqi = sO.getGasSpecific()[i].getAqi();
            gasData.put(sO.getGasSpecific()[i].getGasType(), currentAqi);
        }
        mDb.delete("CurrentAQI", null, null);
        ContentValues values = new ContentValues();
        values.put("aqi",gasData.get("aqi"));
        values.put("nitrogenDioxide",gasData.get("nitrogenDioxide"));
        values.put("ozone",gasData.get("ozone"));
        values.put("pm25",gasData.get("pm25"));
        values.put("pm10",gasData.get("pm10"));
        values.put("carbonMonoxide", gasData.get("carbonMonoxide"));

        Log.d("ContentValues", String.valueOf(gasData.get("aqi")));
        mDb.insert("CurrentAQI", null, values);




    }

    //UNused
    public void createAQITable(JSONArray gases) throws JSONException {
        Log.d("Gas Specific aqi(aaa)", "Abhishek");
        getWritableDatabase();
        Map<String,Integer> gasData = new HashMap<>();
        int currentAqi;
        Log.d("Gas Specific aqi(aaa)", String.valueOf(gases));

        for (int i=0;i<gases.length();i++){
            currentAqi = gases.getJSONObject(i).getInt("aqi");
            gasData.put(gases.getJSONObject(i).getString("gasType"), currentAqi);
            Log.d("Gas Specific aqi(aaa)", String.valueOf(currentAqi));
        }

        mDb.delete("CurrentAQI", null, null);
        Log.d("xxxssss", "xxx");
        Log.d("gasData", String.valueOf(gasData));
        ContentValues values = new ContentValues();
        values.put("aqi",gasData.get("aqi"));
        values.put("nitrogenDioxide",gasData.get("nitrogenDioxide"));
        values.put("ozone",gasData.get("ozone"));
        values.put("pm25",gasData.get("pm25"));
        values.put("pm10",gasData.get("pm10"));
        values.put("carbonMonoxide", gasData.get("carbonMonoxide"));

        Log.d("ContentValues", String.valueOf(gasData.get("aqi")));
        mDb.insert("CurrentAQI", null, values);
        //mDb.rawQuery("INSERT INTO " + DbContract.Aqi.TABLE_NAME + " VALUES  (20,40,60,80,100,120);", null);

    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // Convert sorted map back to a Map
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("[Key] : " + entry.getKey()
                    + " [Value] : " + entry.getValue());
        }
    }

    public void generate24HourTable(ServerObject sO)
    {
        getWritableDatabase();
        Log.d("24 Hour Table" ,"Inside");
        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastDay = null;

        int sizeGasSpec = 0;
        if(sO.getGasSpecific()!=null)
            sizeGasSpec =  sO.getGasSpecific().length;

        for (int i = 0; i < sizeGasSpec ; i++)
        {
            pastDay = sO.getGasSpecific()[i].getPastDay();
            gasData.put(sO.getGasSpecific()[i].getGasType() , pastDay);

        }
        mDb.delete(DbContract.Past24Hours.TABLE_NAME,null,null);

        for (int i = 0; i < pastDay.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.Past24Hours.HOUR_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.Past24Hours.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.Past24Hours.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.Past24Hours.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.Past24Hours.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.Past24Hours.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            Log.d("-Gas Specific aqi-", String.valueOf(gasData.get("carbonMonoxide")[i]));
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.Past24Hours.TABLE_NAME, null, values);
        }

    }

    //UNused
    public void create24HourTable(JSONArray gases) throws JSONException {
        getWritableDatabase();
        Log.d("Gas Specific aqi-24", String.valueOf(gases));
        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastDay = null;
        for (int i=0;i<gases.length();i++){
            pastDay = jsonToIntArray(gases.getJSONObject(i).getJSONArray("pastDay"));
            gasData.put(gases.getJSONObject(i).getString("gasType"), pastDay);
            //Log.d("Gas Specific aqi------", gases.getJSONObject(i).getString("aqi"));
        }

        mDb.delete(DbContract.Past24Hours.TABLE_NAME,null,null);

        for (int i = 0; i < pastDay.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.Past24Hours.HOUR_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.Past24Hours.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.Past24Hours.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.Past24Hours.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.Past24Hours.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.Past24Hours.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            Log.d("-Gas Specific aqi-", String.valueOf(gasData.get("carbonMonoxide")[i]));
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.Past24Hours.TABLE_NAME, null, values);
        }
    }


    public void generateWeekTable(ServerObject sO)
    {
        getWritableDatabase();
        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastWeek = null;

        int sizeGasSpec = 0;
        if(sO.getGasSpecific()!=null)
            sizeGasSpec =  sO.getGasSpecific().length;


        for (int i = 0; i < sizeGasSpec ; i++)
        {
            pastWeek = sO.getGasSpecific()[i].getPastWeek();
            gasData.put(sO.getGasSpecific()[i].getGasType() , pastWeek);

        }

        mDb.delete(DbContract.PastWeek.TABLE_NAME,null,null);

        for (int i = 0; i < pastWeek.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.PastWeek.DAY_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.PastWeek.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.PastWeek.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.PastWeek.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.PastWeek.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.PastWeek.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.PastWeek.TABLE_NAME,null,values);
        }
    }

    //UNused
    public void createWeekTable(JSONArray gases) throws JSONException {
        getWritableDatabase();
        Log.d("Gas Specific aqi-week", String.valueOf(gases));
        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastWeek = null;
        for (int i=0;i<gases.length();i++){
            pastWeek = jsonToIntArray(gases.getJSONObject(i).getJSONArray("pastWeek"));
            gasData.put(gases.getJSONObject(i).getString("gasType"), pastWeek);
            Log.d("Gas Specific aqi-week", gases.getJSONObject(i).getString("aqi"));
        }

        mDb.delete(DbContract.PastWeek.TABLE_NAME,null,null);

        for (int i = 0; i < pastWeek.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.PastWeek.DAY_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.PastWeek.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.PastWeek.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.PastWeek.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.PastWeek.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.PastWeek.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.PastWeek.TABLE_NAME,null,values);
        }
    }


    public void generateMonthTable(ServerObject sO)
    {
        getWritableDatabase();
        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastMonth = null;

        int sizeGasSpec = 0;
        if(sO.getGasSpecific()!=null)
            sizeGasSpec =  sO.getGasSpecific().length;


        for (int i = 0; i < sizeGasSpec ; i++)
        {
            pastMonth = sO.getGasSpecific()[i].getPastMonth();
            gasData.put(sO.getGasSpecific()[i].getGasType() , pastMonth);

        }

        mDb.delete(DbContract.PastMonth.TABLE_NAME,null,null);

        for (int i = 0; i < pastMonth.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.PastMonth.DAY_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.PastMonth.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.PastMonth.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.PastMonth.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.PastMonth.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.PastMonth.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.PastMonth.TABLE_NAME,null,values);
        }


    }

    //UNused
    public void createMonthTable(JSONArray gases) throws JSONException {
        getWritableDatabase();
        Log.d("aqi-week-month", "month\n"+String.valueOf(gases));
        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastMonth = null;
        for (int i=0;i<gases.length();i++){
            pastMonth = jsonToIntArray(gases.getJSONObject(i).getJSONArray("pastMonth"));
            gasData.put(gases.getJSONObject(i).getString("gasType"), pastMonth);
            //Log.d("Gas Specific aqi",gases.getJSONObject(i).getString("aqi"));
        }

        mDb.delete(DbContract.PastMonth.TABLE_NAME,null,null);

        for (int i = 0; i < pastMonth.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.PastMonth.DAY_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.PastMonth.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.PastMonth.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.PastMonth.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.PastMonth.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.PastMonth.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.PastMonth.TABLE_NAME,null,values);
        }
    }


    public void generateYearTable(ServerObject sO)
    {
        getWritableDatabase();

        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastyear = null;


        int sizeGasSpec = 0;
        if(sO.getGasSpecific()!=null)
            sizeGasSpec =  sO.getGasSpecific().length;


        for (int i = 0; i < sizeGasSpec ; i++)
        {
            pastyear = sO.getGasSpecific()[i].getPastYear();
            gasData.put(sO.getGasSpecific()[i].getGasType() , pastyear);
        }

        mDb.delete(DbContract.PastYear.TABLE_NAME,null,null);

        for (int i = 0; i < pastyear.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.PastYear.MONTH_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.PastYear.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.PastYear.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.PastYear.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.PastYear.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.PastYear.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.PastYear.TABLE_NAME,null,values);
        }


    }
    //UNused
    public void createYearTable(JSONArray gases) throws JSONException {
        getWritableDatabase();
        Log.d("aqi-Year", "Year - "+String.valueOf(gases));
        HashMap<String,int[]> gasData = new HashMap<>();
        int[] pastyear = null;
        for (int i=0;i<gases.length();i++){
            pastyear = jsonToIntArray(gases.getJSONObject(i).getJSONArray("pastYear"));
            gasData.put(gases.getJSONObject(i).getString("gasType"), pastyear);
            Log.d("Gas Specific aqi-year", gases.getJSONObject(i).getString("aqi"));
        }

        mDb.delete(DbContract.PastYear.TABLE_NAME,null,null);

        for (int i = 0; i < pastyear.length; i++) {
            ContentValues values=new ContentValues();
            values.put(DbContract.PastYear.MONTH_NUMBER,i);
            //values.put("aqi",gasData.get("aqi")[i]);
            values.put(DbContract.PastYear.NITROGEN_DIOXIDE,gasData.get("nitrogenDioxide")[i]);
            values.put(DbContract.PastYear.OZONE,gasData.get("ozone")[i]);
            values.put(DbContract.PastYear.PM25,gasData.get("pm25")[i]);
            values.put(DbContract.PastYear.PM10,gasData.get("pm10")[i]);
            values.put(DbContract.PastYear.CARBON_MONOXIDE,gasData.get("carbonMonoxide")[i]);
            //Log.d("ContentValues", String.valueOf(gasData.get("aqi")[i]));
            mDb.insert(DbContract.PastYear.TABLE_NAME,null,values);
        }
    }


    public void updateTimeStamp()
    {
        getWritableDatabase();
        mDb.delete(DbContract.TimeStamp.TABLE_NAME,null,null);

        ContentValues values=new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());

        values.put(DbContract.TimeStamp.timeStamp ,currentDateandTime.toString() );
        mDb.insert(DbContract.TimeStamp.TABLE_NAME, null , values);


    }

    public String getTimeStamp()
    {
        getWritableDatabase();

        Cursor row = mDb.rawQuery(" SELECT * From " + DbContract.TimeStamp.TABLE_NAME + " ;",null);
        row.moveToFirst();

        String dateTime = "f";
        dateTime = row.getString(row.getColumnIndex(DbContract.TimeStamp.timeStamp));

        String sol[] = dateTime.split("_");
        String date = sol[0];
        String time = sol[1];
        date = date.substring(0,4) + "/" + date.substring(4,6) + "/" + date.substring(6,8);
        time = time.substring(0,2) + ":" + time.substring(2,4) + ":" + time.substring(4,6);


        dateTime = "Last Updated : " + date + " " + time;
        Log.d("TimeS" , dateTime);



        row.close();

        return dateTime;
    }
}
