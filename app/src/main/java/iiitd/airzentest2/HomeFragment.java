package iiitd.airzentest2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

import iiitd.airzentest2.db.DbSingleton;


public class HomeFragment extends Fragment {

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

        TextView txt = (TextView) l.findViewById(R.id.textView);
        String aqi = String.valueOf(mainaqi);
        String outOf = "/500";
        String finalString = aqi+outOf;
        Spannable sb = new SpannableString( finalString );
        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), finalString.indexOf(aqi), aqi.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //bold
        sb.setSpan(new AbsoluteSizeSpan(250), 0, aqi.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//resize size
        sb.setSpan(new AbsoluteSizeSpan(70), finalString.indexOf(outOf), finalString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//resize size
        txt.setText(sb);
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

        TextView majorpol = (TextView)l.findViewById(R.id.majorPol);
        majorpol.setText("Major Pollutant - PM 2.5");
        //Toast.makeText(getContext(), "Home", Toast.LENGTH_SHORT).show();
        return l;
    }
}
