package main.java.com.exams.dao.interfaces;

import main.java.com.exams.entity.ExamMarks;

import java.util.List;

public interface ExamMarksDao {
    void assignMark(ExamMarks mark);                  // Добавить/обновить оценку
    ExamMarks getGrade(Long applicantId, Long examId);      // Оценка студента за экзамен
    List<ExamMarks> getGradesByStudent(Long applicantId);  // Все оценки студента
    void deleteGrade(Long studentId, Long examId);        // Удалить оценку
    double calculateAverageGrade(Long applicantId);// Расчет среднего балла
}
