package co.ipb.adukerang.adapter;

/**
 * Created by winnerawan on 4/1/16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.ipb.adukerang.R;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView foto;
    public com.pkmmte.view.CircularImageView avatar;
    public TextView textView;
    public TextView textView2;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        foto = (ImageView)itemView.findViewById(R.id.bg_image);
        avatar = (com.pkmmte.view.CircularImageView)itemView.findViewById(R.id.avatar);
        textView = (TextView)itemView.findViewById(R.id.textView);
        textView2 = (TextView)itemView.findViewById(R.id.textView2);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}