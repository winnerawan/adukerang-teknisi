package co.ipb.adukerang.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;
import co.ipb.adukerang.helper.CircledNetworkImageView;
import co.ipb.adukerang.helper.SlideActivity;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.avatar)
    CircledNetworkImageView avatar;
    @InjectView(R.id.nama_pelapor)
    TextView txtPelapor;
    @InjectView(R.id.nama_ruang)
    TextView txtRuangan;
    @InjectView(R.id.created_at)
    TextView txtCreated;
    @InjectView(R.id.foto_keluhan)
    NetworkImageView ivKeluhan;
    @InjectView(R.id.keluhan)
    TextView txtKeluhan;
    @InjectView(R.id.like)
    TextView txtLike;
    @InjectView(R.id.star_button)
    com.like.LikeButton bLike;
    @InjectView(R.id.ib_comment)
    ImageButton bComment;
    @InjectView(R.id.idktemp) TextView temp;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private int i = 0;
    public String id_keluhan, id_ruang, uid;
    private SQLiteHandler db;
    private SessionManager session;
    private SharedPreferences pref;
    SharedPreferences.Editor edit;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        Bundle select = getIntent().getExtras();
        id_keluhan = select.getString("id_keluhan");
        //temp.setText(id_keluhan);
        String pelapor = select.getString("pelapor");
        String pp_null = "http://www.jordanhardware.com/styles/default/xenforo/avatars/avatar_m.png";
        String pp = select.getString("profile_picture");
        String foto = select.getString("foto");
        String keluhan = select.getString("keluhan");
        getDetails();

        txtPelapor.setText(pelapor);
        avatar.setImageUrl(pp, imageLoader);
        ivKeluhan.setImageUrl(foto, imageLoader);
        txtKeluhan.setText(keluhan);

            if (pp.isEmpty()) {
                avatar.setImageUrl(pp_null, imageLoader);
            }


        pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savedValue = sharedPreferences.getInt("key", 0);
            if (savedValue==1) {
                bLike.setLiked(true);
            } else if(savedValue==0){
                bLike.setLiked(false);
            }
        getLiker();
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        checkLiked();

        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String email = user.get("email");
        uid = user.get("uid");

        bLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                bLike.setLiked(true);
                i=1;
                Like();
                getLiker();
                checkLiked();
                SaveInt("key", 1);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(getApplicationContext(), "Unlike Feature Disable",Toast.LENGTH_LONG).show();
            }
        });




    }
    public void SaveInt(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public void LoadInt(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int savedValue = sharedPreferences.getInt("key", 0);
    }
    private void getDetails() {

        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_GET_K_DETAILS + id_keluhan,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject details = (JSONObject) response
                                        .get(i);

                                String date = details.getString("waktu_keluhan");
                                String nm_ruang = details.getString("nama_ruang");
                                txtCreated.setText(date);
                                txtRuangan.setText(nm_ruang);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.d(TAG, "ERROR" + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void Like() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Liking...", "Please wait...", false, false);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LIKE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(DetailsActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(DetailsActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("id_keluhan", id_keluhan);
                //params.put("unique_id", uid);
                params.put("liked", String.valueOf(1));
                //returning parameters
                return params;

            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getLiker() {

        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_GET_LIKER + id_keluhan,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject details = (JSONObject) response
                                        .get(i);

                                int liker = details.getInt("liker");
                                txtLike.setText(liker+ " like");
                                if (liker>1) {
                                   txtLike.setText(liker+" likes");
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.d(TAG, "ERROR" + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
    private void checkLiked() {

        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_CHECK_LIKE + id_keluhan+"&unique_id="+uid,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject details = (JSONObject) response
                                        .get(i);

                                int liked = details.getInt("liked");
                                i=liked;
                                if (liked==1) {
                                    bLike.setLiked(true);
                                    edit.putInt("liked", 1);
                                    edit.commit();
                                    bLike.setSaveEnabled(true);
                                    bLike.setEnabled(false);
                                } else {

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            VolleyLog.d(TAG, "ERROR" + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

        bComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, CommentActivity.class);
                i.putExtra("id_keluhan", id_keluhan);
                startActivity(i);
            }
        });
    }

}
