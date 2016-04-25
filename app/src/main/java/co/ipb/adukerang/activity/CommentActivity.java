package co.ipb.adukerang.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.fragment.CommentFragment;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;

public class CommentActivity extends AppCompatActivity {

    private static final String TAG = CommentActivity.class.getSimpleName();
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.bComment)
    Button bComment;
    @InjectView(R.id.txtcomment)
    EditText txtComment;
    @InjectView(R.id.temp)
    TextView temp;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private SessionManager session;
    String idk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Komentar");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        idk = b.getString("id_keluhan");
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        temp.setText(idk);
        bComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> user = db.getUserDetails();
                String email = user.get("email");
                String comment = txtComment.getText().toString();
                String id_kel = temp.getText().toString();
                postComment(id_kel, email, comment);


            }
        });
    }

    public void Frag(View view) {
        Fragment fr;

        fr = new CommentFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_comment, CommentFragment);
        fragmentTransaction.commit();

    }

    private void postComment(final String id_keluhan, final String email,
                              final String komentar) {
        String tag_string_req = "req_register";

        pDialog.setMessage("Post Comment ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SET_COMMENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Comment Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        JSONObject user = jObj.getJSONObject("user");
                        String id_keluhan = user.getString("id_keluhan");
                        String email = user.getString("email");
                        String komentar = user
                                .getString("komentar");

                    } else {


                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Post Comment Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("tag", "register");
                params.put("id_keluhan", id_keluhan);
                params.put("email", email);
                params.put("komentar", komentar);
                return params;
            }

        };
        //Log.d(TAG,"REG ID : "+ regId.toString());
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
