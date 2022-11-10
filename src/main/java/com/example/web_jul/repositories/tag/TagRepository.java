package com.example.web_jul.repositories.tag;

import com.example.web_jul.entities.Tag;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.tag.
 */
public interface TagRepository {

    public Tag findTagById(int tag_id);
    public Tag findTagByTag(String tag);
    public Tag insertTagByTag(Tag tag);
}