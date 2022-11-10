package com.example.web_jul.repositories.newstag;

import com.example.web_jul.entities.NewsTag;

import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.newstag.
 */
public interface NewsTagRepository {

    public List<NewsTag> getNewsTagByTagId(int reqTagId);
    public List<NewsTag> getNewsTagByNewsId(int reqNewsId);
    public NewsTag insertNewsTag(NewsTag newsTag);


}