package main.java.com.exams.dao.interfaces;

import main.java.com.exams.entity.Faculties;

import java.util.List;

public interface FacultiesDao
{
    void create(Faculties faculty);
    void update(Faculties faculty);
    void delete(Faculties faculty);
    Faculties getById(Long id);
    List<Faculties> getAll();
}
