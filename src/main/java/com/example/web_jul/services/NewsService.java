package com.example.web_jul.services;


import com.example.web_jul.entities.Category;
import com.example.web_jul.entities.News;
import com.example.web_jul.entities.NewsTag;
import com.example.web_jul.entities.Tag;
import com.example.web_jul.repositories.category.CategoryRepository;
import com.example.web_jul.repositories.news.NewsRepository;
import com.example.web_jul.repositories.newstag.NewsTagRepository;
import com.example.web_jul.repositories.tag.TagRepository;


import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.services.
 */
public class NewsService {

    @Inject
    NewsRepository newsRepository;
    @Inject
    NewsTagRepository newsTagRepository;
    @Inject
    TagRepository tagRepository;
    @Inject
    CategoryRepository categoryRepository;

    public List<News> getAllByPage(int page){
        return newsRepository.getNews(page);
    }

    public News findById(Integer id){
        return newsRepository.findNewsById(id);
    }

    public List<News> getAllBySearch(String searchQuote ,int page){
        return newsRepository.findNewsByTitleOrContent(searchQuote, page);
    }

    public List<News> getAllByCategoryId(Integer categoryId, int page){
        Category category = categoryRepository.findCategoryById(categoryId);

        if(category==null){
            return null;
        }

        return newsRepository.findNewsByCategory(categoryId,page);
    }

    public List<News> findAllByTag(String tagText){


        Tag tag = tagRepository.findTagByTag(tagText);

        if(tag==null){
            return null;
        }

        List<NewsTag> newsTags = newsTagRepository.getNewsTagByTagId(tag.getTagId());

        List<News> result = new ArrayList<>();
        for (NewsTag newsTag: newsTags) {
            result.add(newsRepository.findNewsById(newsTag.getNewsId()));
        }
        if(newsTags.isEmpty()){
            return null;
        }
        return result;
    }


    public News insertNews(News news){
        news.setVisitorsNumber(0);
        news.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        return newsRepository.insertNews(news);
    }

    public Boolean updateNews(News news){
        return newsRepository.updateNews(news);
    }

    public boolean deleteById(Integer id){
        return newsRepository.deleteById(id);
    }
}
