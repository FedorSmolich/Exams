package main.java.com.exams.dao.impl;

import main.java.com.exams.dao.interfaces.DistributionsDao;
import main.java.com.exams.entity.Distributions;
import main.java.com.exams.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistributionsDAOImpl implements DistributionsDao {
    private static final String ASSIGN_TO_FACULTY = "INSERT INTO distributions (id_applicant, id_major, id_faculty) VALUES (?, ?, ?)";
    private static final String REMOVE_ASSIGNMENT = "DELETE FROM distributions WHERE id_applicant = ?";
    private static final String GET_ASSIGNMENT = "SELECT * FROM distributions WHERE id_applicant = ?";
    private static final String GET_BY_FACULTY = "SELECT * FROM distributions WHERE id_faculty = ?";
    private static final String GET_BY_SPECIALITY = "SELECT * FROM distributions WHERE id_major = ?";
    private static final String CHECK_ENROLLMENT = "SELECT COUNT(*) FROM distributions WHERE id_applicant = ?";

    @Override
    public void assignToFaculty(Distributions distribution) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ASSIGN_TO_FACULTY)) {
            statement.setLong(1, distribution.getIdApplicant());
            statement.setLong(2, distribution.getIdMajor());
            statement.setLong(3, distribution.getIdFaculty());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAssignment(int applicantId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_ASSIGNMENT)) {
            statement.setInt(1, applicantId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Distributions getAssignment(int applicantId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ASSIGNMENT)) {
            statement.setInt(1, applicantId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToDistribution(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Distributions> getByFaculty(int facultyId) {
        List<Distributions> distributions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_FACULTY)) {
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                distributions.add(mapResultSetToDistribution(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distributions;
    }

    @Override
    public List<Distributions> getBySpeciality(int specialityId) {
        List<Distributions> distributions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_SPECIALITY)) {
            statement.setInt(1, specialityId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                distributions.add(mapResultSetToDistribution(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distributions;
    }

    @Override
    public boolean isEnrolled(int applicantId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_ENROLLMENT)) {
            statement.setInt(1, applicantId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Distributions mapResultSetToDistribution(ResultSet resultSet) throws SQLException {
        Distributions distribution = new Distributions();
        distribution.setIdApplicant(resultSet.getLong("id_applicant"));
        distribution.setIdMajor(resultSet.getLong("id_major"));
        distribution.setIdFaculty(resultSet.getLong("id_faculty"));
        return distribution;
    }
}
