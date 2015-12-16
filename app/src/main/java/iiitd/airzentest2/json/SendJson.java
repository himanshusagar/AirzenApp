package iiitd.airzentest2.json;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;


public class SendJson {


    public static String makeQuery(int yearOfBirth, Set defects, String deviceID){
        JSONObject data = MakeJson.wrapPreferences(yearOfBirth , defects, "A123");
        Log.d("JsonObject", String.valueOf(data));
        //new SendJson(url, data, new Response.Listener<JSONObject>(){});
//        SendJson.sendData(data);
//        HttpResponse response = sendData(data);
        String response = SendData2(data);
        return response;

    }

    public static String SendData2(JSONObject data) {
        //url = "http://sensorize.iiitd.edu.in/api/app";
        URL url = null;
        try {
            url = new URL("http://sensorize.iiitd.edu.in/api/app");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");

            urlConnection.setChunkedStreamingMode(0);
            Log.d("sendingd", String.valueOf(data));
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data.toString());
            writer.close();
            out.close();

//            int HttpResult = con.getResponseCode();
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            //readStream(in);
//            Log.d("inputStream", String.valueOf(in));

            StringBuilder sb = new StringBuilder();
            int HttpResult = urlConnection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String responseData = sb.toString();
                Log.d("responsedata",responseData);
                return responseData;

            }else{
                Log.d("message",urlConnection.getResponseMessage());
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            urlConnection.disconnect();
        }

        return null;
    }
}
