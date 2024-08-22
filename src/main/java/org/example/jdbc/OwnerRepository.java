package org.example.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerRepository implements Repository<Owner> {
    private final Connection connection;

    public OwnerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Owner save(Owner entity) {
        String insertOwnerSQL = "INSERT INTO Owner (name, birth_date) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertOwnerSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, new java.sql.Date(entity.getBirthDate().getTime()));
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1)); // Устанавливаем сгенерированный ID
                }
            }
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM Owner WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByEntity(Owner entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM Owner";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Owner update(Owner entity) {
        String updateSQL = "UPDATE Owner SET name = ?, birth_date = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, new java.sql.Date(entity.getBirthDate().getTime()));
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Owner getById(long id) {
        String sql = "SELECT * FROM Owner WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Owner owner = new Owner();
                owner.setId(resultSet.getInt("id"));
                owner.setName(resultSet.getString("name"));
                owner.setBirthDate(resultSet.getDate("birth_date"));
                return owner;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Owner> getAll() {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM Owner";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Owner owner = new Owner();
                owner.setId(resultSet.getInt("id"));
                owner.setName(resultSet.getString("name"));
                owner.setBirthDate(resultSet.getDate("birth_date"));
                owners.add(owner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owners;
    }
}
