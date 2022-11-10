package com.example.web_jul.repositories.news;


import com.example.web_jul.entities.News;

import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.news.
 */
public interface NewsRepository {

    public List<News> getNews(int page);
    public News findNewsById(int newsId);
    public List<News> findNewsByTitleOrContent(String searchQuote,int page);
    public List<News> findNewsByCategory(int categoryId,int page);
    public Boolean updateNews(News news);
    public News insertNews(News news);
    public Boolean deleteById(int newsId);


}