package iiitd.airzentest2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import iiitd.airzentest2.json.DataParser;
import iiitd.airzentest2.json.SendJson;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        SharedPreferences rPrefs = this.getSharedPreferences("registration", Context.MODE_PRIVATE);
        if(!rPrefs.getBoolean("status",false)){
            Log.d("Status-", String.valueOf(rPrefs.getBoolean("status",false)));
            Toast.makeText(this,"Please register to a device.",Toast.LENGTH_LONG).show();
        }
        String reader = SendJson.makeQuery(prefs.getInt("age", 1989), current, "A123");
        if(reader != null) {
            getJson(reader);
        }

        initialiseViews();
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
                    fragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_profile) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new ProfileFragment()).commit();
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

    private void initialiseViews(){


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

        DbSingleton db=DbSingleton.getInstance();
        db.create24HourTable(gases);
        db.createWeekTable(gases);
        db.createMonthTable(gases);
        db.createYearTable(gases);
        db.createAQITable(gases);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getJson(String jsonStr) {
        try {
            DataParser.parseJsonStr(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
