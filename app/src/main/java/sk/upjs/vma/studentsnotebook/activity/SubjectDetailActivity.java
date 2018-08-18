package sk.upjs.vma.studentsnotebook.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import sk.upjs.vma.studentsnotebook.R;
import sk.upjs.vma.studentsnotebook.localdb.StudentsNotebookContract;
import sk.upjs.vma.studentsnotebook.entity.Subject;

public class SubjectDetailActivity extends AppCompatActivity {

    private Subject subject;
    private EditText editTextSubjectName;
    private boolean ignoreSaveOnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        editTextSubjectName = findViewById(R.id.editTextSubjectName);

        // pripad ak editujem existujucu ulohu
        // tiez aj: getIntent().getExtras().getSerializable("Subject");
        subject = (Subject) getIntent().getSerializableExtra("Subject");

        // pripad ak vytvaram novu ulohu
        if (subject == null)
            subject = new Subject();

        Log.e(SubjectDetailActivity.class.getName(), "DETAIL: " + subject);

        editTextSubjectName.setText(subject.getName());

        getNotes();
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
        ContentValues values = new ContentValues();
        values.put(StudentsNotebookContract.Subject.NAME, subject.getName());

        // save new subject
        if (subject.getId() == null) {
            insertIntoContentProvider(values);
        }
        // update existing subject
        else {
            getContentResolver().update(StudentsNotebookContract.Subject.CONTENT_URI, values, Long.toString(subject.getId()), null);
        }
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
            delete();
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

    private void getNotes() {
        AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                Toast.makeText(SubjectDetailActivity.this, "Loaded notes: " + cursor.getCount(), Toast.LENGTH_LONG).show();
            }
        };
        queryHandler.startQuery(0, null, StudentsNotebookContract.Note.CONTENT_URI, null, Long.toString(subject.getId()), null, null);
    }

    private void insertIntoContentProvider(ContentValues values) {
        // normal
        //getContentResolver().insert(StudentsNotebookContract.Subject.CONTENT_URI, values);

        // asynchronne
        // abstraktna trieda
        AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(SubjectDetailActivity.this, "Saved: " + cookie.toString(), Toast.LENGTH_LONG).show();
            }
        };
        queryHandler.startInsert(0, subject, StudentsNotebookContract.Subject.CONTENT_URI, values);
    }

    private void delete() {
        // normal
        //getContentResolver().delete(StudentsNotebookContract.Subject.CONTENT_URI, Long.toString(subject.getId()), null);

        // asynchronne
        // abstraktna trieda
        AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                Toast.makeText(SubjectDetailActivity.this, "Deleted: " + cookie.toString(), Toast.LENGTH_LONG).show();
            }
        };
        queryHandler.startDelete(0, subject, StudentsNotebookContract.Subject.CONTENT_URI, Long.toString(subject.getId()), null);
    }

}
