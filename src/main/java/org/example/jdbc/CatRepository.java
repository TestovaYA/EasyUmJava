package org.example.jdbc;

import org.example.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatRepository implements Repository<Cat> {
    private final Connection connection;
    public CatRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Cat save(Cat entity) {
        String insertCatSQL = "INSERT INTO Cat (name, birth_date, breed, color, owner_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCatSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, new java.sql.Date(entity.getBirthDate().getTime()));
            preparedStatement.setString(3, entity.getBreed());
            preparedStatement.setString(4, entity.getColor().name());
            preparedStatement.setInt(5, entity.getOwnerId());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM Cat WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByEntity(Cat entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM Cat";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cat update(Cat entity) {
        String updateSQL = "UPDATE Cat SET name = ?, birth_date = ?, breed = ?, color = ?, owner_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, new java.sql.Date(entity.getBirthDate().getTime()));
            preparedStatement.setString(3, entity.getBreed());
            preparedStatement.setString(4, entity.getColor().name());
            preparedStatement.setInt(5, entity.getOwnerId());
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Cat getById(long id) {
        String sql = "SELECT * FROM Cat WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Cat cat = new Cat();
                cat.setId(resultSet.getInt("id"));
                cat.setName(resultSet.getString("name"));
                cat.setBirthDate(resultSet.getDate("birth_date"));
                cat.setBreed(resultSet.getString("breed"));
                cat.setColor(Color.valueOf(resultSet.getString("color")));
                cat.setOwnerId(resultSet.getInt("owner_id"));
                return cat;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cat> getAll() {
        List<Cat> cats = new ArrayList<>();
        String sql = "SELECT * FROM Cat";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Cat cat = new Cat();
                cat.setId(resultSet.getInt("id"));
                cat.setName(resultSet.getString("name"));
                cat.setBirthDate(resultSet.getDate("birth_date"));
                cat.setBreed(resultSet.getString("breed"));
                cat.setColor(Color.valueOf(resultSet.getString("color")));
                cat.setOwnerId(resultSet.getInt("owner_id"));
                cats.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    }

    public List<Cat> getAllByParentId(int ownerId) {
        List<Cat> cats = new ArrayList<>();
        String sql = "SELECT * FROM Cat WHERE owner_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ownerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cat cat = new Cat();
                cat.setId(resultSet.getInt("id"));
                cat.setName(resultSet.getString("name"));
                cat.setBirthDate(resultSet.getDate("birth_date"));
                cat.setBreed(resultSet.getString("breed"));
                cat.setColor(Color.valueOf(resultSet.getString("color")));
                cat.setOwnerId(resultSet.getInt("owner_id"));
                cats.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    }
}
