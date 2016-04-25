package co.ipb.adukerang.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.ipb.adukerang.R;
import co.ipb.adukerang.adapter.CardKeluhanAdapter;
import co.ipb.adukerang.adapter.RecyclerKeluhanAdapter;
import co.ipb.adukerang.model.Keluhan;


public class ComplaintsActivity extends AppCompatActivity {

    private static final String TAG = ComplaintsActivity.class.getSimpleName();
    private CardKeluhanAdapter cardKeluhanAdapter;
    private ListView listView;
    private LinearLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        List<Keluhan> lk = new ArrayList<Keluhan>();
        lLayout = new LinearLayoutManager(ComplaintsActivity.this);
        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        RecyclerKeluhanAdapter rcAdapter = new RecyclerKeluhanAdapter(ComplaintsActivity.this, lk);
        rView.setAdapter(rcAdapter);


    }
}
