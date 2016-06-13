package iiitd.airzentest2.fragment;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import iiitd.airzentest2.GasSpecific;
import iiitd.airzentest2.HealthRisks;
import iiitd.airzentest2.R;
import iiitd.airzentest2.Suggestions;
import iiitd.airzentest2.db.DbSingleton;


public class HomeFragment extends Fragment {
    private static final String URL = "file:///android_asset/index.html?val=";
    private WebView mWebView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        final DbSingleton db = DbSingleton.getInstance();

        LinearLayout l = (LinearLayout) inflater.inflate(R.layout.fragment_home, container, false);

        final int mainaqi = db.getAqi("aqi");




        TextView healthRisks = (TextView)l.findViewById(R.id.healthRisks);
        healthRisks.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), HealthRisks.class);
                        startActivity(intent);
                    }
                }
        );
        TextView suggestions = (TextView)l.findViewById(R.id.suggestions);
        suggestions.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), Suggestions.class);
                        startActivity(intent);
                    }
                }
        );
        TextView gasSpecific = (TextView)l.findViewById(R.id.majorPol);
        gasSpecific.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), GasSpecific.class);
                        startActivity(intent);
                    }
                }
        );
        TextView txt = (TextView) l.findViewById(R.id.textView);

        if(mainaqi==-1)
        {
            l.findViewById(R.id.mainDataContainer).setVisibility(View.GONE);
            l.findViewById(R.id.noDataAvailable).setVisibility(View.VISIBLE);
        }
        else
        {
            l.findViewById(R.id.mainDataContainer).setVisibility(View.VISIBLE);
            l.findViewById(R.id.noDataAvailable).setVisibility(View.GONE);
            if (mainaqi <= 50) {
                txt.setText("Good");
                txt.setTextColor(Color.parseColor("#00b250"));
            } else if (mainaqi <= 100) {
                txt.setText("Satisfactory");
                txt.setTextColor(Color.parseColor("#92c954"));
            } else if (mainaqi <= 250) {
                txt.setText("Moderate");
                txt.setTextColor(Color.parseColor("#e4b7b4"));
            } else if (mainaqi <= 350) {
                txt.setText("Poor!");
                txt.setTextColor(Color.parseColor("#fbbf0f"));
            } else if (mainaqi <= 400) {
                txt.setText("Very Poor!");
                txt.setTextColor(Color.parseColor("#ec2124"));
            } else  {
                txt.setText("Severe!");
                txt.setTextColor(Color.parseColor("#be1d23"));
            }

            TextView majorpol = (TextView) gasSpecific;

            majorpol.setText("Major Pollutant - PM 2.5");
            //Toast.makeText(getContext(), "Home", Toast.LENGTH_SHORT).show();

            mWebView = (WebView) l.findViewById(R.id.webview);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return (event.getAction() == MotionEvent.ACTION_MOVE);
                }
            });

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {

                }
            });
            refreshWebView(mainaqi);
        }


        TextView timeStamp = (TextView)l.findViewById(R.id.textViewTimpStamp);
        Log.d("TimeS","Gonna Query");

        try {
            timeStamp.setText(

                    db.getTimeStamp()
            );
            return l;
        }
        catch(SQLException e)
        {
            e.printStackTrace();

            timeStamp.setText(String.valueOf("ni milla"));
            return l;
        }

        //return l;
    }


    private void refreshWebView(int aqi) {

        mWebView.loadUrl(URL + Integer.toString(aqi));


    }

}
