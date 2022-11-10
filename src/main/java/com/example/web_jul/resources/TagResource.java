package com.example.web_jul.resources;

import com.example.web_jul.entities.Tag;
import com.example.web_jul.services.TagService;
import com.example.web_jul.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.resources.
 */
@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Integer id) {
        return Response.ok(this.tagService.findTagById(id)).build();
    }
    @GET
    @Path("/getByTag/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByTag(@PathParam("tag") String tag) {
        return Response.ok(this.tagService.findTagByTag(tag)).build();

    }

    @GET
    @Path("/getAllTagsByNewsId/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTagsByNewsId(@PathParam("newsId") Integer newsId) {
        return Response.ok(this.tagService.getAllTagsByNewsId(newsId)).build();

    }
}
