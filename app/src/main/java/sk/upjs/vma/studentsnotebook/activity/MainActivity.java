package sk.upjs.vma.studentsnotebook.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import sk.upjs.vma.studentsnotebook.R;

public class MainActivity extends AppCompatActivity {

    private Intent subjectsActivityIntent;
    private Intent settingsActivityIntent;
    private Intent aboutActivityIntent;

    static MediaPlayer mediaPlayer;
    static int musicfile = R.raw.drums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subjectsActivityIntent = new Intent(this, SubjectListActivity.class);
        settingsActivityIntent = new Intent(this, SettingsActivity.class);
        aboutActivityIntent = new Intent(this, AboutActivity.class);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case (R.id.subjectsButton):
                startActivity(subjectsActivityIntent);
                break;
            case (R.id.settingsButton):
                startActivity(settingsActivityIntent);
                break;
            case (R.id.aboutButton):
                startActivity(aboutActivityIntent);
                break;
            case (R.id.quitButton):
                System.exit(0);
        }
    }

}
