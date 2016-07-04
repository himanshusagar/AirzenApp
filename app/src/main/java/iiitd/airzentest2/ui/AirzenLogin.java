package iiitd.airzentest2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import iiitd.airzentest2.MainActivity;
import iiitd.airzentest2.R;
import iiitd.airzentest2.network.api.ServerApi;
import iiitd.airzentest2.network.model.RegisterObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AirzenLogin extends AppCompatActivity
{


    public static final String TAG = "AirzenLogin";
    private static final int REQUEST_SIGNUP = 0;

    private static boolean isNetworkIOComplete = false;

    @InjectView(R.id.input_deviceId)
    EditText _deviceIdText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;

    @InjectView(R.id.textViewshowUserEmailID)
    TextView showUserEmailId;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airzen_login);
        ButterKnife.inject(this);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        try
        {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFERENCES_FILE, Context.MODE_PRIVATE);
            String Token = sharedPreferences.getString(MainActivity.SHARED_PREFS_TOKEN_KEY, "");
            String emailId = sharedPreferences.getString(MainActivity.SHARED_PREFS_EMAILID, "");

            if (Token != "" || emailId != "")
            {
                    Log.d( TAG , "Email+Token found : Starting Main Activity");
                    startMainActivityIntent();

            }
            else
            {
                Log.d(TAG, "NOT Starting Main Activity");

                Log.d(TAG, "Inside AZLogin onCreate" + (Token == "") + emailId);
            }

        }
        catch(Exception e)
        {

        }

        String toBeSet = getIntent().getStringExtra(SignInActivity.EXTRA_EMAIL);

        if (toBeSet != null || toBeSet != "")
        {
            showUserEmailId.setText(toBeSet);
        }

    }


    @Override
    public void onStart()
    {
        super.onStart();
     }

    public void login()
    {
        Log.d(TAG, "Login");

        if (!validate())
        {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(AirzenLogin.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String deviceId = _deviceIdText.getText().toString();
        String passKey = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.


        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(MainActivity.IP_ADDR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();                                        //create an adapter for retrofit with base url

        ServerApi serverService = restAdapter.create(ServerApi.class);

        final String userEmail = getIntent().getStringExtra(SignInActivity.EXTRA_EMAIL);




        //userEmail = MainActivity.DUMMY_EMAIL;

        RegisterObject bodyObject = new RegisterObject(deviceId ,passKey ,userEmail);

        Log.d(TAG , bodyObject.getPassKey());


        Call<RegisterObject> call = serverService.loadObject(bodyObject);

        call.enqueue(new Callback<RegisterObject>() {
            @Override
            public void onResponse(Call<RegisterObject> call, Response<RegisterObject> response)
            {

                progressDialog.dismiss();

                RegisterObject answer = response.body();
                Log.d(TAG," Got Response + Reg");
                //serverObject = answer;
                //answer.toString();

                if(answer.isPairFound())
                {

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.PREFERENCES_FILE , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();

                    editor.putString(MainActivity.SHARED_PREFS_TOKEN_KEY,answer.getToken());
                    editor.putBoolean(MainActivity.SHARED_PREFS_isREGISTERED_KEY,true);
                    editor.putString(MainActivity.SHARED_PREFS_EMAILID,userEmail);

                    editor.apply();

                    startMainActivityIntent();

                    onLoginSuccess();

                }
                else
                    onLoginFailed();


            }

            @Override
            public void onFailure(Call<RegisterObject> call, Throwable t)
            {
                progressDialog.dismiss();


                Log.d(TAG,call.toString() + call.request());

                onLoginFailed();
                //serverObject = null;
            }

        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess()
    {


        Toast.makeText(getBaseContext(), "Login SucessFul", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
      //  finish();

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String deviceId = _deviceIdText.getText().toString();
        String password = _passwordText.getText().toString();

        if (deviceId.isEmpty() )
        {
            _deviceIdText.setError("enter a valid DeviceId");
            valid = false;
        }
        else
        {
            _deviceIdText.setError(null);
        }

        if (password.isEmpty() ) {
            _passwordText.setError("Enter Valid PassKey");
            valid = false;
        }
        else
        {
            _passwordText.setError(null);
        }

        return valid;
    }

    /*
    private void isValidDeviceCredentials(String Token , String emailId) throws Exception
    {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(MainActivity.IP_ADDR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();                                        //create an adapter for retrofit with base url

        ServerApi serverService = restAdapter.create(ServerApi.class);

            ValidationObject bodyObject = new ValidationObject(Token,emailId,false);


            Call<ValidationObject> call = serverService.loadObject(bodyObject);
            call.enqueue(new Callback<ValidationObject>() {
                @Override
                public void onResponse(Call<ValidationObject> call, Response<ValidationObject> response)
                {
                    ValidationObject answer = response.body();

                    if(answer!=null && answer.isValidated())
                    {
                        Log.d( TAG , "Starting Main Activity");
                        startMainActivityIntent();
                    }
                    else
                        Log.d( TAG , "NOT Starting Main Activity");



                }

                @Override
                public void onFailure(Call<ValidationObject> call, Throwable t)
                {
                    Log.d(TAG,call.toString());
                    Log.d( TAG , "NOT Starting Main Activity");

                }
            });




    }

    */

    private void startMainActivityIntent()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }


    private boolean CheckNetworkIOStatus()
    {
        return isNetworkIOComplete;
    }

}
