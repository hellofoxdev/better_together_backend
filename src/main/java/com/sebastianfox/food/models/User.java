package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity // This tells Hibernate to make a table out of this class
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer id;

    /** personal data */
    private String username;

    private String email;

    private String firstname;

    private String lastname;

    @Column(columnDefinition = "LONGTEXT")
    private String imageString;

    /** Security - Password and Salt */
    @JsonIgnore
    private byte[] password;

    @JsonIgnore
    private byte[] salt;

    /** Facebook - id/mail/socialAcoount */
    private long facebookId;

    private String facebookMail;

    private String facebookUsername;

    private boolean socialMediaAccount = false;

    /** Events - list of evets and ownd events */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name="users_events",
            joinColumns=@JoinColumn(name="userId"),
            inverseJoinColumns=@JoinColumn(name="eventId")
    )
    @JsonIgnoreProperties({"owner", "members", "memberRequests"})
    private List<Event> events;

    @OneToMany(mappedBy="owner")
    @JsonIgnoreProperties({"owner", "members", "memberRequests"})
    private List<Event> ownedEvents;

    /** Friends - List of friends (both sides) */
    @ManyToMany
    @JoinTable(name="friends",
            joinColumns=@JoinColumn(name="friendId"),
            inverseJoinColumns=@JoinColumn(name="favoriteId")

    )
    private List<User> friends;

    @ManyToMany
    private List<User> friendOf;

    /**
     * Constructor
     */
    public User(){
        this.friendOf = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.events = new ArrayList<>();
        this.ownedEvents = new ArrayList<>();
    }

    //  Methods

    /**
     * @param appUser data transformed as user from app
     */
    public void mergeDataFromApp(User appUser) {
        this.username = appUser.username;
        this.email = appUser.email;
        this.lastname = appUser.lastname;
        this.firstname = appUser.firstname;
        this.imageString = appUser.imageString;

        /* Not ncessary?
        for (Event event : appUser.getEvents()) {
            if (!this.events.contains(event)){
                this.events.add(event);
            }
        }

        for (Event event : appUser.getOwnedEvents()) {
            if (!this.ownedEvents.contains(event)){
                this.ownedEvents.add(event);
            }
        }

        for (User friend : appUser.getFriends()) {
            if (!this.friends.contains(friend)){
                this.friends.add(friend);
            }
        }

        for (User friendOf : appUser.getFriendOf()) {
            if (!this.friendOf.contains(friendOf)){
                this.friendOf.add(friendOf);
            }
        }
        */
    }

    // Getter and Setter methods

    /**
     * Get id
     * @return Integer id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set id
     * @param id of user
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get username
     * @return String username of user
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get email
     * @return String email of user
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get password
     * @return String password of user (ignored by JSON)
     */
    @JsonIgnore
    public byte[] getPassword() {
        return password;
    }

    /**
     * Set password
     * @param password of user
     */
    @JsonProperty
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * Get salt
     * @return byte[] salt of user
     */
    @JsonIgnore
    public byte[] getSalt() {
        return salt;
    }

    @JsonProperty
    public void setSalt(byte[] salt) {
        this.salt = salt;
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
    public boolean isSocialMediaAccount() {
        return socialMediaAccount;
    }

    public void setSocialMediaAccount(boolean socialMediaAccount) {
        this.socialMediaAccount = socialMediaAccount;
    }

    public void addEvent(Event event){
        this.events.add(event);
        if (!event.getMembers().contains(this)){
            event.addMember(this);
        }
    }

    public void removeEvent(Event event){
        this.events.remove(event);
        if (event.getMembers().contains(this)) {
            event.removeMember(this);
        }
    }

    public void removeOwnedEventFromList(Event event) {
        this.events.remove(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(List<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public void addOwnedEvent(Event ownedEvent) {
        this.ownedEvents.add(ownedEvent);
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * add friend to array of friends
     * @param friend user
     */
    public void addFriend(User friend){
        if (this.friends.contains(friend)) {
            this.friends.add(friend);
        }
        if (!friend.getFriendOf().contains(this)){
            friend.addFriendOf(this);
        }
    }

    /**
     * Get friends of
     * @return list of firends the current user is
     */
    public List<User> getFriendsOfFriends() {
        List<User> friendsOfFriends = new ArrayList<>();
        for (User friend : friends) {
            friendsOfFriends.add(friend);
            friendsOfFriends.addAll(friend.getFriends());
        }
        return friendsOfFriends;
    }

    /**
     * Get friends of
     * @return list of firends the current user is
     */
    public List<User> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(List<User> friendOf) {
        this.friendOf = friendOf;
    }


    public void addFriendOf(User friendOf){
        if (!this.friendOf.contains(friendOf)) {
            this.friendOf.add(friendOf);
        }
        if (!friendOf.getFriends().contains(this)){
            friendOf.addFriend(this);
        }
    }

    /**
     * Get Facebook ID
     * @return facebookId
     */
    public long getFacebookId() {
        return facebookId;
    }

    /**
     * Set Facebook id
     * @param facebookId id
     */
    public void setFacebookId(long facebookId) {
        this.facebookId = facebookId;
    }
}



