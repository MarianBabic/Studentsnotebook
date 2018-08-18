package sk.upjs.vma.studentsnotebook.localdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class StudentsNotebookContentProvider extends ContentProvider {

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
        Cursor cursor = db.query(StudentsNotebookContract.Subject.TABLE_NAME, null,
                null, null, null, null, null);
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