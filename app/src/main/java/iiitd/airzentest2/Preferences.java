package iiitd.airzentest2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import iiitd.airzentest2.db.DbSingleton;

public class Preferences extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    AutoCompleteTextView textView=null;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<Integer> ageAdapter;

    String item[]={
            "Asthma", "Bronchitis", "Cough", "Lung cancer"
    };

    Set<String> current = new HashSet<String>();

    //List<Integer> age = new ArrayList<Integer>();
    Integer[] age = new Integer[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar t = (Toolbar)findViewById(R.id.toolbar4);
        setSupportActionBar(t);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        FrameLayout f = (FrameLayout) findViewById(R.id.fillDisease);
        textView = (AutoCompleteTextView) f.findViewById(R.id.diseases);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);

        textView.setThreshold(1);
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        //Range age = new Range(1,100);

        int count = 0;
        while(count <= 99){
            age[count] = year;
            count++;
            year--;
        }


        final Spinner ageSpin;
        ageSpin = (Spinner) findViewById(R.id.ageSpinner);
        ageAdapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,age);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpin.setAdapter(ageAdapter);

        final DbSingleton db = DbSingleton.getInstance();

        //setting user's selected age.
        int selectedAge;
        final SharedPreferences prefs = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        selectedAge = prefs.getInt("age", 0);
        if(selectedAge == 0){
            selectedAge = 1989;
        }

        int index = 0;
        index = c.get(Calendar.YEAR) - selectedAge;
        Log.d("age", String.valueOf(index));
        ageSpin.setSelection(index);

        //setting user's health defects.
        //current.add("Asthma");
        //current.add("Bronchitis");
        current = db.getDefects();
        fillTags(current);


        //saving preferences on click of save button.
        Button save = (Button)findViewById(R.id.savePreferences);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int ageSpinnerSelection = (int)ageSpin.getSelectedItem();
                prefs.edit().clear();
                prefs.edit().putInt("age", ageSpinnerSelection).apply();
                Log.d("spinnerSelection", String.valueOf(ageSpinnerSelection));
                Log.d("savedAge", String.valueOf(prefs.getInt("age", 0)));
                db.updateDefects(current);
                /*
                JSONObject data = MakeJson.wrapPreferences(prefs.getInt("age", 1989), current, "A123");
                Log.d("JsonObject", String.valueOf(data));
                //new SendJson(url, data, new Response.Listener<JSONObject>(){});
                SendJson.sendData(data);
                */
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
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

    private class MyListener implements Button.OnClickListener {
        Object defect;
        public MyListener(Object def) {
            defect = def;

        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            current.remove(defect);
            fillTags(current);
        }
    }

    public void fillTags(final Set<String> current){
        int len = current.size();
        int index = 0;
        TextView diseaseText = null;

        String ids[] = new String[48];
        List<String> curr = new ArrayList<>(current);
        while(index < 10){
            ids[index] = "disease" + Integer.toString(index + 1);
            Log.d("IDno", ids[index]);
            int resID = getResources().getIdentifier(ids[index], "id", "iiitd.airzentest2");
            LinearLayout l = (LinearLayout) findViewById(resID);
            l.setVisibility(View.GONE);
            if(index < len) {
                //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) l.getLayoutParams();
                diseaseText = (TextView) l.findViewById(R.id.diseases);
                diseaseText.setText( curr.get(index));
                //params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                l.invalidate();
                Button minus = (Button) l.findViewById(R.id.remove);
                minus.setOnClickListener(new MyListener(curr.get(index)));

                l.setVisibility(View.VISIBLE);
                //l.setLayoutParams(params);
            }

            index++;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selection = (String) parent.getItemAtPosition(position);
        current.add(selection);
        fillTags(current);
        FrameLayout f = (FrameLayout) findViewById(R.id.fillDisease);
        textView = (AutoCompleteTextView) f.findViewById(R.id.diseases);
        textView.setText("");


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
