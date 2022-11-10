package com.example.web_jul.services;

import com.example.web_jul.entities.NewsTag;
import com.example.web_jul.entities.Tag;
import com.example.web_jul.repositories.newstag.NewsTagRepository;
import com.example.web_jul.repositories.tag.TagRepository;

import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.resources.
 */

public class TagService {

    @Inject
    TagRepository tagRepository;
    @Inject
    NewsTagRepository newsTagRepository;

    public List<Tag> getAllTagsByNewsId(Integer newsId){
        List<NewsTag> newsTags = newsTagRepository.getNewsTagByNewsId(newsId);

        if (newsTags.isEmpty()){
            return null;
        }

        List<Tag> result = new ArrayList<>();
        for (NewsTag newsTag: newsTags) {
            result.add(tagRepository.findTagById(newsTag.getTagId()));
        }
        return result;
    }

    public Tag findTagById(Integer id) {
        return this.tagRepository.findTagById(id);
    }

    public Tag findTagByTag(String tag) {
        return  this.tagRepository.findTagByTag(tag);
    }

    public void addTagToNews(String tagText, Integer newsId) {

        Tag tag = tagRepository.findTagByTag(tagText);

        if( tag == null){
            tag = tagRepository.insertTagByTag(new Tag(tagText));
        }

        newsTagRepository.insertNewsTag(new NewsTag(newsId,tag.getTagId()));
    }
}
