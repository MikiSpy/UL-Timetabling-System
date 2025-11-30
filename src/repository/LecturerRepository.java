package repository;

import model.Lecturer;

public interface LecturerRepository {
    Lecturer findById(String id);
}
