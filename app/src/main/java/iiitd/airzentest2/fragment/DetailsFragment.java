package iiitd.airzentest2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import iiitd.airzentest2.GasSpecific;
import iiitd.airzentest2.MyProgressBar;
import iiitd.airzentest2.R;
import iiitd.airzentest2.db.DbSingleton;

public class DetailsFragment extends Fragment {

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Toast.makeText(getContext(),"Details",Toast.LENGTH_SHORT).show();

        View l = inflater.inflate(R.layout.fragment_details, container, false);

        DbSingleton db = DbSingleton.getInstance();
        Map<String,Integer> gasData = new HashMap<>();
        gasData = db.getAqi();
        if(gasData!=null)
        {
            l.findViewById(R.id.noDataAvailable).setVisibility(View.GONE);
            l.findViewById(R.id.mainFieldsContainer).setVisibility(View.VISIBLE);

            int s = gasData.size();
            int[] aqiValues = new int[s];
            final String[] gasNames = new String[s];
            int index = 0;

            for (Map.Entry<String, Integer> entry : gasData.entrySet()) {

                gasNames[index] = String.valueOf(entry.getKey());
                aqiValues[index] = (entry.getValue());
                index++;
            }

            NewHandleFrameLayouts(gasNames , aqiValues, l);

        }
        else{
            l.findViewById(R.id.noDataAvailable).setVisibility(View.VISIBLE);
            l.findViewById(R.id.mainFieldsContainer).setVisibility(View.GONE);
        }

        return l;
    }


    public void gasSpecific(int tileIndex, String gasName){
        Intent intent = new Intent(getContext(),GasSpecific.class);
        intent.putExtra("Tile_Index", tileIndex);
        intent.putExtra("Gas_Name", gasName);
        startActivity(intent);
    }


    private void NewHandleFrameLayouts(final String[] gasNames ,final int[] aqiValues,View l)
    {

        int frameLayouts[] ={-1 ,R.id.mostProminent,R.id.cell1 ,R.id.cell2 ,R.id.cell3 ,R.id.cell4 };
        int cardViews =R.id.cv;


        for (int i = 1 ; i <= 5 ; i++)
        {

            final int index = i-1;


            FrameLayout frameLayout = (FrameLayout)l.findViewById( frameLayouts[i] );
            CardView cardView = (CardView) frameLayout.findViewById( cardViews );
            cardView.setMaxCardElevation(8);
            TextView gasName = (TextView) cardView.findViewById(R.id.gas_name);
            gasName.setText(gasNames[ index ]);
            TextView gasAqi = (TextView) cardView.findViewById(R.id.gas_aqi);
            int aqi = aqiValues[ index ];
            gasAqi.setText("(" + aqi + ")");
            MyProgressBar progressBar = (MyProgressBar) cardView.findViewById(R.id.prog_bar);
            progressBar.setProgress(aqi);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    gasSpecific( index , gasNames[index]);

                }
            });


        }


    }



    //Unused
    private void handleFrameLayouts(final String[] gasNames ,final int[] aqiValues,View l)
    {


        FrameLayout f1 = (FrameLayout) l.findViewById(R.id.mostProminent);
        CardView main = (CardView) f1.findViewById(R.id.cv);
        main.setMaxCardElevation(8);
        TextView gasName = (TextView) main.findViewById(R.id.gas_name);
        gasName.setText(gasNames[0]);
        TextView gasAqi = (TextView) main.findViewById(R.id.gas_aqi);

        int aqi = aqiValues[0];
        gasAqi.setText("(" + aqi + ")");
        MyProgressBar mainBar = (MyProgressBar) main.findViewById(R.id.prog_bar);
        mainBar.setProgress(aqi);
        main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gasSpecific(0, gasNames[0]);
                // TODO Auto-generated method stub
                //DO you work here
            }
        });


        FrameLayout f2 = (FrameLayout) l.findViewById(R.id.cell1);
        CardView cv1 = (CardView) f2.findViewById(R.id.cv);
        cv1.setMaxCardElevation(8);
        TextView gasName2 = (TextView) cv1.findViewById(R.id.gas_name);
        gasName2.setText(gasNames[1]);
        TextView gasAqi2 = (TextView) cv1.findViewById(R.id.gas_aqi);
        int aqi2 = aqiValues[1];
        gasAqi2.setText("(" + aqi2 + ")");
        MyProgressBar bar2 = (MyProgressBar) cv1.findViewById(R.id.prog_bar);
        bar2.setProgress(aqi2);
        cv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gasSpecific(1, gasNames[1]);
                // TODO Auto-generated method stub
                //DO you work here
            }
        });

        FrameLayout f3 = (FrameLayout) l.findViewById(R.id.cell2);
        CardView cv2 = (CardView) f3.findViewById(R.id.cv);
        cv2.setMaxCardElevation(8);
        TextView gasName3 = (TextView) cv2.findViewById(R.id.gas_name);
        gasName3.setText(gasNames[2]);
        TextView gasAqi3 = (TextView) cv2.findViewById(R.id.gas_aqi);
        int aqi3 = aqiValues[2];
        gasAqi3.setText("(" + aqi3 + ")");
        MyProgressBar bar3 = (MyProgressBar) cv2.findViewById(R.id.prog_bar);
        bar3.setProgress(aqi3);
        cv2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gasSpecific(2, gasNames[2]);
                // TODO Auto-generated method stub
                //DO you work here
            }
        });

        FrameLayout f4 = (FrameLayout) l.findViewById(R.id.cell3);
        CardView cv3 = (CardView) f4.findViewById(R.id.cv);
        cv3.setMaxCardElevation(8);
        TextView gasName4 = (TextView) cv3.findViewById(R.id.gas_name);
        gasName4.setText(gasNames[3]);
        TextView gasAqi4 = (TextView) cv3.findViewById(R.id.gas_aqi);
        int aqi4 = aqiValues[3];
        gasAqi4.setText("(" + aqi4 + ")");
        MyProgressBar bar4 = (MyProgressBar) cv3.findViewById(R.id.prog_bar);
        bar4.setProgress(aqi4);
        cv3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gasSpecific(3, gasNames[3]);
                // TODO Auto-generated method stub
                //DO you work here
            }
        });

        FrameLayout f5 = (FrameLayout) l.findViewById(R.id.cell4);
        CardView cv4 = (CardView) f5.findViewById(R.id.cv);
        cv4.setMaxCardElevation(8);
        TextView gasName5 = (TextView) cv4.findViewById(R.id.gas_name);
        gasName5.setText(gasNames[4]);
        TextView gasAqi5 = (TextView) cv4.findViewById(R.id.gas_aqi);
        int aqi5 = aqiValues[4];
        gasAqi5.setText("(" + aqi5 + ")");
        MyProgressBar bar5 = (MyProgressBar) cv4.findViewById(R.id.prog_bar);
        bar5.setProgress(aqi5);
        cv4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gasSpecific(4, gasNames[4]);
                // TODO Auto-generated method stub
                //DO you work here
            }
        });



    }

}

