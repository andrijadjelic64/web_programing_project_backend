package com.example.web_jul.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.entities.
 */
public class Tag {

    private Integer tagId;

    @NotNull(message = "Tag field is required")
    @NotEmpty(message = "Tag field is required")
    private String tag;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public Tag(Integer tagId, String tag) {
        this.tagId = tagId;
        this.tag = tag;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
