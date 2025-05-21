package main.java.com.exams.dao.interfaces;

import main.java.com.exams.entity.Applicants;

import java.util.List;

public interface ApplicantsDao {
    Applicants findById(Long id);
    void create(Applicants applicant);
    void save(Applicants applicant);
    void update(Applicants applicant);
    void delete(Applicants applicant);
    List<Applicants> getAll();
    boolean checkTelephone(String telephone);
    boolean TelephoneExists(String telephone);
    boolean NameExists(String name);
    boolean LastNameExists(String lastName);
}
