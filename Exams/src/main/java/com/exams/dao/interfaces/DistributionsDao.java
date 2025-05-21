package main.java.com.exams.dao.interfaces;

import main.java.com.exams.entity.Distributions;

import java.util.List;

public interface DistributionsDao {
    void assignToFaculty(Distributions distribution);    // Распределить абитуриента на факультет/специальность
    void removeAssignment(int applicantId);             // Удалить распределение
    Distributions getAssignment(int applicantId);        // Получить распределение абитуриента
    List<Distributions> getByFaculty(int facultyId);     // Список абитуриентов факультета
    List<Distributions> getBySpeciality(int specialityId); // Список абитуриентов специальности
    boolean isEnrolled(int applicantId);               // Зачислен ли абитуриент
}
