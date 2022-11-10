package com.example.web_jul.repositories.comment;

import com.example.web_jul.entities.Comment;
import com.example.web_jul.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.comment.
 */
public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository{

    @Override
    public List<Comment> getCommentsByNewsId(int newsId, int page) {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            int startRow = (page-1)*10;
            preparedStatement = connection.prepareStatement("select * from comments LIMIT ?,?");
            preparedStatement.setInt(1, startRow);
            preparedStatement.setInt(2, 10);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int commentId = resultSet.getInt("comment_id");
                String name = resultSet.getString("name");
                java.sql.Timestamp dateCreated = resultSet.getTimestamp("date_created");
                String content = resultSet.getString("content");

                comments.add(new Comment(commentId,newsId,name,dateCreated,content));
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

        return comments;
    }

    @Override
    public Comment insertComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"comment_id"};

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO comments (news_id,name,date_created,content) VALUES(?,?,?,?)", generatedColumns);
            preparedStatement.setInt(1, comment.getNewsId());
            preparedStatement.setString(2, comment.getName());
            preparedStatement.setTimestamp(3, comment.getDateCreated());
            preparedStatement.setString(4, comment.getContent());


            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setNewsId(resultSet.getInt(1));
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

        return comment;
    }

    @Override
    public boolean deleteById(int newsId) {
        return false;
    }
}
