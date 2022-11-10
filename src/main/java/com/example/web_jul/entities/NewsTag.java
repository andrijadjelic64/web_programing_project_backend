package com.example.web_jul.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.entities.
 */
public class NewsTag {

    @NotNull(message = "News field is required")
    @NotEmpty(message = "News field is required")
    private Integer newsId;

    @NotNull(message = "Category field is required")
    @NotEmpty(message = "Category field is required")
    private Integer tagId;

    public NewsTag() {
    }

    public NewsTag(Integer newsId, Integer tagId) {
        this.newsId = newsId;
        this.tagId = tagId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
