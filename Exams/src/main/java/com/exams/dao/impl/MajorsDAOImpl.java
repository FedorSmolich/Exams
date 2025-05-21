package main.java.com.exams.dao.impl;

import main.java.com.exams.dao.interfaces.MajorsDao;
import main.java.com.exams.entity.Majors;
import main.java.com.exams.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MajorsDAOImpl implements MajorsDao {
    private static final String GET_BY_ID = "SELECT * FROM majors WHERE id = ?";
    private static final String CREATE = "INSERT INTO majors (id, name, count_of_students) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE majors SET name = ?, count_of_students = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM majors WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM majors";
    private static final String GET_BY_PASSING_SCORE = "SELECT m.* FROM majors m " +
            "JOIN major_passing_scores mps ON m.id = mps.major_id " +
            "WHERE mps.passing_score >= ?";

    @Override
    public Majors getById(Long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToMajor(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Majors major) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setLong(1, major.getId());
            statement.setString(2, major.getName());
            statement.setInt(3, major.getCountOfStudents());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Majors major) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, major.getName());
            statement.setInt(2, major.getCountOfStudents());
            statement.setLong(3, major.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Majors major) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, major.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Majors> getAll() {
        List<Majors> majors = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                majors.add(mapResultSetToMajor(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majors;
    }

    @Override
    public List<Majors> getByPassingScore(double minScore) {
        List<Majors> majors = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_PASSING_SCORE)) {
            statement.setDouble(1, minScore);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                majors.add(mapResultSetToMajor(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return majors;
    }

    private Majors mapResultSetToMajor(ResultSet resultSet) throws SQLException {
        Majors major = new Majors();
        major.setId(resultSet.getLong("id"));
        major.setName(resultSet.getString("name"));
        major.setCountOfStudents(resultSet.getInt("count_of_students"));
        return major;
    }
}
