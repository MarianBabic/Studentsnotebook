package sk.upjs.vma.studentsnotebook.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import sk.upjs.vma.studentsnotebook.R;
import sk.upjs.vma.studentsnotebook.entity.Note;
import sk.upjs.vma.studentsnotebook.localdb.StudentsNotebookContract;

public class NoteDetailActivity extends AppCompatActivity {

    private Note note;
    private EditText editTextNoteTitle;
    private EditText editTextNoteContent;
    private boolean ignoreSaveOnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);

        // pripad ak editujem existujucu poznamku
        // tiez aj: getIntent().getExtras().getSerializable("note");
        note = (Note) getIntent().getSerializableExtra("note");

        // pripad ak vytvaram novu poznamku
        if (note == null) {
            note = new Note();
            Long subjectId = (Long) getIntent().getSerializableExtra("subject_id");
            note.setSubjectId(subjectId);
        }

        editTextNoteTitle.setText(note.getTitle());
        editTextNoteContent.setText(note.getContent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNote();
    }

    private void saveNote() {
        // v pripade ze sa odstranuje note a nema byt ulozeny
        if (ignoreSaveOnFinish) {
            ignoreSaveOnFinish = false;
            return;
        }

        note.setTitle(editTextNoteTitle.getText().toString());
        note.setContent(editTextNoteContent.getText().toString());
        ContentValues values = new ContentValues();
        values.put(StudentsNotebookContract.Note.SUBJECT_ID, note.getSubjectId());
        values.put(StudentsNotebookContract.Note.TITLE, note.getTitle());
        values.put(StudentsNotebookContract.Note.CONTENT, note.getContent());

        // save new note
        if (note.getId() == null) {
            insertNote(values);
        }
        // update existing note
        else {
            getContentResolver().update(StudentsNotebookContract.Note.CONTENT_URI, values, Long.toString(note.getId()), null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_detail_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.deleteNote) {
            deleteNote();
            // priznak aby sa note neukladal po navrate z detail aktivity
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

    private void insertNote(ContentValues values) {
        AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(NoteDetailActivity.this, "New Note Saved: " + cookie.toString(), Toast.LENGTH_LONG).show();
            }
        };
        queryHandler.startInsert(0, note, StudentsNotebookContract.Note.CONTENT_URI, values);
    }

    private void deleteNote() {
        AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onDeleteComplete(int token, Object cookie, int result) {
                Toast.makeText(NoteDetailActivity.this, "Note Deleted: " + cookie.toString(), Toast.LENGTH_LONG).show();
            }
        };
        queryHandler.startDelete(0, note, StudentsNotebookContract.Note.CONTENT_URI, Long.toString(note.getId()), null);
    }

}
