package co.ipb.adukerang.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.activity.LaporActivity;
import co.ipb.adukerang.activity.ProfileActivity;
import co.ipb.adukerang.handler.SQLiteHandler;
import co.ipb.adukerang.handler.SessionManager;

/**
 * Created by winnerawan on 3/27/16.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;


    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewProfile = inflater.inflate(R.layout.profile_fragment, container, false);
        db = new SQLiteHandler(getActivity().getApplicationContext());
        session = new SessionManager(getActivity().getApplicationContext());

        TextView tv_name = (TextView) viewProfile.findViewById(R.id.fname);
        TextView tv_email = (TextView) viewProfile.findViewById(R.id.femail);
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String email = user.get("email");

        tv_name.setText(name);
        tv_email.setText(email);

        return viewProfile;
    }
}
