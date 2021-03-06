package mx.saudade.discovermusicapp.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import mx.saudade.discovermusicapp.AnalyticsApplication;
import mx.saudade.discovermusicapp.R;
import mx.saudade.discovermusicapp.fragments.AboutFragment;
import mx.saudade.discovermusicapp.utils.NavigationUtils;

/**
 * Created by angie on 7/3/16.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        NavigationUtils.loadFragment(this, new AboutFragment());
        ((AnalyticsApplication) getApplication()).trackScreen(getString(R.string.about));
    }
}
