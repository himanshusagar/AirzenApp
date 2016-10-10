package iiitd.airzentest2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import iiitd.airzentest2.db.DbSingleton;
import iiitd.airzentest2.fragment.ProfileFragment;
import iiitd.airzentest2.fragment.TabFragment;
import iiitd.airzentest2.json.DataParser;
import iiitd.airzentest2.network.api.ServerApi;
import iiitd.airzentest2.network.model.ServerObject;
import iiitd.airzentest2.ui.AirzenLogin;
import iiitd.airzentest2.ui.SignInActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    public static final String IP_ADDR = "http://192.168.55.220:8080/";
    public static final String SERVER_URL = IP_ADDR +"api/app";
    public static final String SERVER_REGISTER = IP_ADDR + "api/app/register";
    public static final String SHARED_PREFS_TOKEN_KEY = "iiitd.airzentest2.token";
    public static final String SHARED_PREFS_isREGISTERED_KEY = "iiitd.airzentest2.isRegistered";
    public static final String SHARED_PREFS_EMAILID = "iiitd.airzentest2.emailId";
    public static final String SHARED_PREFS_DEVICEID = "iiitd.airzentest2.deviceId";

    public static final String PREFERENCES_FILE = "iiitd.airzentest2.PrefsFile";


    //public static final String DUMMY_EMAIL = "gmail@gmail.com";

    private static final String TAG = "AirzenLogin";

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar t = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        SharedPreferences prefs = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
//        try {
//            testJson();
//        } catch (JSONException e) {
//                e.printStackTrace();
//        }


        final DbSingleton db = DbSingleton.getInstance();
        Set<String> current = new HashSet<String>();
        current = db.getDefects();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFERENCES_FILE , Context.MODE_PRIVATE);



        Log.d("-defects-", String.valueOf(current));

        if(!sharedPreferences.getBoolean(MainActivity.SHARED_PREFS_isREGISTERED_KEY,false))
        {
            //Log.d("Status-", String.valueOf(rPrefs.getBoolean("status",false)));
            Toast.makeText(this,"Please register to a device.",Toast.LENGTH_LONG).show();
        }


/*
        SharedPreferences timeSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext() );
        SharedPreferences.Editor editor = timeSettings.edit();
        editor.putString("timeStamp","1/1/2016");

        editor.commit();
        */


        mContext = getApplicationContext();

        String Token = sharedPreferences.getString(MainActivity.SHARED_PREFS_TOKEN_KEY , "");


        Log.d(AirzenLogin.TAG , Token);

        //Unused
       /* String reader = SendJson.makeQuery(prefs.getInt("age", 1989), current, "A123");
        if(reader != null) {
            getJson(reader);
        }
        */
        handleDatabase(); // NOT in Splash Screen Class


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();



        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_profile) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new ProfileFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_Preferences) {
                    Intent intent = new Intent(getApplicationContext(), Preferences.class);
                    startActivity(intent);
                }



                if (menuItem.getItemId() == R.id.nav_item_action_logout)
                {

                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);

                intent.putExtra(SignInActivity.EXTRA_LOGOUT_STATUS, true);

                Log.d(TAG, "Inside Menu");

                    startActivity(intent);
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();


    }


    private void testJson() throws JSONException {
        String jsonStr = "{\n" +
                "  \"inferences\":[\"Test Inference\",\"ddddd\"],\n" +
                "  \"gasSpecific\":\n" +
                "  [\n" +
                "    {\n" +
                "      \"gasType\":\"aqi\",\n" +
                "      \"aqi\":321,\n" +
                "      \"pastDay\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastWeek\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastMonth\":[213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"monthly\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"healthRisks\":[],\n" +
                "      \"suggestions\":[]\n" +
                "    },\n" +
                "    {\n" +
                "      \"gasType\":\"nitrogenDioxide\",\n" +
                "      \"aqi\":213,\n" +
                "      \"pastDay\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastWeek\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastMonth\":[213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"monthly\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"healthRisks\":[],\n" +
                "      \"suggestions\":[]\n" +
                "    },\n" +
                "    {\n" +
                "      \"gasType\":\"ozone\",\n" +
                "      \"aqi\":222,\n" +
                "      \"pastDay\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastWeek\":[213,444,123,11,87,213,444,123,11,87],\n" +
                "      \"pastMonth\":[213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"monthly\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"healthRisks\":[],\n" +
                "      \"suggestions\":[]\n" +
                "    },\n" +
                "    {\n" +
                "      \"gasType\":\"pm25\",\n" +
                "      \"aqi\":123,\n" +
                "      \"pastDay\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastWeek\":[213,444,123,11,87,213,444,123,11,87],\n" +
                "      \"pastMonth\":[213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"monthly\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"healthRisks\":[],\n" +
                "      \"suggestions\":[]\n" +
                "    },\n" +
                "    {\n" +
                "      \"gasType\":\"pm10\",\n" +
                "      \"aqi\":111,\n" +
                "      \"pastDay\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastWeek\":[213,444,123,11,87,213,444,123,11,87],\n" +
                "      \"pastMonth\":[213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"monthly\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"healthRisks\":[],\n" +
                "      \"suggestions\":[]\n" +
                "    },\n" +
                "    {\n" +
                "      \"gasType\":\"carbonMonoxide\",\n" +
                "      \"aqi\":321,\n" +
                "      \"pastDay\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"pastWeek\":[213,444,123,11,87,213,444,123,11,87],\n" +
                "      \"pastMonth\":[213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290,213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"monthly\":[213,444,123,11,87,213,444,123,11,87,65,290],\n" +
                "      \"healthRisks\":[],\n" +
                "      \"suggestions\":[]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        JSONObject mainObj = new JSONObject(jsonStr);
        String gasSpecific = mainObj.getString("gasSpecific");
        Log.d("Gas Specific", gasSpecific);
        JSONArray gases = new JSONArray(gasSpecific);

        DbSingleton db = DbSingleton.getInstance();
        db.create24HourTable(gases);
        db.createWeekTable(gases);
        db.createMonthTable(gases);
        db.createYearTable(gases);
        db.createAQITable(gases);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return true;
    }

    //Unused
    public void getJson(String jsonStr) {
        try {
            DataParser.parseJsonStr(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void handleDatabase()
    {
       // protected ServerObject serverObject;
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(MainActivity.IP_ADDR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();                                        //create an adapter for retrofit with base url

        ServerApi serverService = restAdapter.create(ServerApi.class);


        iiitd.airzentest2.network.model.GasSpecific[] array = {new iiitd.airzentest2.network.model.GasSpecific()};
        String[] array2 = {"My Inferences"};



        ServerObject bodyObject = new ServerObject(array,  array2);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MainActivity.PREFERENCES_FILE , Context.MODE_PRIVATE);
        String Token = sharedPreferences.getString(MainActivity.SHARED_PREFS_TOKEN_KEY , "");
        String emailId = sharedPreferences.getString(MainActivity.SHARED_PREFS_EMAILID , "");

        bodyObject.setToken(Token);
        bodyObject.setEmailId(emailId);

        Call<ServerObject> call = serverService.loadObject(bodyObject);
        call.enqueue(new Callback<ServerObject>() {
            @Override
            public void onResponse(Call<ServerObject> call, Response<ServerObject> response)
            {
                ServerObject answer = response.body();
                Log.d(TAG," Got Response");
                //serverObject = answer;
                Log.d(TAG , " ");
                //answer.toString();


                if(answer!=null)
                    DataParser.initialiseTables(answer);
                else
                {
                    Log.d(TAG , "Answer Null: "  + (answer==null));


                }


            }

            @Override
            public void onFailure(Call<ServerObject> call, Throwable t)
            {
                Log.d(TAG,call.toString());
                //serverObject = null;
            }
        });


    }


    @Override
    public void onBackPressed()
    {
        this.finishAffinity();


    }



}
