package co.ipb.adukerang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import co.ipb.adukerang.R;
import co.ipb.adukerang.controller.AppController;
import co.ipb.adukerang.model.Keluhan;

/**
 * Created by winnerawan on 4/1/16.
 */
public class CardKeluhanAdapter extends ArrayAdapter<Keluhan> {

    private static final String TAG = CardKeluhanAdapter.class.getSimpleName();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private List<Keluhan> cardList = new ArrayList<Keluhan>();

    static class CardViewHolder {
        ImageView foto;
        com.pkmmte.view.CircularImageView avatar;
        TextView textView;
        TextView textView2;
    }

    public CardKeluhanAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    @Override
    public void add(Keluhan object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Keluhan getItem(int index) {
        return this.cardList.get(index);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.card_item_one, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.foto = (ImageView)row.findViewById(R.id.bg_image);
            viewHolder.avatar = (com.pkmmte.view.CircularImageView)row.findViewById(R.id.avatar);
            viewHolder.textView = (TextView)row.findViewById(R.id.textView);
            viewHolder.textView2 = (TextView)row.findViewById(R.id.textView2);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        Keluhan kel = new Keluhan();
        viewHolder.textView.setText(kel.getUnique_id());
        viewHolder.textView2.setText(kel.getKeluhan());
        return row;
        }
    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
