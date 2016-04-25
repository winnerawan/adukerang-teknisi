package co.ipb.adukerang.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.ipb.adukerang.R;
import co.ipb.adukerang.activity.DashboardActivity;
import co.ipb.adukerang.activity.DetailsActivity;
import co.ipb.adukerang.adapter.ListCommentAdapter;
import co.ipb.adukerang.adapter.ListKeluhanAdapter;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.model.Comment;
import co.ipb.adukerang.model.Keluhan;

/**
 * Created by winnerawan on 4/17/16.
 */
public class CommentFragment extends ListFragment {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Comment> listComment = new ArrayList<Comment>();
    private ListCommentAdapter adapter;
    public JSONObject obj;
    String id_keluhan;

    public CommentFragment() {

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewKeluhan = inflater.inflate(R.layout.comment_fragment, container, false);

        ListView lv_keluhan = (ListView) viewKeluhan.findViewById(android.R.id.list);
        adapter = new ListCommentAdapter(this, listComment);
        lv_keluhan.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Bundle select = getActivity().getIntent().getExtras();
        id_keluhan = select.getString("id_keluhan");
        
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest foodReq = new JsonArrayRequest(AppConfig.URL_GET_COMMENT+id_keluhan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                hidePDialog();
                // Parsing json
                for (int i = 0; i < response.length(); i++)
                    try {
                        obj = response.getJSONObject(i);
                        Comment comment =  new Comment();

                        comment.setId_keluhan(obj.getInt("id_keluhan"));
                        comment.setEmail(obj.getString("email"));
                        comment.setId_komentar(obj.getInt("id_komentar"));
                        comment.setName(obj.getString("name"));
                        comment.setKomentar(obj.getString("komentar"));
                        comment.setProfilePicture(obj.getString("profile_picture"));

                        listComment.add(comment);
                        adapter.notifyDataSetChanged();
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
        adapter.notifyDataSetChanged();
        return viewKeluhan;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //selected = ((SetanTextView)v.findViewById(R.id.title)).getText().toString();
        //String image = ((Drink)drinkMenus.get(position)).getThumbnailUrl();
        String selected_idk = ((TextView)v.findViewById(R.id.idk)).getText().toString();

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