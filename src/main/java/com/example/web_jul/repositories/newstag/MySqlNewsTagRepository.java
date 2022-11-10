package com.example.web_jul.repositories.newstag;

import com.example.web_jul.entities.NewsTag;
import com.example.web_jul.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.newstag.
 */
public class MySqlNewsTagRepository extends MySqlAbstractRepository implements NewsTagRepository {

    @Override
    public List<NewsTag> getNewsTagByTagId(int reqTagId) {
        List<NewsTag> newsTagList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM newstag where tag_id = ?");
            preparedStatement.setInt(1, reqTagId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int newsId = resultSet.getInt("news_id");
                int tagId = resultSet.getInt("tag_id");
                newsTagList.add(new NewsTag(newsId, tagId));
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

        return newsTagList;
    }

    @Override
    public List<NewsTag> getNewsTagByNewsId(int reqNewsId) {
        List<NewsTag> newsTagList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM newstag where news_id = ?");
            preparedStatement.setInt(1, reqNewsId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int newsId = resultSet.getInt("news_id");
                int tagId = resultSet.getInt("tag_id");
                newsTagList.add(new NewsTag(newsId, tagId));
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

        return newsTagList;
    }

    @Override
    public NewsTag insertNewsTag(NewsTag newsTag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"tag_id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO newstag (news_id,tag_id) VALUES(?,?)", generatedColumns);
            preparedStatement.setInt(1, newsTag.getNewsId());
            preparedStatement.setInt(2, newsTag.getTagId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {

            }else{
                newsTag = null;
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

        return newsTag;
    }
}
