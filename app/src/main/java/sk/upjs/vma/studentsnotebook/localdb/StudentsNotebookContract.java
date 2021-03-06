package sk.upjs.vma.studentsnotebook.localdb;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface StudentsNotebookContract {

    String AUTHORITY = "sk.upjs.vma.studentsnotebook";

    interface Subject extends BaseColumns {
        String TABLE_NAME = "subject";

        String NAME = "name";
        String TIMESTAMP = "timestamp";

        Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    interface Note extends BaseColumns {
        String TABLE_NAME = "note";

        String SUBJECT_ID = "subject_id";
        String TITLE = "title";
        String CONTENT = "content";
        String TIMESTAMP = "timestamp";

        Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

}
