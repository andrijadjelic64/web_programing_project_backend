package com.example.web_jul.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.entities.
 */
public class User {

    private Integer userId;

    @NotNull(message = "Email field is required")
    @NotEmpty(message = "Email field is required")
    private String email;

    @NotNull(message = "First name field is required")
    @NotEmpty(message = "First name field is required")
    private String firstName;

    @NotNull(message = "Last name field is required")
    @NotEmpty(message = "Last name field is required")
    private String lastName;

    @NotNull(message = "Type field is required")
    @NotEmpty(message = "Type field is required")
    private String type;

    @NotNull(message = "Status field is required")
    @NotEmpty(message = "Status field is required")
    private Boolean active;

    @NotNull(message = "Password field is required")
    @NotEmpty(message = "Password field is required")
    private String hashedPassword;

    public User() {
    }


    public User(Integer userId, String email, String firstName, String lastName, String type, Boolean active, String hashedPassword) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.active = active;
        this.hashedPassword = hashedPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
