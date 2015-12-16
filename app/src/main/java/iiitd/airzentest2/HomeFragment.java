package iiitd.airzentest2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import iiitd.airzentest2.db.DbSingleton;


public class HomeFragment extends Fragment {
    private static final String URL = "file:///android_asset/index.html?val=";
    private WebView mWebView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final DbSingleton db = DbSingleton.getInstance();
        // Inflate the layout for this fragment
        ScrollView l = (ScrollView) inflater.inflate(R.layout.fragment_home, container, false);
//        Random generator = new Random();
//        int i = generator.nextInt(500);

        final int mainaqi = db.getAqi("aqi");

        setMessage(mainaqi);
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
        if(mainaqi<=50){
            txt.setText("Good");
            txt.setTextColor(Color.parseColor("#00b250"));
        }
        else if(mainaqi<=100){
            txt.setText("Satisfactory");
            txt.setTextColor(Color.parseColor("#92c954"));
        }
        else if(mainaqi<=250){
            txt.setText("Moderate");
            txt.setTextColor(Color.parseColor("#e4b7b4"));
        }
        else if(mainaqi<=350){
            txt.setText("Poor!");
            txt.setTextColor(Color.parseColor("#fbbf0f"));
        }
        else if(mainaqi<=400){
            txt.setText("Very Poor!");
            txt.setTextColor(Color.parseColor("#ec2124"));
        }
        else if(mainaqi<=500){
            txt.setText("Severe!");
            txt.setTextColor(Color.parseColor("#be1d23"));
        }

        TextView majorpol = (TextView)l.findViewById(R.id.majorPol);
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
                //String user = ((EditText) findViewById(R.id.edit_text)).getText().toString();
               /* if (user.isEmpty()) {
                    user = "World";
                }*/
                /*String javascript="javascript: m();";
                view.loadUrl(javascript);*/
                /*String javascript="javascript: document.getElementById('msg').innerHTML='Hello "+user+"!';";
                view.loadUrl(javascript);
                */
            }
        });
        refreshWebView(mainaqi);
        return l;
    }

    private void setMessage(int mainaqi){
        //final int mainaqi = db.getAqi("aqi");


        /*String aqi = String.valueOf(mainaqi);
        String outOf = "/500";
        String finalString = aqi+outOf;
        Spannable sb = new SpannableString( finalString );
        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), finalString.indexOf(aqi), aqi.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //bold
        sb.setSpan(new AbsoluteSizeSpan(250), 0, aqi.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//resize size
        sb.setSpan(new AbsoluteSizeSpan(70), finalString.indexOf(outOf), finalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//resize size
        txt.setText(sb);*/
    }

    private void refreshWebView(int aqi) {
        /*
        Random rand=new Random();
        int num=rand.nextInt(500);
        */
        mWebView.loadUrl(URL + Integer.toString(aqi));

        /*TextView aqiText=(TextView)getActivity().findViewById(R.id.aqiTextView);
        aqiText.setText("AQI - " + Integer.toString(aqi));*/
    }

}
