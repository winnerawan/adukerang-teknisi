package co.ipb.adukerang.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;
import co.ipb.adukerang.helper.HttpRequest;
import co.ipb.adukerang.teknisi.GCMIntentService;

public class NotifActivity2 extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = NotifActivity2.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String uid, email, name, teknisi_id, id_ruangan, id_barang, keluhan, foto, id_pengadu, id_keluhan, status;
    //final String gcm_pengadu;
    @InjectView(R.id.n_foto_keluhan)
    NetworkImageView iv_foto_keluhan;
    @InjectView(R.id.txtRuang)
    TextView tvRuang;
    @InjectView(R.id.txtBarang)
    TextView tvBarang;
    @InjectView(R.id.txtKeluhan)
    TextView tvKeluhan;
    @InjectView(R.id.spStatus)
    Spinner spStatus;
    @InjectView(R.id.btnLapor)
    Button bLapor;
    @InjectView(R.id.tvverify)
    TextView tvverify;
    @InjectView(R.id.btnCancel)
    Button bCancel;
    @InjectView(R.id.radioVerify)
    RadioGroup gVerify;
    @InjectView(R.id.cProses)
    RadioButton cProses;
    @InjectView(R.id.cPending)
    RadioButton cPending;
    @InjectView(R.id.cSelesai)
    RadioButton cSelesai;
    @InjectView(R.id.cVerify)
    CheckBox cVerify;
    @InjectView(R.id.temporary_gcm_pengadu)
    TextView temp_gcm;
    @InjectView(R.id.temporary_unique_id)
    TextView temp_uid;
    @InjectView(R.id.temporary_id_keluhan)
    TextView temp_id_keluhan;
    String n_keluhan, idk_last,nama;
    int p;
    String statuS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        ButterKnife.inject(this);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        teknisi_id = user.get("uid");
        email = user.get("email");

        List<String> list_status = new ArrayList<String>();
        list_status.add("PROSES");
        list_status.add("PENDING");
        list_status.add("SELESAI");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(statusAdapter);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        bLapor.setOnClickListener(this);
        bCancel.setOnClickListener(this);

        Intent intent = getIntent();


        Context context = getApplicationContext();

        ComponentName comp =
                new ComponentName(context.getPackageName(),
                        GCMIntentService.class.getName());

        intent.setComponent(comp);

        n_keluhan = intent.getStringExtra("message");

        tvKeluhan.setText(n_keluhan);
        getKeluhanBaru();
        String kl = intent.getStringExtra("keluhan");
        tvKeluhan.setText(kl);
        String br = intent.getStringExtra("id_barang");
        tvBarang.setText(br);
        nama = intent.getStringExtra("name");
        String ru = intent.getStringExtra("id_ruang");
        tvRuang.setText(ru);
        String ft = intent.getStringExtra("foto");
        iv_foto_keluhan.setImageUrl(ft, imageLoader);



        String sts = intent.getStringExtra("status");
        if (sts == null) {
            sts = "";
        }
        if (sts.equals("PROSES")) {
            cProses.setChecked(true);
        } else if (sts.equals("PENDING")) {
           cPending.setChecked(true);
        } else if (sts.equals("SELESAI")) {
           cSelesai.setChecked(true);
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        if (v == bCancel) {
            onBackPressed();
        }
        if (v == bLapor) {
            //sendNotifications();
            String statuS="status";
            if (cSelesai.isChecked()) {
                cPending.setChecked(false);
                cProses.setChecked(false);
                statuS = "SELESAI";
            } else if (cPending.isChecked()) {
                cSelesai.setChecked(false);
                cProses.setChecked(false);
                statuS = "PENDING";

            }
            updateKeluhan2(statuS, tvKeluhan.getText().toString().replaceAll(" ","%20"));
            Log.e(TAG, "1........."+statuS);
            Log.e(TAG, "2........."+tvKeluhan.getText().toString());
            getGCMUser();
            //sendNotifications();
            onBackPressed();

        }

        if ((v == bLapor) && (cVerify.isChecked())) {
            tutupKeluhan();
        }
    }


    private void getKeluhanBaru() {
        String keluhantes = "ac%20mati";
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_TEKNISI_NOTIF + tvKeluhan.getText().toString()
                .replaceAll(" ", "%20"),
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

                                id_keluhan = teknisi.getString("id_keluhan");
                                id_ruangan = teknisi.getString("id_ruang");
                                id_barang = teknisi.getString("id_barang");
                                keluhan = teknisi.getString("keluhan");
                                foto = teknisi.getString("foto");
                                uid = teknisi.getString("unique_id");
                                status = teknisi.getString("status");

                                tvRuang.setText(id_ruangan);
                                tvBarang.setText(id_barang);
                                //tvKeluhan.setText(keluhan);

                                if (status.equals("PROSES")) {
                                    cProses.setChecked(true);
                                    cPending.setEnabled(true);
                                    cSelesai.setEnabled(false);
                                } else if (status.equals("PENDING")) {
                                    cProses.setEnabled(false);
                                    cProses.setChecked(false);
                                    cPending.setChecked(true);
                                    cPending.setEnabled(false);
                                    cSelesai.setEnabled(true);
                                } else if (status.equals("SELESAI")) {
                                    cPending.setEnabled(false);
                                    cProses.setEnabled(false);
                                    cSelesai.setEnabled(true);
                                    cSelesai.setChecked(true);
                                }
                                iv_foto_keluhan.setImageUrl(foto, imageLoader);
                                temp_uid.setText(uid);
                                temp_id_keluhan.setText(id_keluhan);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "KELUHAN BARU TIDAK ADA: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "KELUHAN BARU TIDAK ADA" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void updateKeluhan() {
        Map<String, String> data = new HashMap<String, String>();
        getKeluhanBaru();
        String spStatusSelect = ((Spinner) findViewById(R.id.spStatus)).getSelectedItem().toString();
        String statusSelect = "PROSES";
        if (cSelesai.isChecked()) {
            statusSelect = "SELESAI";
        } else if (cProses.isChecked()) {
            statusSelect = "PROSES";
        } else if (cPending.isChecked()) {
            statusSelect = "PENDING";
        }
        data.put("status", statusSelect);
        data.put("id_keluhan", id_keluhan);
        if (HttpRequest.post(AppConfig.URL_UPDATE_KELUHAN + statusSelect + "&id_keluhan" + id_keluhan).form(data).created())
            System.out.print("Notifications Send!");
        Log.i(TAG, data.toString());
    }

    private void getGCMUser() {
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_GET_GCM_USER + nama,
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

                                String gcm_pengadu = teknisi.getString("gcm_regid");
                                temp_gcm.setText(gcm_pengadu);
                                //sendNotif(teknisi.getString("gcm_regid"), tvKeluhan.getText().toString());
                                Map<String, String> data = new HashMap<String, String>();
                                data.put("id", gcm_pengadu);
                                data.put("keluhan", tvKeluhan.getText().toString());
                                //if (HttpRequest.post(AppConfig.URL_SEND_NOTIF + gcm_pengadu + "&keluhan" + tvKeluhan.getText().toString()).form(data).created())
                                //    System.out.print("Notifications Send!");
                                sendNotifications(gcm_pengadu, tvKeluhan.getText().toString());
                                Log.i(TAG, data.toString());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "GCM TIDAK ADA: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "GCM TIDAK ADA" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void sendNotifications(final String id, final String keluhan) {
        //getGCMUser();
        Map<String, String> data = new HashMap<String, String>();
        data.put("id", id);
        data.put("keluhan", keluhan);
        if (HttpRequest.post(AppConfig.URL_SEND_NOTIF + id + "&keluhan=" + keluhan).form(data).created())
            System.out.print("Notifications Send!");
    }

    private void tutupKeluhan() {
        final ProgressDialog loading = ProgressDialog.show(this, "Tutup Aduan...", "Please wait...", false, false);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_TUTUP_ADUAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(NotifActivity2.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                int fix = 1;
                //String verifySelect = ((Spinner)findViewById(R.id.spVerify)).getSelectedItem().toString();
                String idk = ((TextView) findViewById(R.id.temporary_id_keluhan)).getText().toString();
                String tutup = "ADUAN DITUTUP";
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("is_fix", String.valueOf(fix));
                params.put("keterangan", tutup);
                params.put("id_keluhan", idk);

                //returning parameters
                return params;

            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        Toast.makeText(getApplicationContext(), "ADUAN DITUTUP", Toast.LENGTH_LONG).show();
    }

    //
    //
    private void updateKeluhan2(final String status, final String keluhan) {
        final String tag_json_obj = "json_obj_req";
        final String url = AppConfig.URL_UPDATE_KELUHAN;
        final Map<String, String> params = new HashMap<String, String>();
        params.put("status", status );
        params.put("keluhan", keluhan);

        StringRequest sr = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG,"Error:" + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr, tag_json_obj);
    }
}