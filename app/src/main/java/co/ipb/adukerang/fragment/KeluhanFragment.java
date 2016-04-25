package co.ipb.adukerang.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.ipb.adukerang.R;
import co.ipb.adukerang.activity.DashboardActivity;
import co.ipb.adukerang.activity.DetailsActivity;
import co.ipb.adukerang.adapter.ListKeluhanAdapter;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.model.Keluhan;

/**
 * Created by winnerawan on 3/26/16.
 */
public class KeluhanFragment extends ListFragment {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Keluhan> listKeluhan = new ArrayList<Keluhan>();
    private ListKeluhanAdapter adapter;
    public JSONObject obj;

    public KeluhanFragment() {

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewKeluhan = inflater.inflate(R.layout.keluhan_fragment, container, false);

        ListView lv_keluhan = (ListView) viewKeluhan.findViewById(android.R.id.list);
        adapter = new ListKeluhanAdapter(this, listKeluhan);
        lv_keluhan.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest foodReq = new JsonArrayRequest(AppConfig.URL_GET_KELUHAN, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                hidePDialog();
                // Parsing json
                for (int i = 0; i < response.length(); i++)
                    try {
                        obj = response.getJSONObject(i);
                        Keluhan keluhan = new Keluhan();
                        keluhan.setId_keluhan(obj.getString("id_keluhan"));
                        keluhan.setName(obj.getString("name"));
                        keluhan.setFoto(obj.getString("foto"));
                        keluhan.setKeluhan(obj.getString("keluhan"));
                        keluhan.setProfile_picture(obj.getString("profile_picture"));
                        keluhan.setStatus(obj.getString("status"));
                        listKeluhan.add(keluhan);
                        //View vv = viewKeluhan.findViewById(R.id.view_status);
                        //vv.setBackgroundColor(Color.RED);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                VolleyLog.e("Error: ", error.toString());
                hidePDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(foodReq);
        return viewKeluhan;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //selected = ((SetanTextView)v.findViewById(R.id.title)).getText().toString();
        //String image = ((Drink)drinkMenus.get(position)).getThumbnailUrl();
        String selected_idk = ((TextView)v.findViewById(R.id.idk)).getText().toString();
        String foto = ((Keluhan)listKeluhan.get(position)).getFoto();
        String plpr = ((Keluhan)listKeluhan.get(position)).getName();
        String pp = ((Keluhan)listKeluhan.get(position)).getProfile_picture();

        String ket_kel = ((Keluhan)listKeluhan.get(position)).getKeluhan();
        //Toast toast = Toast.makeText(getActivity(), selected_idk, Toast.LENGTH_SHORT);
        //toast.show();
        Intent details = new Intent(getActivity(), DetailsActivity.class);
        details.putExtra("id_keluhan", selected_idk);
        details.putExtra("pelapor",plpr);
        details.putExtra("id_keluhan", selected_idk);
        details.putExtra("foto", foto);
        details.putExtra("profile_picture",pp);
        details.putExtra("keluhan", ket_kel);
        startActivity(details);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }

}