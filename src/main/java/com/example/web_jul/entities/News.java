package com.example.web_jul.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.entities.
 */
public class News {

    private Integer newsId;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

//    @NotNull(message = "Time field is required")
//    @NotEmpty(message = "Time field is required")
    private java.sql.Timestamp timeCreated;

//    @NotNull(message = "Visitor number field is required")
//    @NotEmpty(message = "Visitor number field is required")
    private Integer visitorsNumber;

//    @NotNull(message = "Visitor number field is required")
//    @NotEmpty(message = "Visitor number field is required")
    private Integer authorUserId;

    @NotNull(message = "CategoryId field is required")
    @NotEmpty(message = "CategoryId field is required")
    private Integer categoryId;

    public News() {
    }

    public News(Integer newsId, String title, String content, Timestamp timeCreated, Integer visitorsNumber, Integer authorUserId, Integer categoryId) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.timeCreated = timeCreated;
        this.visitorsNumber = visitorsNumber;
        this.authorUserId = authorUserId;
        this.categoryId = categoryId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Integer getVisitorsNumber() {
        return visitorsNumber;
    }

    public void setVisitorsNumber(Integer visitorsNumber) {
        this.visitorsNumber = visitorsNumber;
    }

    public Integer getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(Integer authorUserId) {
        this.authorUserId = authorUserId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
