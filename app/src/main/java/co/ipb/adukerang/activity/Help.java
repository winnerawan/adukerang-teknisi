package co.ipb.adukerang.activity;

/**
 * Created by winnerawan on 5/22/16.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.ipb.adukerang.R;
import co.ipb.adukerang.handler.Version;

public class Help extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(View.generateViewId());
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));

        getFragmentManager()
                .beginTransaction()
                .add(frame.getId(), new CreditsFragment())
                .commit();
        Toolbar toolbar = (Toolbar)LayoutInflater.from(this).inflate(R.layout.tool, frame, false);
        frame.addView(toolbar, 0);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static class CreditsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.help);

            for(int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
                PreferenceCategory cat = (PreferenceCategory) getPreferenceScreen().getPreference(i);

                for(int j = 0; j < cat.getPreferenceCount(); j++){
                    Preference pref = cat.getPreference(j);
                    pref.setOnPreferenceClickListener(this);
                }
            }
        }


        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch(preference.getKey()) {
                case "author":
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://winnerawan.net")));
                    return true;
                case "github":
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/mrbimc/SELinuxModeChanger")));
                    return true;
                case "gpl3":
                    showDialog(getString(R.string.help0), "file:///android_asset/panduan.txt");
                    return true;
                case "apache2":
                    Intent i = new Intent(getActivity(), Version.class);
                    getActivity().startActivity(i);
                    return true;
                case "rootshell":

                    return true;
                default:
                    return false;
            }
        }

        private void showDialog(String title, String url) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle(title);
            WebView wv = new WebView(getActivity());
            wv.loadUrl(url);
            wv.setHorizontalScrollBarEnabled(false);
            alert.setView(wv);
            alert.setNegativeButton(getActivity().getString(R.string.close), null);
            alert.show();
        }
    }
}
