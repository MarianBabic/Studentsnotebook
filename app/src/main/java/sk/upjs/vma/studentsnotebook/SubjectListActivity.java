package sk.upjs.vma.studentsnotebook;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SubjectListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView;

    private static final int SUBJECTS_LOADER_ID = 3;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        this.listView = findViewById(R.id.listViewSubjects);
        this.listView.setAdapter(initializeAdapter());
        getLoaderManager().initLoader(SUBJECTS_LOADER_ID, Bundle.EMPTY, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor c = (Cursor) adapter.getItem(position);
                Subject subject = Subject.newInstance(c);

                Log.e(SubjectListActivity.class.getName(), "LIST: " + subject);

                Intent intent = new Intent(SubjectListActivity.this,
                        SubjectDetailActivity.class);

                intent.putExtra("Subject", subject);

                startActivity(intent);
            }
        });
    }

    private ListAdapter initializeAdapter() {
        String[] from = {StudentsNotebookContract.Subject.NAME, StudentsNotebookContract.Subject._ID};
        // TODO: R.id.editTextSubjectName / R.id.cardText
        int[] to = {R.id.cardId, R.id.cardText};
        // TODO: R.layout.activity_subject_detail / R.layout.subject
        this.adapter = new SimpleCursorAdapter(this, R.layout.subject, null, from, to, 0);
        return this.adapter;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO


//        List<Subject> list = subjectDao.list();
//
//        ListAdapter adapter = new ArrayAdapter<Subject>(this,
//                android.R.layout.simple_list_item_1, list) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                TextView listItemView = (TextView) super.getView(position, convertView, parent);
//
//                Subject subject = getItem(position);
//
//                listItemView.setText(subject.getName());
//                return listItemView;
//            }
//        };
//
//        // adapter sa nastavuje v onResume, aby sa zoznam aktualizoval pri navrate z DetailActivity
//        listView.setAdapter(adapter);

        // TODO

//        this.listView = findViewById(R.id.listViewSubjects);
//        this.listView.setAdapter(initializeAdapter());
//        getLoaderManager().initLoader(SUBJECTS_LOADER_ID, Bundle.EMPTY, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subject_list_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addNewSubject) {
            Log.d("Hello", "vytvorenie novej aktivity");

            /*Uri uri = Uri.parse("https://www.upjs.sk/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);*/

            Intent intent = new Intent(this, SubjectDetailActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id != SUBJECTS_LOADER_ID) {
            throw new IllegalStateException("Invalid Subjects Loader with ID: " + id);
        }
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(StudentsNotebookContract.Subject.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
        // TODO: ked to odkomentujem pada subject list activity
//        cursor.setNotificationUri(getContentResolver(), StudentsNotebookContract.Subject.CONTENT_URI);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

}
