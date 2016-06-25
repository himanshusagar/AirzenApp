package iiitd.airzentest2.network.api;

import iiitd.airzentest2.network.model.RegisterObject;
import iiitd.airzentest2.network.model.ServerObject;
import iiitd.airzentest2.network.model.ValidationObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Himanshu Sagar on 10-06-2016.
 */
public interface ServerApi
{
    @POST("api/app")
    Call<ServerObject> loadObject(@Body ServerObject serverObject);

    @POST("api/app/register")
    Call<RegisterObject> loadObject(@Body RegisterObject registerObject);

    @POST("api/app/auth")
    Call<ValidationObject> loadObject(@Body ValidationObject validationObject);


}
