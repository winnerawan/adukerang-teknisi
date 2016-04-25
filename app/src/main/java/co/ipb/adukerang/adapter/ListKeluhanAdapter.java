package co.ipb.adukerang.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.fragment.KeluhanFragment;
import co.ipb.adukerang.helper.CircledNetworkImageView;
import co.ipb.adukerang.model.Keluhan;

/**
 * Created by winnerawan on 3/26/16.
 */
public class ListKeluhanAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private KeluhanFragment fragment;
    private List<Keluhan> listKeluhan;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListKeluhanAdapter(KeluhanFragment fragment, List<Keluhan> listKeluhan) {
        this.fragment=fragment;
        this.listKeluhan=listKeluhan;

    }
    @Override
    public int getCount() {
        return listKeluhan.size();
    }

    @Override
    public Object getItem(int location) {
        return listKeluhan.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String pp_default = "http://www.jordanhardware.com/styles/default/xenforo/avatars/avatar_m.png";
        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_keluhan, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        TextView namaPengeluh = (TextView) convertView.findViewById(R.id.id_keluhan);
        NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView keluhan = (TextView) convertView.findViewById(R.id.keluhan);
        CircledNetworkImageView avatar = (CircledNetworkImageView) convertView.findViewById(R.id.avatar);
        TextView idk = (TextView)convertView.findViewById(R.id.idk);
        View v_status = convertView.findViewById(R.id.view_status);
        TextView tvstatuskel = (TextView)convertView.findViewById(R.id.statuskeluhan);
        TextView tvs = (TextView)convertView.findViewById(R.id.tvstatus);



        Keluhan k = listKeluhan.get(position);
        tvs.setText(k.getStatus());
        tvstatuskel.setText(k.getStatus());
        image.setImageUrl(k.getFoto(), imageLoader);
        namaPengeluh.setText(k.getName());
        keluhan.setText(k.getKeluhan());
        avatar.setImageUrl(k.getProfile_picture(), imageLoader);
        if (k.getProfile_picture().equals("")) {
            avatar.setImageUrl(pp_default, imageLoader);

        }
        idk.setText(k.getId_keluhan());
        tvs.setText(k.getStatus());
        String finalStatus = tvs.getText().toString();

        if (finalStatus.equals("PENDING")) {
            v_status.setBackgroundColor(Color.parseColor("#2196f3"));
        } else if (finalStatus.equals("PROSES")) {
            v_status.setBackgroundColor(Color.parseColor("#009688"));
        } else if (finalStatus.equals("SELESAI")) {
            v_status.setBackgroundColor(Color.parseColor("#ff5722"));
        }

        return convertView;
    }
}
