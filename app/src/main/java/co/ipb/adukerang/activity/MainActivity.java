package co.ipb.adukerang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;

/**
 * Created by winnerawan on 3/23/16.
 */
public class MainActivity extends ActionBarActivity {

    private SQLiteHandler db;
    private SessionManager session;
    @InjectView(R.id.btn_signup) Button register;
    @InjectView(R.id.btn_login) Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(reg);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(log);
            }
        });
        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent dashboard = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(dashboard);
            finish();
        }
    }
}
