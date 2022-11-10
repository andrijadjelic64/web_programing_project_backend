package com.example.web_jul.filters;

import com.example.web_jul.resources.CategoryResource;
import com.example.web_jul.resources.NewsResource;
import com.example.web_jul.resources.TagResource;
import com.example.web_jul.resources.UserResource;
import com.example.web_jul.services.NewsService;
import com.example.web_jul.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        String roleType = typeRequired(requestContext);
        System.out.println("RoleType required"+roleType);
        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token,roleType)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private String typeRequired(ContainerRequestContext req){
        String path = req.getUriInfo().getPath();

        if (path.contains("users")) {
            return "admin";
        }
        else{
            return "content_creator";
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {

        String path = req.getUriInfo().getPath();

        if (path.contains("login")) {
            return false;
        }

        if((path.contains("get") || path.contains("search")) && !path.contains("users")){
            return false;
        }


        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof UserResource) {
                return true;
            }else if (matchedResource instanceof NewsResource) {
                return true;
            }else if (matchedResource instanceof CategoryResource) {
                return true;
            }else if (matchedResource instanceof TagResource) {
                return true;
            }
        }

        return false;
    }
}
