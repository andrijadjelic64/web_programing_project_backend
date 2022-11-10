package com.example.web_jul.resources;

import com.example.web_jul.entities.User;
import com.example.web_jul.requests.LoginRequest;
import com.example.web_jul.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.resources.
 */
@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String[] responsePair = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (responsePair == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", responsePair[0]);
        response.put("role", responsePair[1]);

        return Response.ok(response).build();
    }

    @GET
    @Path("/get/{page}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll(@PathParam("page") Integer page)
    {
        Map<String, String> response = new HashMap<>();

//        if(userService.authorizeRequestedType(authorization, "admin")) {

        return Response.ok(userService.getAllByPage(page)).build();
//        }
//        response.put("message", "You do not have a privilege for requested method");
//        return Response.status(401, "Unauthorized").entity(response).build();
    }

    @GET
    @Path("/getById/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findUserById(@PathParam("id") Integer id)
    {
        Map<String, String> response = new HashMap<>();


        User user = this.userService.findUserById(id);

        if(user!=null){

            return Response.ok(user).build();
        }
        else{
            response.put("message", "There is no user found with this id");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(@Valid User user)
    {
//        Map<String, String> response = new HashMap<>();
//
//        if(userService.authorizeRequestedType(authorization, "admin")) {
        user = this.userService.create(user);
        return Response.ok(user).build();
//            }
//        response.put("message", "You do not have a privilege for requested method");
//        return Response.status(401, "Unauthorized").entity(response).build();
    }

    @PUT
    @Path("/update")
    public Response update(@Valid User user) {


        Map<String, String> response = new HashMap<>();


        User originalUser = this.userService.findUserById(user.getUserId());

        if(originalUser==null){
            response.put("message", "User not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        if(this.userService.updateUser(user)){

            return Response.ok(this.userService.findUserById(user.getUserId())).build();
        }
        else{
            response.put("message", "User not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

    }
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Integer id) {


        Map<String, String> response = new HashMap<>();

//        if(userService.authorizeRequestedType(authorization, "admin")) {

        User originalUser = this.userService.findUserById(id);

        if(originalUser==null){
            response.put("message", "User not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        if(this.userService.deleteUser(id)){
                return Response.ok(this.userService.findUserById(id)).build();
        }
        else{
            response.put("message", "User not found");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
//        }
//        response.put("message", "You do not have a privilege for requested method");
//        return Response.status(401, "Unauthorized").entity(response).build();

    }
}
