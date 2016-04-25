package co.ipb.adukerang.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import co.ipb.adukerang.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.handler.SessionManager;
import co.ipb.adukerang.handler.SQLiteHandler;

public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    @InjectView(R.id.email) EditText txtEmail;
    @InjectView(R.id.password) EditText txtPassword;
    @InjectView(R.id.login) Button bLogin;
    @InjectView(R.id.spinner_level) fr.ganfra.materialspinner.MaterialSpinner level;
    @InjectView(R.id.snackbarCoordinatorLayout) CoordinatorLayout snackbarCoordinatorLayout;
    String tes_regid="TESREGID";
    GoogleCloudMessaging gcm;
    String regId,email;
    Context context;
    SharedPreferences prefs;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        //declare spinner
        final List<String> levelList = new ArrayList<>();
        levelList.add("Teknisi");
        levelList.add("User");


        email=txtEmail.getText().toString();
        context = getApplicationContext();




        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, levelList);
        level.setAdapter(adapterSpinner);


        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        //cek user sudah login apa belum
        if (session.isLoggedIn()) {
            //user sudah login
            Intent mainActivity = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(mainActivity);
            finish();
        }

        //tombol login
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "SPINNER = " +level.getSelectedItem().toString());
                email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                    if (level.getSelectedItem().toString().equals("Teknisi") && (!email.isEmpty() && (!password.isEmpty()))) {
                        loginTeknisi(email, password);
                        Log.e(TAG, txtEmail.getText().toString());
                    } else if (level.getSelectedItem().toString().equals("User") && (!email.isEmpty() && (!password.isEmpty()))) {
                        checkLogin(email, password);
                        //updateGCMID(email,regId);
                        //Log.i(TAG, "REGID = "+regId);
                    } else if (level.getSelectedItem().toString().equals("")) {
                        Snackbar snackbar = Snackbar.make(snackbarCoordinatorLayout,
                                "Anda Teknisi atau User ? ", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } if ((txtEmail.getText().toString().isEmpty() && txtPassword.getText().toString().isEmpty()) &&
                        (level.getSelectedItem().toString().equals("Teknisi") || level.getSelectedItem().toString().equals("User"))) {
                        Toast.makeText(getApplicationContext(),"Isi Email & Password !",Toast.LENGTH_LONG).show();
                    }
            }
        });

    }
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        session.setLogin(true);

                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        String gcm_regid = user.getString("gcm_regid");
                        db.addUser(name, email, uid, created_at, gcm_regid);

                        Intent intent = new Intent(LoginActivity.this,
                                DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void loginTeknisi(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_TEKNISI_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        session.setLogin(true);

                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        String gcm_regid = user.getString("gcm_regid");
                        db.addUser(name, email, uid, created_at, gcm_regid);

                        Intent intent = new Intent(LoginActivity.this,
                                DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

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

