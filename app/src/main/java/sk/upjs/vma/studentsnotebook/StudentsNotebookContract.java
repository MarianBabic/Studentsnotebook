package sk.upjs.vma.studentsnotebook;

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

}
