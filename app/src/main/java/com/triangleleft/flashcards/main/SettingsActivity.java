package com.triangleleft.flashcards.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.ButterKnife;
import com.triangleleft.flashcards.R;
import com.triangleleft.flashcards.common.DrawableUtils;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable drawable = DrawableUtils
            .getTintedDrawable(this, R.drawable.ic_arrow_back_black_24dp, R.color.textColorPrimary);
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.content, new GeneralPreferenceFragment())
            .commit();
    }

    public static class GeneralPreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onCreatePreferences(Bundle bundle, String string) {
            addPreferencesFromResource(R.xml.pref_general);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
