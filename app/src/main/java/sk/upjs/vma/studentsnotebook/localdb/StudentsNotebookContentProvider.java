package sk.upjs.vma.studentsnotebook.localdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import sk.upjs.vma.studentsnotebook.R;

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
                cursor = db.query(StudentsNotebookContract.Subject.TABLE_NAME, null,
                        null, null, null, null, null);
                break;
            case NOTES:
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
            default:
                showDbError();
        }

        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseOpenHelper.getWritableDatabase();
        String[] whereArgs = {selection};
        int affectedRows = 0;

        int match = sURIMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                affectedRows = db.delete(
                        StudentsNotebookContract.Subject.TABLE_NAME,
                        StudentsNotebookContract.Subject._ID + "=?",
                        whereArgs);
                getContext().getContentResolver().notifyChange(StudentsNotebookContract.Subject.CONTENT_URI, null);
                break;
            case NOTES:
                affectedRows = db.delete(
                        StudentsNotebookContract.Note.TABLE_NAME,
                        StudentsNotebookContract.Subject._ID + "=?",
                        whereArgs);
                getContext().getContentResolver().notifyChange(StudentsNotebookContract.Note.CONTENT_URI, null);
                break;
            default:
                showDbError();
        }

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
        long id;
        Uri result = null;

        int match = sURIMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                id = db.insert(StudentsNotebookContract.Subject.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(StudentsNotebookContract.Subject.CONTENT_URI, null);
                result = Uri.withAppendedPath(StudentsNotebookContract.Subject.CONTENT_URI, String.valueOf(id));
                break;
            case NOTES:
                id = db.insert(StudentsNotebookContract.Note.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(StudentsNotebookContract.Note.CONTENT_URI, null);
                result = Uri.withAppendedPath(StudentsNotebookContract.Note.CONTENT_URI, String.valueOf(id));
                break;
            default:
                showDbError();
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = databaseOpenHelper.getWritableDatabase();
        String[] whereArgs = {selection};
        int affectedRows = 0;

        int match = sURIMatcher.match(uri);
        switch (match) {
            case SUBJECTS:
                affectedRows = db.update(
                        StudentsNotebookContract.Subject.TABLE_NAME,
                        values,
                        StudentsNotebookContract.Subject._ID + "=?",
                        whereArgs);
                getContext().getContentResolver().notifyChange(StudentsNotebookContract.Subject.CONTENT_URI, null);
                break;
            case NOTES:
                affectedRows = db.update(
                        StudentsNotebookContract.Note.TABLE_NAME,
                        values,
                        StudentsNotebookContract.Note._ID + "=?",
                        whereArgs);
                getContext().getContentResolver().notifyChange(StudentsNotebookContract.Note.CONTENT_URI, null);
                break;
            default:
                showDbError();
        }

        return affectedRows;
    }

    private void showDbError() {
        Toast.makeText(getContext(), R.string.db_error, Toast.LENGTH_LONG).show();
    }

}
