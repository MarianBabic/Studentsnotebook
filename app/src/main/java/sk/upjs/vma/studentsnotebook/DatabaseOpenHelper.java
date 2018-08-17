package sk.upjs.vma.studentsnotebook;

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

        insertSampleEntry(db, "vma1");
        insertSampleEntry(db, "paz1a");
        insertSampleEntry(db, "paz1b");
    }

    private void insertSampleEntry(SQLiteDatabase db, String description) {
        ContentValues values = new ContentValues();
        values.put(StudentsNotebookContract.Subject.NAME, description);
        values.put(StudentsNotebookContract.Subject.TIMESTAMP, System.currentTimeMillis() / 1000);
        db.insert(StudentsNotebookContract.Subject.TABLE_NAME, null, values);
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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // do nothing
    }

}
