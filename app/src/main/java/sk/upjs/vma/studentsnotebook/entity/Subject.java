package sk.upjs.vma.studentsnotebook.entity;

import android.database.Cursor;

import java.io.Serializable;

public class Subject implements Serializable {

    private Long id;

    private String name;

    public Subject() {
        // empty constructor
    }

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Subject newInstance(Cursor c) {
        Subject subject = new Subject();
        int _id = c.getInt(c.getColumnIndex("_id"));
        subject.setId((long) _id);
        String _name = c.getString(c.getColumnIndex("name"));
        subject.setName(_name);
        return subject;
    }

}
