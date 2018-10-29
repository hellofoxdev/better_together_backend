package com.sebastianfox.food.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity // This tells Hibernate to make a table out of this class
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer id;

    private String username;

    private String email;

    private String session;

    private String firstname;

    private String lastname;

    @JsonIgnore
    private byte[] password;

    @JsonIgnore
    private byte[] salt;

    private String facebookMail;

    private String facebookUsername;

    private boolean socialMediaAccount = false;

    @ManyToMany
    @JoinTable(name="favorites",
            joinColumns=@JoinColumn(name="userFriendId"),
            inverseJoinColumns=@JoinColumn(name="userFavoriteId")
    )
    private List<User> friends;

    @ManyToMany
    @JoinTable(name="favorites",
            joinColumns=@JoinColumn(name="userFavoriteId"),
            inverseJoinColumns=@JoinColumn(name="userFriendId")
    )
    private List<User> friendOf;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserImage> userImages;

//    Constructor

    public User(){
        this.userImages = new ArrayList<>();
        this.friendOf = new ArrayList<>();
        this.friends = new ArrayList<>();
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

    public String getFacebookMail() {
        return facebookMail;
    }

    public void setFacebookMail(String facebookMail) {
        this.facebookMail = facebookMail;
    }

    public String getFacebookUsername() {
        return facebookUsername;
    }

    public void setFacebookUsername(String facebookUsername) {
        this.facebookUsername = facebookUsername;
    }

    public List<UserImage> getUserImages() {
        return userImages;
    }

    public void setUserImages(List<UserImage> userImages) {
        this.userImages = userImages;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void addUserImage(UserImage userImage){
        this.userImages.add(userImage);
        userImage.setUser(this);
    }

    public boolean isSocialMediaAccount() {
        return socialMediaAccount;
    }

    public void setSocialMediaAccount(boolean socialMediaAccount) {
        this.socialMediaAccount = socialMediaAccount;
    }

   /* public void addFriendOf(User user){
        user.friendOf.add(this);
        this.addFriend(user);
    }*/

    public void addFriend(User friend){
        this.friends.add(friend);
       // friend.addFriendOf(this);
        //userImage.setUser(this);
    }

    public List<User> getFriends() {
        return friends;
    }
}



