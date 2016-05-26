package co.ipb.adukerang.helper;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.ipb.adukerang.R;

/**
 * Created by winnerawan on 5/22/16.
 */


/**
 * Created by winnerawan on 23/11/15.
 */
public class KerangPrefScreen extends PreferenceCategory {

    public KerangPrefScreen(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public KerangPrefScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public KerangPrefScreen(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        // It's just a TextView!
        TextView categoryTitle =  (TextView)super.onCreateView(parent);
        categoryTitle.setTextColor(parent.getResources().getColor(R.color.black));

        return categoryTitle;
    }
}