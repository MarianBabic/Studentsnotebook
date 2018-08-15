package sk.upjs.vma.studentsnotebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class SubjectDetailActivity extends AppCompatActivity {

    private SubjectDao subjectDao = SubjectDao.INSTANCE;
    private Subject subject;

    private EditText editTextSubjectName;

    private boolean ignoreSaveOnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        editTextSubjectName = findViewById(R.id.editTextSubjectName);

        Long subjectId = (Long) getIntent().getSerializableExtra(
                SubjectListActivity.SUBJECT_ID_EXTRA);

        Log.d("DETAIL", "intent includes " + subjectId);

        if (subjectId == null) {
            // pripad ak vytvaram novu ulohu
            subject = new Subject();
        } else {
            // pripad ak editujem existujucu ulohu
            subject = subjectDao.getSubject(subjectId);
        }

        editTextSubjectName.setText(subject.getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSubject();
    }

    private void saveSubject() {
        // v pripade ze sa odstranuje subject a nema byt ulozeny
        if (ignoreSaveOnFinish) {
            ignoreSaveOnFinish = false;
            return;
        }

        subject.setName(editTextSubjectName.getText().toString());
        subjectDao.saveOrUpdate(subject);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subject_detail_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_delete) {
            subjectDao.delete(subject);
            // priznak aby sa subject neukladal po navrate z detail aktivity
            ignoreSaveOnFinish = true;
            finish();
            return true;
        }

        // v tomto pripade to funguje aj bez nasledovneho kodu
        if (id == android.R.id.home) {
            finish();
            // https://developer.android.com/training/implementing-navigation/ancestral.html
            // dokumentacia odporuca pouzit tuto metodu, ktora obsahuje aj volanie finish
            // NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
