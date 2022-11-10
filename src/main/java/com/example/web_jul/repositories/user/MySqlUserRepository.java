package com.example.web_jul.repositories.user;

import com.example.web_jul.entities.User;
import com.example.web_jul.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.repositories.user.
 */
public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository{

    @Override
    public List<User> getUsers(int page) {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startRow = (page-1)*10;
            preparedStatement = connection.prepareStatement("SELECT * FROM users LIMIT ?,?");
            preparedStatement.setInt(1, startRow);
            preparedStatement.setInt(2, 10);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                String email = resultSet.getString("email");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String type = resultSet.getString("type");
                Boolean active = resultSet.getBoolean("active");
                String password = resultSet.getString("password");
                users.add(new User(user_id, email, first_name,last_name, type, active, password));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User findUserById(Integer id) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where user_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                String email = resultSet.getString("email");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String type = resultSet.getString("type");
                Boolean active = resultSet.getBoolean("active");
                String password = resultSet.getString("password");
                user = new User(user_id, email, first_name,last_name, type, active, password);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String req_email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ?");
            preparedStatement.setString(1, req_email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                String email = resultSet.getString("email");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String type = resultSet.getString("type");
                Boolean active = resultSet.getBoolean("active");
                String password = resultSet.getString("password");
                user = new User(user_id, email, first_name,last_name, type, active, password);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public Boolean updateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean successfullUpdate = false;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"user_id","email","first_name","last_name","type","active","password"};


            preparedStatement = connection.prepareStatement("UPDATE users SET email = ?, first_name = ?, last_name = ?, type= ?, active = ?   WHERE user_id = ?",generatedColumns);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getType());
            preparedStatement.setBoolean(5, user.getActive());
            preparedStatement.setInt(6, user.getUserId());

            int row = preparedStatement.executeUpdate();

            if (row>0) {
                successfullUpdate = true;
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return successfullUpdate;
    }

    @Override
    public User insertUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"user_id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO users (email, first_name, last_name, type, active,password) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getType());
            preparedStatement.setBoolean(5, user.getActive());
            preparedStatement.setString(6, user.getHashedPassword());


            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public boolean deleteUser(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean successfullDeletion = false;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM users where user_id = ?");
            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();

            if (row>0) {
                successfullDeletion = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return successfullDeletion;
    }
}
