package sk.upjs.vma.studentsnotebook.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "students_notebook";
    public static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSubject());
        db.execSQL(createTableNote());

        insertSampleSubject(db, "vma1");
        insertSampleSubject(db, "paz1a");
        insertSampleSubject(db, "paz1b");

        insertSampleNote(db, 1, "sample1", "sample1");
        insertSampleNote(db, 1, "sample2", "sample2");
        insertSampleNote(db, 2, "sample3", "sample3");
        insertSampleNote(db, 2, "sample4", "sample4");
        insertSampleNote(db, 2, "sample5", "sample5");
        insertSampleNote(db, 3, "sample6", "sample6");
    }

    private void insertSampleSubject(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(StudentsNotebookContract.Subject.NAME, name);
        values.put(StudentsNotebookContract.Subject.TIMESTAMP, System.currentTimeMillis() / 1000);
        db.insert(StudentsNotebookContract.Subject.TABLE_NAME, null, values);
    }

    private void insertSampleNote(SQLiteDatabase db, Integer subjectId, String title, String content) {
        ContentValues values = new ContentValues();
        values.put(StudentsNotebookContract.Note.SUBJECT_ID, subjectId);
        values.put(StudentsNotebookContract.Note.TITLE, title);
        values.put(StudentsNotebookContract.Note.CONTENT, content);
        values.put(StudentsNotebookContract.Note.TIMESTAMP, System.currentTimeMillis() / 1000);
        db.insert(StudentsNotebookContract.Note.TABLE_NAME, null, values);
    }

    private String createTableSubject() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s INTEGER)";
        return String.format(sqlTemplate,
                StudentsNotebookContract.Subject.TABLE_NAME,
                StudentsNotebookContract.Subject._ID,
                StudentsNotebookContract.Subject.NAME,
                StudentsNotebookContract.Subject.TIMESTAMP
        );
    }

    private String createTableNote() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s INTEGER REFERENCES " + StudentsNotebookContract.Subject.TABLE_NAME + " (" + StudentsNotebookContract.Subject._ID + "),"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s INTEGER)";
        return String.format(sqlTemplate,
                StudentsNotebookContract.Note.TABLE_NAME,
                StudentsNotebookContract.Note._ID,
                StudentsNotebookContract.Note.SUBJECT_ID,
                StudentsNotebookContract.Note.TITLE,
                StudentsNotebookContract.Note.CONTENT,
                StudentsNotebookContract.Note.TIMESTAMP
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // do nothing
    }

}
