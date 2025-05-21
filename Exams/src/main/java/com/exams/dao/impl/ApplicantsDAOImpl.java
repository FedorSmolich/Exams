package main.java.com.exams.dao.impl;

import main.java.com.exams.dao.interfaces.ApplicantsDao;
import main.java.com.exams.entity.Applicants;
import main.java.com.exams.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantsDAOImpl implements ApplicantsDao {
    private static final String FIND_BY_ID = "SELECT * FROM applicants WHERE id = ?";
    private static final String SAVE = "INSERT INTO applicants (name, lastname, patronymic, telephone) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE applicants SET name = ?, lastname = ?, patronymic = ?, telephone = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM applicants WHERE id = ?";
    private static final String CHECK_TELEPHONE = "SELECT COUNT(*) FROM applicants WHERE telephone = ?";
    private static final String CHECK_NAME = "SELECT COUNT(*) FROM applicants WHERE name = ?";
    private static final String CHECK_LASTNAME = "SELECT COUNT(*) FROM applicants WHERE lastname = ?";
    private static final String GET_ALL = "SELECT * FROM applicants";
    private static final String CREATE = "INSERT INTO applicants (id, name, lastname, patronymic, telephone) VALUES (?, ?, ?, ?, ?)";

    @Override
    public Applicants findById(Long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Applicants applicant = new Applicants();
                applicant.setId(resultSet.getLong("id"));
                applicant.setName(resultSet.getString("name"));
                applicant.setLastname(resultSet.getString("lastname"));
                applicant.setPatronymic(resultSet.getString("patronymic"));
                applicant.setTelephone(resultSet.getString("telephone"));
                return applicant;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Applicants applicant) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setString(1, applicant.getName());
            statement.setString(2, applicant.getLastname());
            statement.setString(3, applicant.getPatronymic());
            statement.setString(4, applicant.getTelephone());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Applicants applicant) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, applicant.getName());
            statement.setString(2, applicant.getLastname());
            statement.setString(3, applicant.getPatronymic());
            statement.setString(4, applicant.getTelephone());
            statement.setLong(5, applicant.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Applicants applicant) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, applicant.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkTelephone(String telephone) {
        if (telephone == null || telephone.length() != 13 || !telephone.startsWith("+375")) {
            return false;
        }
        // Check if the rest of the characters are digits
        for (int i = 4; i < telephone.length(); i++) {
            if (!Character.isDigit(telephone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean TelephoneExists(String telephone) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_TELEPHONE)) {
            statement.setString(1, telephone);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean NameExists(String name) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean LastNameExists(String lastName) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_LASTNAME)) {
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void create(Applicants applicant) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setLong(1, applicant.getId());
            statement.setString(2, applicant.getName());
            statement.setString(3, applicant.getLastname());
            statement.setString(4, applicant.getPatronymic());
            statement.setString(5, applicant.getTelephone());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Applicants> getAll() {
        List<Applicants> applicants = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Applicants applicant = new Applicants();
                applicant.setId(resultSet.getLong("id"));
                applicant.setName(resultSet.getString("name"));
                applicant.setLastname(resultSet.getString("lastname"));
                applicant.setPatronymic(resultSet.getString("patronymic"));
                applicant.setTelephone(resultSet.getString("telephone"));
                applicants.add(applicant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applicants;
    }
}
