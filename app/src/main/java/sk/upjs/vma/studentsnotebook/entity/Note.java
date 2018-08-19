package sk.upjs.vma.studentsnotebook.entity;

import android.database.Cursor;

import java.io.Serializable;

public class Note implements Serializable {

    private Long id;

    private Long subjectId;

    private String title;

    private String content;

    public Note() {
        // empty constructor
    }

    public Note(Long id, Long subjectId, String title, String content) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.content = content;
    }

    public Note(Long subjectId, String title, String content) {
        this.subjectId = subjectId;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return subjectId + ": " + title;
    }

    public static Note newInstance(Cursor c) {
        Note note = new Note();
        int _id = c.getInt(c.getColumnIndex("_id"));
        note.setId((long) _id);
        int _subjectId = c.getInt(c.getColumnIndex("_subject_id"));
        note.setSubjectId((long) _subjectId);
        String _title = c.getString(c.getColumnIndex("title"));
        note.setTitle(_title);
        String _content = c.getString(c.getColumnIndex("content"));
        note.setContent(_content);
        return note;
    }
}
