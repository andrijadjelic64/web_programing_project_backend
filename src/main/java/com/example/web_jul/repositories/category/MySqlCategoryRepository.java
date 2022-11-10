package com.example.web_jul.repositories.category;

import com.example.web_jul.entities.Category;
import com.example.web_jul.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.category.
 */
public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository{

    @Override
    public List<Category> getCategories(int page) {
        List<Category> categories = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startRow = (page-1)*10;
            preparedStatement = connection.prepareStatement("select * from categories LIMIT ?,?");
            preparedStatement.setInt(1, startRow);
            preparedStatement.setInt(2, 10);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                categories.add(new Category(categoryId, name,description));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category findCategoryById(int id) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from categories where category_id = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                category =new Category(categoryId, name,description);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public Category findCategoryByName(String req_name) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("select * from categories where name = ?");
            preparedStatement.setString(1, req_name);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                category =new Category(categoryId, name,description);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public Category insertCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"category_id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO categories (name,description) VALUES(?,?)", generatedColumns);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());


            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                category.setCategoryId(resultSet.getInt(1));
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

        return category;
    }

    @Override
    public Boolean updateCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean successfullUpdate = false;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"category_id","name","description"};

            preparedStatement = connection.prepareStatement("UPDATE categories SET name = ?, description= ? WHERE category_id = ?",generatedColumns);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getCategoryId());

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
    public Boolean deleteById(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean successfullDeletion = false;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM categories where category_id = ?");
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
