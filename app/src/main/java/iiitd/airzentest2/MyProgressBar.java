package iiitd.airzentest2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Srijan Batra on 06-07-2015.
 */
public class MyProgressBar extends ProgressBar {

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setProgressDrawable(context.getResources().getDrawable(R.drawable.progressbar));
    }
}