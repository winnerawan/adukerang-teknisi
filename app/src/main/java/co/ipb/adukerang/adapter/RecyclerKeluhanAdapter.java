package co.ipb.adukerang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.ipb.adukerang.R;
import co.ipb.adukerang.model.Keluhan;

/**
 * Created by winnerawan on 4/1/16.
 */
public class RecyclerKeluhanAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<Keluhan> listKel;
    private Context context;

    public RecyclerKeluhanAdapter(Context context, List<Keluhan> listKel) {
        this.listKel = listKel;
        this.context = context;
    }
        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_one, null);
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolders holder, int position) {
            holder.foto.setImageResource(R.drawable.iconadukerang);
            holder.avatar.setImageResource(R.drawable.iconadukerang);
            holder.textView.setText(listKel.get(position).getUnique_id());
            holder.textView2.setText(listKel.get(position).getKeluhan());
        }

        @Override
        public int getItemCount() {
            return this.listKel.size();
        }
    }