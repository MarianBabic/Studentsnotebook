package sk.upjs.vma.studentsnotebook.localdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StudentsNotebookContentProvider extends ContentProvider {

    // uri matcher
    private static final int SUBJECTS = 1;
    private static final int NOTES = 2;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(StudentsNotebookContract.AUTHORITY, "subject", SUBJECTS);
        sURIMatcher.addURI(StudentsNotebookContract.AUTHORITY, "note", NOTES);
    }

    private DatabaseOpenHelper databaseOpenHelper;

    @Override
    public boolean onCreate() {
        databaseOpenHelper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = null;

        int match = sURIMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                Log.e("MATCHER ", "SUBJECTS");
                cursor = db.query(StudentsNotebookContract.Subject.TABLE_NAME, null,
                        null, null, null, null, null);
                break;
            case NOTES:
                Log.e("MATCHER", "NOTES");
                String[] whereArgs = {selection};
                cursor = db.query(StudentsNotebookContract.Note.TABLE_NAME,
                        null,
                        StudentsNotebookContract.Note.SUBJECT_ID + "=?",
                        whereArgs,
                        null,
                        null,
                        null,
                        null);
                break;
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseOpenHelper.getWritableDatabase();
        String[] whereArgs = {selection};
        int affectedRows = db.delete(
                StudentsNotebookContract.Subject.TABLE_NAME,
                StudentsNotebookContract.Subject._ID + "=?",
                whereArgs);
        getContext().getContentResolver().notifyChange(StudentsNotebookContract.Subject.CONTENT_URI, null);
        return affectedRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseOpenHelper.getWritableDatabase();
        long id = db.insert(StudentsNotebookContract.Subject.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(StudentsNotebookContract.Subject.CONTENT_URI, null);
        return Uri.withAppendedPath(StudentsNotebookContract.Subject.CONTENT_URI, String.valueOf(id));
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = databaseOpenHelper.getWritableDatabase();
        String[] whereArgs = {selection};
        int affectedRows = db.update(
                StudentsNotebookContract.Subject.TABLE_NAME,
                values,
                StudentsNotebookContract.Subject._ID + "=?",
                whereArgs);
        getContext().getContentResolver().notifyChange(StudentsNotebookContract.Subject.CONTENT_URI, null);
        return affectedRows;
    }

}
