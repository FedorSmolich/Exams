package main.java.com.exams.dao.impl;

import main.java.com.exams.dao.interfaces.FacultiesDao;
import main.java.com.exams.entity.Faculties;
import main.java.com.exams.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultiesDAOImpl implements FacultiesDao {
    private static final String GET_BY_ID = "SELECT * FROM faculties WHERE id = ?";
    private static final String CREATE = "INSERT INTO faculties (id, name, address, info) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE faculties SET name = ?, address = ?, info = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM faculties WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM faculties";
    private static final String GET_BY_NAME = "SELECT * FROM faculties WHERE name = ?";

    @Override
    public Faculties getById(Long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToFaculty(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Faculties faculty) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setLong(1, faculty.getId());
            statement.setString(2, faculty.getName());
            statement.setString(3, faculty.getAddress());
            statement.setString(4, faculty.getInfo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Faculties faculty) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, faculty.getName());
            statement.setString(2, faculty.getAddress());
            statement.setString(3, faculty.getInfo());
            statement.setLong(4, faculty.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Faculties faculty) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, faculty.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Faculties> getAll() {
        List<Faculties> faculties = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                faculties.add(mapResultSetToFaculty(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculties;
    }

    @Override
    public Faculties getByName(String name) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToFaculty(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Faculties mapResultSetToFaculty(ResultSet resultSet) throws SQLException {
        Faculties faculty = new Faculties();
        faculty.setId(resultSet.getLong("id"));
        faculty.setName(resultSet.getString("name"));
        faculty.setAddress(resultSet.getString("address"));
        faculty.setInfo(resultSet.getString("info"));
        return faculty;
    }
}
