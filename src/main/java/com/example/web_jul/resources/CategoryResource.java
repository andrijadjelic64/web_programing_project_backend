package com.example.web_jul.resources;

import com.example.web_jul.entities.Category;
import com.example.web_jul.services.CategoryService;

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
@Path("/categories")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @GET
    @Path("/get/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("page") Integer page) {
        return Response.ok(this.categoryService.getCategoriesByPage(page)).build();
    }

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNewsById(@PathParam("id") Integer id) {

        Map<String, String> response = new HashMap<>();

        Category category = categoryService.findById(id);

        if(category!=null){

            return Response.ok(category).build();
        }
        else{
            response.put("message", "Category not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(@Valid Category category)
    {
        Map<String, String> response = new HashMap<>();

        Category category1 = categoryService.findByName(category.getName());

        if(category1==null){
            return Response.ok(this.categoryService.insertCategory(category)).build();
        }
        else{
            response.put("message", "There is another category with same category name.");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

    }

    @PUT
    @Path("/update")
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@Valid Category category) {
        Map<String, String> response = new HashMap<>();

        Category originalCategory = categoryService.findById(category.getCategoryId());
        Category categorytest = categoryService.findByName(category.getName());

        if(originalCategory==null){
            response.put("message", "Category not found");
            return Response.status(404, "Unprocessable Entity").entity(response).build();
        }

        //slucaj ako je se ne menja naziv katogorije
        if((categorytest !=null && originalCategory.getCategoryId()==categorytest.getCategoryId())){
            if(this.categoryService.updateCategory(category)){
                return Response.ok(categoryService.findById(category.getCategoryId())).build();
            }else{
                response.put("message", "Category not found");
                return Response.status(404, "Unprocessable Entity").entity(response).build();
            }
        }// slucaj ako menja naziv kategorije
        else if(categorytest == null){
            if(this.categoryService.updateCategory(category)){
                return Response.ok(categoryService.findById(category.getCategoryId())).build();
            }else{
                response.put("message", "Category not found");
                return Response.status(404, "Unprocessable Entity").entity(response).build();
            }
        }
        else{
            response.put("message", "There is another category with same category name.");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        if(categoryService.deleteById(id)){
            response.put("message", "Category deleted successfully");
            return Response.ok(response).build();
        }else{
            response.put("message", "Category not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }
}
