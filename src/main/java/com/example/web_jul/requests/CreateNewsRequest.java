package com.example.web_jul.requests;

import com.example.web_jul.entities.News;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created on 13.07.2022. by Andrija inside package com.example.web_jul.requests.
 */
public class CreateNewsRequest {
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private News news;


    private List<String> tagList;

    public CreateNewsRequest() {
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
