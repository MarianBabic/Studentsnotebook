package sk.upjs.vma.studentsnotebook;

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

}
