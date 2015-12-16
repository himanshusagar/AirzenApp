package iiitd.airzentest2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import iiitd.airzentest2.json.SendJson;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout l = (LinearLayout)inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("registration", Context.MODE_PRIVATE);
        TextView currentDevice = (TextView)l.findViewById(R.id.currentDevice);
        currentDevice.setText("Current Device - " + prefs.getString("currentDevice", "Not registered to any device."));
        Button button = (Button)l.findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkInputs() == true) {
                            try {
                                if (isValidUser()) {
                                    Toast.makeText(getContext(), "Registered successfully.", Toast.LENGTH_SHORT).show();
                                    updateCurrentDevice();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        return l;
    }

    private void updateCurrentDevice(){
        SharedPreferences prefs = getActivity().getSharedPreferences("registration", Context.MODE_PRIVATE);
        MaterialEditText deviceId = (MaterialEditText)getActivity().findViewById(R.id.deviceId);
        prefs.edit().putString("currentDevice",deviceId.getText().toString());
        TextView currentDevice = (TextView)getActivity().findViewById(R.id.currentDevice);
        currentDevice.setText("Current Device - " + prefs.getString("currentDevice", "Not registered to any device."));
        Log.d("currentDevice",prefs.getString("currentDevice", "Not registered to any device."));
    }

    private boolean isValidUser() throws JSONException {
        boolean status = true;
        MaterialEditText email = (MaterialEditText)getActivity().findViewById(R.id.userEmail);
        MaterialEditText deviceId = (MaterialEditText)getActivity().findViewById(R.id.deviceId);
        MaterialEditText passkey = (MaterialEditText)getActivity().findViewById(R.id.devicePasskey);
        String reader = SendJson.makeRegistrationQuery(email.getText().toString(), deviceId.getText().toString(), passkey.getText().toString());
        SharedPreferences prefs = this.getActivity().getSharedPreferences("registration", Context.MODE_PRIVATE);
        if(reader != null) {
            Log.d("-registration-",reader);
            JSONObject mainObj = new JSONObject(reader);
            String response = mainObj.getString("response");
            Log.d("responseString",mainObj.getString("response"));
            if(response.toString().equals("true")){
                Log.d("responseString----",mainObj.getString("response"));
                prefs.edit().putBoolean("status",true);
            }
            else{
                prefs.edit().putBoolean("status",false);
            }

        }
        else{
            Log.d("-registration-","response is null");
        }
        return status;
    }

    private boolean checkInputs(){
        SharedPreferences prefs = this.getActivity().getSharedPreferences("registration", Context.MODE_PRIVATE);
        MaterialEditText email = (MaterialEditText)getActivity().findViewById(R.id.userEmail);
        MaterialEditText deviceId = (MaterialEditText)getActivity().findViewById(R.id.deviceId);
        MaterialEditText passkey = (MaterialEditText)getActivity().findViewById(R.id.devicePasskey);
        MaterialEditText cPasskey = (MaterialEditText)getActivity().findViewById(R.id.deviceCPasskey);
        boolean status = true;
        Log.d("-registration-","Checking inputs");
        if(!isValidEmail(email.getText().toString())){
            Toast.makeText(getContext(),"Please enter a valid email id.",Toast.LENGTH_SHORT).show();
            status = false;
        }
        if(!passkey.getText().toString().equals(cPasskey.getText().toString())){
            Toast.makeText(getContext(),"Passkeys do not match.",Toast.LENGTH_SHORT).show();
            status = false;
        }
        String temp = prefs.getString("currentDevice",null);
        if(temp!=null){
            if(temp.equals(deviceId.getText().toString())){
                Toast.makeText(getContext(),"You are already registered to this device.",Toast.LENGTH_LONG).show();
                status = false;
            }
        }

        return status;
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
