package com.example.web_jul.repositories.comment;


import com.example.web_jul.entities.Comment;

import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.comment.
 */
public interface CommentRepository {
    public List<Comment> getCommentsByNewsId(int newsId,int page);
    public Comment insertComment(Comment comment);
    public boolean deleteById(int newsId);
}