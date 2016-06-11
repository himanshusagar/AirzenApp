package iiitd.airzentest2.network;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import iiitd.airzentest2.MainActivity;
import iiitd.airzentest2.R;
import iiitd.airzentest2.network.api.ServerApi;
import iiitd.airzentest2.network.model.GasSpecific;
import iiitd.airzentest2.network.model.ServerObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckNetwork extends AppCompatActivity
{
    String API = MainActivity.IP_ADDR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_network);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();                                        //create an adapter for retrofit with base url

        ServerApi serverService = restAdapter.create(ServerApi.class);

        GasSpecific[] array = {new GasSpecific()};
        String[] array2 = {"My Inferences"};



        ServerObject bodyObject = new ServerObject(array
                ,  array2);


        Call<ServerObject> call = serverService.loadObject(bodyObject);
        call.enqueue(new Callback<ServerObject>() {
            @Override
            public void onResponse(Call<ServerObject> call, Response<ServerObject> response)
            {
                ServerObject answer = response.body();

                Log.d("YO" , " ");
                answer.toString();


            }

            @Override
            public void onFailure(Call<ServerObject> call, Throwable t) {
                Log.d("Fail",call.toString());
            }
        });

    }

}
