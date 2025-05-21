package main.java.com.exams.dao.impl;

import main.java.com.exams.dao.interfaces.ExamsDao;
import main.java.com.exams.entity.Exams;
import main.java.com.exams.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamsDAOImpl implements ExamsDao {
    private static final String GET_BY_ID = "SELECT * FROM exams WHERE id = ?";
    private static final String CREATE = "INSERT INTO exams (id, name, info) VALUES (?, ?, ?)";
    private static final String GET_ALL = "SELECT * FROM exams";

    @Override
    public void create(Exams exam) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setLong(1, exam.getId());
            statement.setString(2, exam.getName());
            statement.setString(3, exam.getInfo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Exams getById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToExam(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Exams> getAll() {
        List<Exams> exams = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                exams.add(mapResultSetToExam(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exams;
    }

    @Override
    public void update(Exams exam) {

    }

    @Override
    public void delete(int id) {

    }

    private Exams mapResultSetToExam(ResultSet resultSet) throws SQLException {
        Exams exam = new Exams();
        exam.setId(resultSet.getLong("id"));
        exam.setName(resultSet.getString("name"));
        exam.setInfo(resultSet.getString("info"));
        return exam;
    }
}
