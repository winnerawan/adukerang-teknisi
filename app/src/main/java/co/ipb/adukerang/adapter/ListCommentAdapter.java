package co.ipb.adukerang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.util.List;

import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.fragment.CommentFragment;
import co.ipb.adukerang.fragment.KeluhanFragment;
import co.ipb.adukerang.helper.CircledNetworkImageView;
import co.ipb.adukerang.model.Comment;
import co.ipb.adukerang.model.Keluhan;

/**
 * Created by winnerawan on 4/17/16.
 */
public class ListCommentAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private CommentFragment fragment;
    private List<Comment> listComment;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListCommentAdapter(CommentFragment fragment, List<Comment> listComment) {
        this.fragment=fragment;
        this.listComment=listComment;

    }
    @Override
    public int getCount() {
        return listComment.size();
    }

    @Override
    public Object getItem(int location) {
        return listComment.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_comment, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        /*
        TextView namaPengeluh = (TextView) convertView.findViewById(R.id.id_keluhan);
        NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView keluhan = (TextView) convertView.findViewById(R.id.keluhan);
        CircledNetworkImageView avatar = (CircledNetworkImageView) convertView.findViewById(R.id.avatar);
        TextView idk = (TextView)convertView.findViewById(R.id.idk);
        View v_status = convertView.findViewById(R.id.view_status);
        TextView tvstatuskel = (TextView)convertView.findViewById(R.id.statuskeluhan);
        TextView tvs = (TextView)convertView.findViewById(R.id.tvstatus);
        */

        TextView nama = (TextView)convertView.findViewById(R.id.title);
        CircledNetworkImageView avatar = (CircledNetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView comment = (TextView)convertView.findViewById(R.id.comment);


        String pp_default = "http://www.jordanhardware.com/styles/default/xenforo/avatars/avatar_m.png";


        Comment c = listComment.get(position);
        nama.setText(c.getName());
        comment.setText(c.getKomentar());
        avatar.setImageUrl(c.getProfilePicture(), imageLoader);
        if (c.getProfilePicture().equals("")) {
            avatar.setImageUrl(pp_default, imageLoader);

        }
        
        return convertView;
    }
}
