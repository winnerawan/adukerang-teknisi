package co.ipb.adukerang.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.astuetz.PagerSlidingTabStrip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.fragment.KeluhanFragment;
import co.ipb.adukerang.fragment.KeluhanKuFragment;
import co.ipb.adukerang.fragment.ProfileFragment;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;
import co.ipb.adukerang.helper.CircledNetworkImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;
    private String uid,name,email,regid;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @InjectView(R.id.username) TextView tv_username;
    @InjectView(R.id.email) TextView tv_email;
    @InjectView(R.id.imageView2) CircledNetworkImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();
            uid   = user.get("uid");
            name  = user.get("name");
            email = user.get("email");
            regid = user.get("gcm_regid");

        tv_username.setText(name);
        tv_email.setText(email);
        getAvatar();

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.getAdapter().notifyDataSetChanged();

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }
    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? "PROFILE" :
                    getResources().getString(R.string.title_keluhan);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            return (position == 0) ? new ProfileFragment() : new KeluhanKuFragment();
        }
    }
    private void getAvatar() {

        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_GET_USER_FOTO + email,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            //jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject teknisi = (JSONObject) response
                                        .get(i);

                                String pp = teknisi.getString("profile_picture");
                                avatar.setImageUrl(pp, imageLoader);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.d(TAG, "ERROR" + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "PROFILE PICTURE TIDAK ADA" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
}
