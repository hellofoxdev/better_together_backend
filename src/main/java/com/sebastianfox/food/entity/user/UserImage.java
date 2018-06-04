package com.sebastianfox.food.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sebastianfox.food.entity.user.User;

import javax.persistence.*;

@Entity
@Table(name="user_images")
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_image_id")
    private Integer id;

    private byte[] image;

    private String name;

    private boolean ProfilePicture;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(boolean profilePicture) {
        ProfilePicture = profilePicture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
