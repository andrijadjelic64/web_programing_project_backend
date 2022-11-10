package com.example.web_jul.resources;

import com.example.web_jul.entities.Comment;
import com.example.web_jul.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created on 13.07.2022. by Andrija inside package com.example.web_jul.resources.
 */
@Path("/comments")
public class CommentResource {

    @Inject
    CommentService commentService;

    @GET
    @Path("/get/{newsId}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("newsId") Integer newsId,@PathParam("page") Integer page) {
        List<Comment> comments = commentService.getCommentsByNewsId(newsId,page);
        return Response.ok(comments).build();

    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@Valid Comment comment) {
        Comment result = commentService.insertComment(comment);
        return Response.ok(result).build();

    }
}
