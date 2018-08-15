package sk.upjs.vma.studentsnotebook;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public enum SubjectDao {

    INSTANCE;

    private List<Subject> subjects = new LinkedList<>();

    private long idGenerator = 0;

    SubjectDao() {
        Subject vma = new Subject("VMA");
        saveOrUpdate(vma);

        Subject paz1a = new Subject("PAZ1a");
        saveOrUpdate(paz1a);
    }

    public void saveOrUpdate(Subject subject) {
        if (subject.getId() == null) {
            subject.setId(idGenerator++);
            subjects.add(subject);
        } else {
            Iterator<Subject> iterator = subjects.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Subject s = iterator.next();
                if (s.getId().equals(subject.getId())) {
                    iterator.remove();
                    break;
                }
                index++;
            }
            subjects.add(index, subject);
        }
    }

    public List<Subject> list() {
        return new LinkedList<>(this.subjects);
    }

    public Subject getSubject(long subjectId) {
        for (Subject subject : this.subjects) {
            if (subject.getId() == subjectId) {
                return subject;
            }
        }
        return null;
    }

    public void delete(Subject subject) {
        Iterator<Subject> iterator = this.subjects.iterator();
        while (iterator.hasNext()) {
            Subject s = iterator.next();
            if (s.getId() == subject.getId()) {
                iterator.remove();
            }
        }
    }

}
