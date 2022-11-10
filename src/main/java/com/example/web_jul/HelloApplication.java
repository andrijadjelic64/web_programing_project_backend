package com.example.web_jul;

import com.example.web_jul.repositories.category.CategoryRepository;
import com.example.web_jul.repositories.category.MySqlCategoryRepository;
import com.example.web_jul.repositories.comment.CommentRepository;
import com.example.web_jul.repositories.comment.MySqlCommentRepository;
import com.example.web_jul.repositories.news.MySqlNewsRepository;
import com.example.web_jul.repositories.news.NewsRepository;
import com.example.web_jul.repositories.newstag.MySqlNewsTagRepository;
import com.example.web_jul.repositories.newstag.NewsTagRepository;
import com.example.web_jul.repositories.tag.MySqlTagRepository;
import com.example.web_jul.repositories.tag.TagRepository;
import com.example.web_jul.repositories.user.MySqlUserRepository;
import com.example.web_jul.repositories.user.UserRepository;
import com.example.web_jul.services.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(TagRepository.class).in(Singleton.class);
                this.bind(MySqlNewsRepository.class).to(NewsRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlNewsTagRepository.class).to(NewsTagRepository.class).in(Singleton.class);

                this.bindAsContract(UserService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(CommentService.class);

            }
        };
        register(binder);


        // Ucitavamo resurse
        packages("com.example.web_jul");
    }

}