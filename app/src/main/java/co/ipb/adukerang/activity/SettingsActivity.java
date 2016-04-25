package co.ipb.adukerang.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;
import co.ipb.adukerang.helper.CircledNetworkImageView;
import co.ipb.adukerang.helper.HttpRequest;
import co.ipb.adukerang.model.Keluhan;

/**
 * Created by winnerawan on 3/23/16.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @InjectView(R.id.avatar) NetworkImageView avatar;
    String name,email,uid;
    @InjectView(R.id.editUsername) EditText txtUsername;
    @InjectView(R.id.editEmail) EditText txtEmail;
    @InjectView(R.id.editPassword) EditText txtPass0;
    @InjectView(R.id.editConfirmPassword) EditText txtPass1;
    @InjectView(R.id.btnLapor) Button bLapor;
    @InjectView(R.id.btnCancel) Button bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        getAvatar();
        HashMap<String, String> user = db.getUserDetails();
        name=user.get("name");
        email=user.get("email");
        uid=user.get("uid");
        txtUsername.setText(name);
        txtEmail.setText(email);
        bLapor.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        avatar.setOnClickListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }
    @Override
    public void onClick(View v) {
        if(v == avatar){
            showFileChooser();
        }
        if (v == bCancel) {
            onBackPressed();
        }
        if (v == bLapor) {
            String u_name = txtUsername.getText().toString();
            String u_email = txtEmail.getText().toString();
            String u_pass1 = txtPass1.getText().toString();
            String u_pass0 = txtPass0.getText().toString();
            if ((!u_pass0.isEmpty() && (!u_pass1.isEmpty()))) {
                updateUserDetails(uid, u_name, u_email);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                alert.setTitle("Fill Password");
                alert.setMessage("Please fill password for update data");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        }

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                avatar.setImageBitmap(bitmap);
                setAvatar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setAvatar() {
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SET_AVATAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(SettingsActivity.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(SettingsActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String pp = getStringImage(bitmap);
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                params.put("profile_picture", pp);
                params.put("name", name);
                params.put("unique_id", uid);

                //returning parameters
                return params;

            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void updateUserDetails(final String uid, final String name, final String email) {
            final String tag_json_obj = "json_obj_req";
            final String url = AppConfig.UPDATE_USER_DETAILS;
            final Map<String, String> params = new HashMap<String, String>();
            params.put("unique_id", uid);
            params.put("name", name);
            params.put("email", email);

            StringRequest sr = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response.toString());
                            AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                            alert.setTitle("Update User Details");
                            alert.setMessage("Will not Effect Until You Logout or Clear Data");
                            alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    logoutUser();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error:" + error.getMessage());
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
            /*
        Map<String, String> data = new HashMap<String, String>();
        String update_name = txtUsername.getText().toString();
        String update_email = txtEmail.getText().toString();
        data.put("name", update_name);
        data.put("email", update_email);

        if (HttpRequest.post(AppConfig.UPDATE_USER_DETAILS + uid +"&name="+update_name +"&email="+update_email).form(data).created())
        Log.i(TAG, data.toString());
        */
        }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
