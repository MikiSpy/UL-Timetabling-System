package repository;

import model.StudentGroup;

public interface StudentGroupRepository {
    boolean save(StudentGroup studentGroup);
    boolean delete(String code);
    StudentGroup findByCode(String code);
}
