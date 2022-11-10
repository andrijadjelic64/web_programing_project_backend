package com.example.web_jul.requests;

import com.example.web_jul.entities.Comment;
import com.example.web_jul.entities.News;
import com.example.web_jul.entities.Tag;

import java.util.List;

/**
 * Created on 13.07.2022. by Andrija inside package com.example.web_jul.requests.
 */
public class OpenArticleResponse {

    private News news;

    private String author;

    private List<Tag> tags;

    private List<Comment> comments;

    public OpenArticleResponse() {
    }

    public OpenArticleResponse(News news, String author, List<Tag> tags, List<Comment> comments) {
        this.news = news;
        this.author = author;
        this.tags = tags;
        this.comments = comments;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
