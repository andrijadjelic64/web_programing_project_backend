package com.example.web_jul.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.entities.
 */
public class Comment {

    private Integer commentId;

    @NotNull(message = "News id field is required")
    @NotEmpty(message = "News id field is required")
    private Integer newsId;

    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name field is required")
    private String name;

    private java.sql.Timestamp dateCreated;

    @NotNull(message = "Comment field is required")
    @NotEmpty(message = "Comment field is required")
    private String content;

    public Comment() {
    }

    public Comment(Integer commentId, Integer newsId, String name, java.sql.Timestamp dateCreated, String content) {
        this.commentId = commentId;
        this.newsId = newsId;
        this.name = name;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
