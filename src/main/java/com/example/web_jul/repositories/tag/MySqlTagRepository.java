package com.example.web_jul.repositories.tag;

import com.example.web_jul.entities.Tag;
import com.example.web_jul.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.tag.
 */
public class MySqlTagRepository extends MySqlAbstractRepository implements TagRepository {


    @Override
    public Tag findTagById(int tag_id) {
        Tag tag = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM tags where tag_id = ?");
            preparedStatement.setInt(1, tag_id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int tagId = resultSet.getInt("tag_id");
                String tagText = resultSet.getString("tag");
                tag = new Tag(tagId, tagText);
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

        return tag;
    }

    @Override
    public Tag findTagByTag(String req_tag) {
        Tag tag = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM tags where tag = ?");
            preparedStatement.setString(1, req_tag);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int tagId = resultSet.getInt("tag_id");
                String tagText = resultSet.getString("tag");
                tag = new Tag(tagId, tagText);
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

        return tag;
    }

    @Override
    public Tag insertTagByTag(Tag tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"tag_id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO tags (tag) VALUES(?)", generatedColumns);
            preparedStatement.setString(1, tag.getTag());


            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                tag.setTagId(resultSet.getInt(1));
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

        return tag;
    }
}
