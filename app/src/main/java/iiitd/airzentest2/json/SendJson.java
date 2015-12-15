package iiitd.airzentest2.json;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class SendJson {
    /*
    public SendJson(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
    }
*/

    /*public static HttpResponse makeQuery(int yearOfBirth, Set defects, String deviceID){
        JSONObject data = MakeJson.wrapPreferences(yearOfBirth , defects, "A123");
        Log.d("JsonObject", String.valueOf(data));
        //new SendJson(url, data, new Response.Listener<JSONObject>(){});
        SendJson.sendData(data);
        HttpResponse response = sendData(data);
        return response;

    }

    public static HttpResponse sendData(JSONObject data){
        for( int retries = 0; retries < 1; retries++) {
            int TIMEOUT_MILLISEC = 10000;  // = 50 seconds
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost request = new HttpPost("http://sensorize.iiitd.edu.in/api/app");
            try {
                StringEntity entity = new StringEntity(data.toString(), HTTP.UTF_8);
                entity.setContentType("application/json");
                request.setEntity(entity);
                Log.d("data", String.valueOf(entity));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                HttpResponse response = client.execute(request);
                return response;
            } catch (IOException e) {
                //try again.
            }
        }
        return null;
    }*/
}
