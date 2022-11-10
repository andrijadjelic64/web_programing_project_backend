package com.example.web_jul.repositories.news;

import com.example.web_jul.entities.News;
import com.example.web_jul.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.news.
 */
public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository{

    @Override
    public List<News> getNews(int page) {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startRow = (page-1)*10;
            preparedStatement = connection.prepareStatement("select * from news ORDER BY time_created DESC LIMIT ?,?");
            preparedStatement.setInt(1, startRow);
            preparedStatement.setInt(2, 10);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("news_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Timestamp timeCreated = resultSet.getTimestamp("time_created");
                int visitorsNumber = resultSet.getInt("visitors_number");
                int userId = resultSet.getInt("user_id");
                int categoryId = resultSet.getInt("category_id");
                news.add(new News(newsId, title,content, timeCreated, visitorsNumber, userId,categoryId));
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

        return news;
    }

    @Override
    public News findNewsById(int newsId) {
        News news = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news where news_id = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int tagId = resultSet.getInt("news_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Timestamp timeCreated = resultSet.getTimestamp("time_created");
                int visitorsNumber = resultSet.getInt("visitors_number");
                int userId = resultSet.getInt("user_id");
                int categoryId = resultSet.getInt("category_id");
                news = new News(tagId, title,content, timeCreated, visitorsNumber, userId,categoryId);
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

        return news;
    }

    @Override
    public List<News> findNewsByTitleOrContent(String searchQuote,int page){
        List<News> news =  new ArrayList<>();
        String sqlKeyword = "%" + searchQuote + "%";
        Connection connection  = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet =  null;
        try{
            connection = this.newConnection();
            int startRow = (page-1)*10;
            preparedStatement = connection.prepareStatement("SELECT * FROM news WHERE title LIKE ? or content LIKE ? ORDER BY time_created DESC LIMIT ?,?");
            preparedStatement.setString(1, sqlKeyword);
            preparedStatement.setString(2, sqlKeyword);
            preparedStatement.setInt(3, startRow);
            preparedStatement.setInt(4, 10);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int newsId = resultSet.getInt("news_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                java.sql.Timestamp timeCreated = resultSet.getTimestamp("time_created");
                int visitorsNumber = resultSet.getInt("visitors_number");
                int userId = resultSet.getInt("user_id");
                int categoryId = resultSet.getInt("category_id");
                news.add(new News(newsId, title,content, timeCreated, visitorsNumber, userId,categoryId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public List<News> findNewsByCategory(int categoryId, int page) {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startRow = (page-1)*10;
            preparedStatement = connection.prepareStatement("select * from news WHERE category_id = ? ORDER BY time_created DESC LIMIT ?,?");
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, startRow);
            preparedStatement.setInt(3, 10);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int newsId = resultSet.getInt("news_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Timestamp timeCreated = resultSet.getTimestamp("time_created");
                int visitorsNumber = resultSet.getInt("visitors_number");
                int userId = resultSet.getInt("user_id");
//                int categoryId = resultSet.getInt("category_id");
                news.add(new News(newsId, title,content, timeCreated, visitorsNumber, userId,categoryId));
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

        return news;
    }

    @Override
    public News insertNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"news_id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO news (title,content,time_created,visitors_number,user_id, category_id) VALUES(?,?,?,?,?,?)", generatedColumns);
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setTimestamp(3, news.getTimeCreated());
            preparedStatement.setInt(4, news.getVisitorsNumber());
            preparedStatement.setInt(5, news.getAuthorUserId());
            preparedStatement.setInt(6, news.getCategoryId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                news.setNewsId(resultSet.getInt(1));
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

        return news;
    }

    @Override
    public Boolean updateNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean successfullDeletion = false;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE news SET title = ?, content = ? WHERE news_id = ?");
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setInt(3, news.getNewsId());

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

    @Override
    public Boolean deleteById(int newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean successfullDeletion = false;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news where news_id = ?");
            preparedStatement.setInt(1, newsId);
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
