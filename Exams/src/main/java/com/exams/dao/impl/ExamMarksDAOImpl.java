package main.java.com.exams.dao.impl;

import main.java.com.exams.dao.interfaces.ExamMarksDao;
import main.java.com.exams.entity.ExamMarks;
import main.java.com.exams.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExamMarksDAOImpl implements ExamMarksDao {
    private static final String ASSIGN_MARK = "INSERT INTO exam_marks (id_applicant, id_exam, mark) VALUES (?, ?, ?) " +
            "ON CONFLICT (id_applicant, id_exam) DO UPDATE SET mark = EXCLUDED.mark";
    private static final String GET_BY_EXAM_ID = "SELECT * FROM exam_marks WHERE id_exam = ?";
    private static final String GET_BY_APPLICANT_ID = "SELECT * FROM exam_marks WHERE id_applicant = ?";

    @Override
    public void assignMark(ExamMarks mark) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ASSIGN_MARK)) {
            statement.setLong(1, mark.getIdApplicant());
            statement.setLong(2, mark.getIdExam());
            statement.setInt(3, mark.getMark());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ExamMarks getGrade(Long applicantId, Long examId) {
        return null;
    }

    @Override
    public List<ExamMarks> getGradesByStudent(Long applicantId) {
        return List.of();
    }

    @Override
    public void deleteGrade(Long studentId, Long examId) {

    }


    public double calculateAverageGrade(Long applicantId) {
        return 0;
    }

    public ExamMarks findByExamId(int examId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_EXAM_ID)) {
            statement.setInt(1, examId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToExamMark(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ExamMarks findByApplicantId(int applicantId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_APPLICANT_ID)) {
            statement.setInt(1, applicantId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToExamMark(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int generateMark(ExamMarks mark) {
        // Generate a random mark between 1 and 10
        Random random = new Random();
        int generatedMark = random.nextInt(10) + 1;
        
        // Update the mark in the database
        mark.setMark(generatedMark);
        assignMark(mark);
        
        return generatedMark;
    }

    private ExamMarks mapResultSetToExamMark(ResultSet resultSet) throws SQLException {
        ExamMarks mark = new ExamMarks();
        mark.setIdApplicant(resultSet.getLong("id_applicant"));
        mark.setIdExam(resultSet.getLong("id_exam"));
        mark.setMark(resultSet.getInt("mark"));
        return mark;
    }
}
