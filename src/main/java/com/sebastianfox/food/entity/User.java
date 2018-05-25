package com.sebastianfox.food.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebastianfox.food.entity.event.food.Ingrediant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer id;

    private String username;

    private String email;

    private String session;

    @JsonIgnore
    private byte[] password;

    @JsonIgnore
    private byte[] salt;

    private String fbMail;

    private String fbUsername;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserImage> userImages;

    public User(){
        this.userImages = new ArrayList<>();
    }

//    getter and setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public byte[] getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(byte[] password) {
        this.password = password;
    }

    @JsonIgnore
    public byte[] getSalt() {
        return salt;
    }

    @JsonProperty
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getFbMail() {
        return fbMail;
    }

    public void setFbMail(String fbMail) {
        this.fbMail = fbMail;
    }

    public String getFbUsername() {
        return fbUsername;
    }

    public void setFbUsername(String fbUsername) {
        this.fbUsername = fbUsername;
    }

    public List<UserImage> getUserImages() {
        return userImages;
    }

    public void setUserImages(List<UserImage> userImages) {
        this.userImages = userImages;
    }

    public void addUserImage(UserImage userImage){
        this.userImages.add(userImage);
        userImage.setUser(this);
    }
}



