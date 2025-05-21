package main.java.com.exams.dao.interfaces;

import main.java.com.exams.entity.Majors;

import java.util.List;

public interface MajorsDao {
    Majors getById(Long Id);
    void create(Majors majors);
    void update(Majors majors);
    void delete(Majors majors);
    List<Majors> getAll();
    List<Majors> getByPassingScore(double minScore);  // Специальности с проходным баллом >= minScore

}
