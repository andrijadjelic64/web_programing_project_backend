package com.example.web_jul.services;

import com.example.web_jul.entities.Comment;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created on 13.07.2022. by Andrija inside package com.example.web_jul.services.
 */
public class CommentService {
    @Inject
    com.example.web_jul.repositories.comment.CommentRepository commentRepository;

    public List<Comment> getCommentsByNewsId(int newsId, int page){
        return commentRepository.getCommentsByNewsId(newsId, page);
    }

    public Comment insertComment(Comment comment){
        comment.setDateCreated(new Timestamp(System.currentTimeMillis()));
        return commentRepository.insertComment(comment);
    }
}
