package iiitd.airzentest2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Map;

import iiitd.airzentest2.db.DbSingleton;

public class GasSpecific extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private ArrayAdapter<String> trendAdapter;
    String item[]={
            "Past 24 Hours", "Past Week", "Past Month", "Past Year"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_specific);
        Toolbar t = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(t);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        DbSingleton db = DbSingleton.getInstance();
        Map<String,Integer> gasData = db.getRawAqi();

        String gasName;
        int gasID = 0;
        int gasAQI = 0;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                gasName = null;
            } else {
                gasName = extras.getString("Gas_Name");
                gasID = extras.getInt("Tile_Index");
            }
        } else {
            gasName = (String) savedInstanceState.getSerializable("Gas_Name");
        }
        //displayGraph(0, "nitrogenDioxide");
        gasID = 0;
        Object gas = gasData.keySet().toArray()[gasID];
        String gasKey = gas.toString();
        displayGraph(0, gasKey);

        final Spinner trendSpin;
        trendSpin = (Spinner) findViewById(R.id.trendSelector);
        trendAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,item);
        trendAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trendSpin.setAdapter(trendAdapter);
        trendSpin.setOnItemSelectedListener(new MyListener(gasKey));


        TextView msg = (TextView)findViewById(R.id.message);
        Object gasAqi = gasData.get(gasData.keySet().toArray()[gasID]);
        gasAQI = Integer.parseInt(gasAqi.toString());
        msg.setText(getStatement(gasAQI, gasName));

        /*ExpandableTextView exTextViewH=(ExpandableTextView)findViewById(R.id.exTextViewHealthRisks);
        exTextViewH.setText("-Shortness of breath and pain when taking a deep breath." +
                "\n-Coughing and sore or scratchy throat." +
                "\n-Increase the frequency of asthma attacks.");
        ExpandableTextView exTextViewP=(ExpandableTextView)findViewById(R.id.exTextViewPrecautions);
        exTextViewP.setText("-Avoid heavy exertion" +
                "\n-Conserve energy at home, at work, everywhere. In the long term, it helps to reduce the emissions associated with energy production." +
                "\n-Consider manual or electric-powered lawn and garden maintenance equipment when replacing a gasoline-powered mower.");
*/
    }

    private String getStatement(int gasAQI, String gasName) {
        String statement = "";
        if(0 < gasAQI && gasAQI < 50){
            statement = "This concentration of " + gasName + " is good.";
        }
        else if(51 < gasAQI && gasAQI < 100){
            statement = "This concentration of " + gasName + " is satisfactory.";
        }
        else if(101 < gasAQI && gasAQI < 200){
            statement = "This concentration of " + gasName + " is moderate.";
        }
        else if(201 < gasAQI && gasAQI < 300){
            statement = "This concentration of " + gasName + " is poor.";
        }
        else if(301  < gasAQI && gasAQI < 400){
            statement = "This concentration of " + gasName + " is very poor.";
        }
        else{
            statement = "This concentration of " + gasName + " is very severe.";
        }
        return statement;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gas_specific, menu);
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
    public class MyListener implements AdapterView.OnItemSelectedListener
    {
        String gas;
        public MyListener(String gasKey){
            gas = gasKey;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //Log.d("position", String.valueOf(position));
            displayGraph(position, gas);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void displayGraph(int position, String gas){
        DbSingleton db = DbSingleton.getInstance();
        ArrayList<Integer> data = null;

        switch(position){
            case 0: data = db.getPast24Hour(gas);
                break;
            case 1: data = db.getPastWeek(gas);
                break;
            case 2: data = db.getPastMonth(gas);
                break;
            case 3: data = db.getPastYear(gas);
                break;
        }

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < data.size(); i++) {
            entries.add(new Entry(data.get(i),i));
            labels.add(String.valueOf(i));
            Log.d(gas.toString(), data.get(i).toString());
        }

        LineDataSet dataSet = new LineDataSet(entries, "AQI value");

        dataSet.setColor(Color.BLACK);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setLineWidth(1f);
        dataSet.setCircleSize(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        dataSet.setFillAlpha(65);
        dataSet.setFillColor(Color.BLACK);


        LineChart chart = (LineChart)findViewById(R.id.chart);
        LineData lineData=new LineData(labels,dataSet);
        chart.setData(lineData);
        chart.setDescription("Trends");
        chart.animateXY(2000, 2000);

        //chart.setHighlightEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        chart.invalidate();
    }
}
