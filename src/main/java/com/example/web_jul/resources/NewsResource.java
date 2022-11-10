package com.example.web_jul.resources;

import com.example.web_jul.entities.Comment;
import com.example.web_jul.entities.News;
import com.example.web_jul.entities.Tag;
import com.example.web_jul.entities.User;
import com.example.web_jul.requests.CreateNewsRequest;
import com.example.web_jul.requests.OpenArticleResponse;
import com.example.web_jul.requests.SearchRequest;
import com.example.web_jul.services.CommentService;
import com.example.web_jul.services.NewsService;
import com.example.web_jul.services.TagService;
import com.example.web_jul.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.resources.
 */
@Path("/news")
public class NewsResource {

    @Inject
    private NewsService newsService;


    @Inject
    private UserService userService;

    @Inject
    private TagService tagService;

    @Inject
    CommentService commentService;

    @GET
    @Path("/get/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("page") Integer page) {
        return Response.ok(this.newsService.getAllByPage(page)).build();
    }

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNewsById(@PathParam("id") Integer id) {

        Map<String, String> response = new HashMap<>();

        News news = newsService.findById(id);

        if(news!=null){

            return Response.ok(news).build();
        }
        else{
            response.put("message", "There is no article found with this id");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }
    }

    @GET
    @Path("/getFullArticleById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response openArticleById(@PathParam("id") Integer id) {

        Map<String, String> response = new HashMap<>();

        News news = newsService.findById(id);

        if(news == null){
            response.put("message", "There is no article found with this id");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }
        User author = userService.findUserById(news.getAuthorUserId());

        List<Tag> tags = tagService.getAllTagsByNewsId(news.getNewsId());

        List<Comment> comments = commentService.getCommentsByNewsId(news.getNewsId(),1);

        OpenArticleResponse articleResponse = new OpenArticleResponse(news,author.getFirstName() +" " + author.getLastName(),tags,comments);
        return Response.ok(articleResponse).build();
    }


    @GET
    @Path("/getAllNewsByCategory/{categoryId}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNewsByCategory(@PathParam("categoryId") Integer categoryId,@PathParam("page") Integer page) {
        Map<String, String> response = new HashMap<>();
        List<News> result = this.newsService.getAllByCategoryId(categoryId,page );

        if(result == null){
            response.put("message", "No article found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        return Response.ok(result).build();

    }

    @GET
    @Path("/getAllNewsByTag/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNewsByTag(@PathParam("tag") String tag) {
        return Response.ok(this.newsService.findAllByTag(tag)).build();

    }

    @POST
    @Path("/search/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@Valid SearchRequest searchRequest, @PathParam("page") Integer page) {
        Map<String, String> response = new HashMap<>();

        List<News> newsList = this.newsService.getAllBySearch(searchRequest.getSearch(),page);

        if(newsList.isEmpty()){
            response.put("message", "No Article found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        return Response.ok(newsList).build();
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(@Valid CreateNewsRequest createNewsRequest, @HeaderParam("Authorization") String token) {
        User user = this.userService.getUserFromToken(token);
        News news = createNewsRequest.getNews();
        news.setAuthorUserId(user.getUserId());
        news = this.newsService.insertNews(news);


        List<String> tagList = createNewsRequest.getTagList();

        for (String tag:tagList){
            tagService.addTagToNews(tag,news.getNewsId());
        }

        return Response.ok(news).build();
    }

    @PUT
    @Path("/update")
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@Valid News news, @HeaderParam("Authorization") String token) {
        Map<String, String> response = new HashMap<>();
        User user = this.userService.getUserFromToken(token);
        News originalNews = this.newsService.findById(news.getNewsId());

        if(originalNews == null){
            response.put("message", "Article not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        if (user.getUserId() != originalNews.getAuthorUserId()) {
            response.put("message", "This article is not yours");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        if(this.newsService.updateNews(news)){
            return Response.ok(newsService.findById(news.getNewsId())).build();
        }
        response.put("message", "Article not found");
        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer id, @HeaderParam("Authorization") String token) {
        Map<String, String> response = new HashMap<>();
        User user = this.userService.getUserFromToken(token);
        News news = this.newsService.findById(id);

        if(news == null){
            response.put("message", "Article not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        if (user.getUserId() != news.getAuthorUserId()) {
            response.put("message", "This article is not yours");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        if(newsService.deleteById(id)){
            response.put("message", "Article deleted successfully");
            return Response.ok(response).build();
        }
        response.put("message", "Article not found");
        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }
}
