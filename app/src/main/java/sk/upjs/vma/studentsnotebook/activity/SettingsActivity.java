package sk.upjs.vma.studentsnotebook.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;

import sk.upjs.vma.studentsnotebook.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        String languages_key = getResources().getString(R.string.languages_key);
        this.findPreference(languages_key).setOnPreferenceChangeListener(
                new OnPreferenceChangeListener() {

                    @Override
                    public boolean onPreferenceChange(Preference preference,
                                                      Object newValue) {
                        switchLanguage(newValue.toString());
                        return true;
                    }
                });

        final String music_chkbox_key = getResources().getString(R.string.music_chkbox_key);
        this.findPreference(music_chkbox_key).setOnPreferenceChangeListener(
                new OnPreferenceChangeListener() {

                    @Override
                    public boolean onPreferenceChange(Preference preference,
                                                      Object newValue) {
                        if (Boolean.valueOf(newValue.toString()) == true) {
                            MainActivity.startMusic(getBaseContext());
                        } else {
                            MainActivity.stopMusic();
                        }
                        return true;
                    }
                });

        String music_key = getResources().getString(R.string.music_key);
        this.findPreference(music_key).setOnPreferenceChangeListener(
                new OnPreferenceChangeListener() {

                    @Override
                    public boolean onPreferenceChange(Preference preference,
                                                      Object newValue) {
                        switch (newValue.toString()) {
                            case ("drums"):
                                MainActivity.musicfile = R.raw.drums;
                                break;
                            case ("synth_bass"):
                                MainActivity.musicfile = R.raw.synth_bass;
                        }

                        if (MainActivity.mediaPlayer != null) {
                            MainActivity.stopMusic();
                            MainActivity.startMusic(getBaseContext());
                        }
                        return true;
                    }
                });
    }

    /**
     * this method was inspired by this code:
     * https://stackoverflow.com/questions/18582264/can-you-programmatically-switch-between-xml-resource-files-on-android
     */
    private void switchLanguage(String newLanguage) {
        Resources res = getResources();
        Configuration conf = res.getConfiguration();

        String languages_en = getResources().getString(R.string.languages_en);
        String languages_sk = getResources().getString(R.string.languages_sk);
        Locale locale = newLanguage.equals(languages_sk) ? new Locale("sk") : new Locale("en");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(locale);
        } else {
            Locale.setDefault(locale);
            conf.locale = locale;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getApplicationContext().createConfigurationContext(conf);
        } else {
            res.updateConfiguration(conf, res.getDisplayMetrics());
        }

        Log.e("LANGUAGE", conf.locale.toString());
    }

}
