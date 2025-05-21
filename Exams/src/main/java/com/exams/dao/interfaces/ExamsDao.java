package main.java.com.exams.dao.interfaces;

import main.java.com.exams.entity.Exams;

import java.util.List;

public interface ExamsDao {
    void create(Exams exam);
    Exams getById(int id);
    List<Exams> getAll();
    void update(Exams exam);
    void delete(int id);

}
