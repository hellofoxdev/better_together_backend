package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
//@JsonIgnoreProperties({"friendshipsFriend1", "friendshipsFriend2"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    /**
     * personal data
     */
    private String userName;

    private String email;

    private String name;

    private String description;

    /**
     * Security - Password and Salt
     */
    @JsonIgnore
    private byte[] password;

    @JsonIgnore
    private byte[] salt;

    /**
     * Facebook - id/mail/socialAcoount
     */
    private long facebookAccountId;

    private String facebookAccountEmail;

    private String facebookAccountUserName;

    private boolean facebookAccount = false;

    /**
     * Events - list of evets and ownd events
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "users_events",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "eventId")
    )
    @JsonIgnoreProperties({"friendships1", "friendships2", "events", "ownedEvents", "acceptedFriends", "getFriendshipRequestsByCurrentUser", "getFriendshipRequestsByFriend"})
    private List<Event> events;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonProperty("ownedEvents")
    @JsonIgnoreProperties({"friendships1", "friendships2", "events", "ownedEvents", "acceptedFriends", "getFriendshipRequestsByCurrentUser", "getFriendshipRequestsByFriend"})
//    @JsonManagedReference(value="event-owner")
    private List<Event> ownedEvents;

    @OneToMany(mappedBy = "friend1")
    @JsonIgnore
//    @JsonProperty("friendshipsFriend1")
//    @JsonIgnoreProperties({ "friendshipsFriend1", "friendshipsFriend2", "events", "ownedEvents", "acceptedFriends" })
    @JsonManagedReference(value="friend1")
    private List<Friendship> friendshipsFriend1;

    @OneToMany(mappedBy = "friend2")
    @JsonIgnore
//    @JsonProperty("friendshipsFriend2")
//    @JsonIgnoreProperties({ "friendshipsFriend1", "friendshipsFriend2", "events", "ownedEvents", "acceptedFriends" })
    @JsonManagedReference(value="friend2")
    private List<Friendship> friendshipsFriend2;

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private Date created;

    /**
     * Constructor
     */
    public User() {
        this.friendshipsFriend1 = new ArrayList<>();
        this.friendshipsFriend2 = new ArrayList<>();
        this.events = new ArrayList<>();
        this.ownedEvents = new ArrayList<>();
    }

    //  Methods

    /**
     * @param appUser data transformed as user from app
     */
    public void mergeDataFromApp(User appUser) {
        this.userName = appUser.userName;
        this.email = appUser.email;
        this.name = appUser.name;
    }

    // Getter and Setter methods

    /**
     * Get id
     *
     * @return Integer id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id of user
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get userName
     *
     * @return String userName of user
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get email
     *
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
     *
     * @return String password of user (ignored by JSON)
     */
    @JsonIgnore
    public byte[] getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password of user
     */
    @JsonProperty
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * Get salt
     *
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

    public String getFacebookAccountEmail() {
        return facebookAccountEmail;
    }

    public void setFacebookAccountEmail(String facebookAccountEmail) {
        this.facebookAccountEmail = facebookAccountEmail;
    }

    public String getFacebookAccountUserName() {
        return facebookAccountUserName;
    }

    public void setFacebookAccountUserName(String facebookAccountUserName) {
        this.facebookAccountUserName = facebookAccountUserName;
    }

    public boolean isFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(boolean facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    /**
     * Get Facebook ID
     *
     * @return facebookAccountId
     */
    public long getFacebookAccountId() {
        return facebookAccountId;
    }

    /**
     * Set Facebook id
     *
     * @param facebookAccountId id
     */
    public void setFacebookAccountId(long facebookAccountId) {
        this.facebookAccountId = facebookAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* *************************
     *  Friendship Handling
     ************************ */

    @JsonIgnore
    public List<Friendship> getFriendshipsFriend1() {
        return friendshipsFriend1;
    }

    public void setFriendshipsFriend1(List<Friendship> friendshipsFriend1) {
        this.friendshipsFriend1 = friendshipsFriend1;
    }

    /**
     * add friend to array of friends
     *
     * @param friendship user
     */
    public void addFriendshipFriend1(Friendship friendship) {
        if (!this.friendshipsFriend1.contains(friendship)) {
            this.friendshipsFriend1.add(friendship);
        }
    }

    public void removeFriendshipFriend1(Friendship friendship) {
        this.friendshipsFriend1.remove(friendship);
    }

    @JsonIgnore
    public List<Friendship> getFriendshipsFriend2() {
        return friendshipsFriend2;
    }

    public void setFriendshipsFriend2(List<Friendship> friendshipsFriend2) {
        this.friendshipsFriend2 = friendshipsFriend2;
    }

    /**
     * add friend to array of friends
     *
     * @param friendship user
     */
    public void addFriendshipFriend2(Friendship friendship) {
        if (!this.friendshipsFriend2.contains(friendship)) {
            this.friendshipsFriend2.add(friendship);
        }
    }

    public void removeFriendshipFriend2(Friendship friendship) {
        this.friendshipsFriend2.remove(friendship);
    }

    // accepted friends / friendships
    @JsonIgnore
    @JsonProperty("acceptedFriendships")
    @JsonIgnoreProperties({"friendshipsFriend1", "friendshipsFriend2", "events", "ownedEvents", "acceptedFriendships", "open", "accepted"})
    public List<Friendship> getAcceptedFriendships() {
        Iterator<Friendship> friendshipIterator1 = friendshipsFriend1.iterator();
        List<Friendship> acceptedFriendships = new ArrayList<>(this.getAcceptedFriendshipRequestsByFriendType(friendshipIterator1));
        Iterator<Friendship> friendshipIterator2 = friendshipsFriend2.iterator();
        acceptedFriendships.addAll(this.getAcceptedFriendshipRequestsByFriendType(friendshipIterator2));
        return acceptedFriendships;
    }

    @JsonIgnoreProperties({"acceptedFriends", "requestedFriendsByCurrentUser", "requestedFriendsByFriend", "events", "ownedEvents", "acceptedFriends", "open", "accepted"})
    public List<User> getAcceptedFriends() {
        Iterator<Friendship> friendshipIterator = this.getAcceptedFriendships().iterator();

        List<User> friendshipRequester = new ArrayList<>();

        return this.getFriendList(friendshipIterator);
    }

    // get opened friendshiprequests by others / requesters
    @JsonIgnore
    public List<Friendship> getFriendshipRequestsByFriend() {
        Iterator<Friendship> friendshipIterator = friendshipsFriend2.iterator();
        return this.getOpenFriendshipRequestsByFriendType(friendshipIterator);
    }

    @JsonIgnoreProperties({"acceptedFriends", "requestedFriendsByCurrentUser", "requestedFriendsByFriend", "events", "ownedEvents", "acceptedFriends", "open", "accepted"})
    public List<User> getRequestedFriendsByFriend() {
        Iterator<Friendship> friendshipIterator = this.getFriendshipRequestsByFriend().iterator();
        return this.getFriendList(friendshipIterator);
    }

    // get my opend eFriendshiprequests to others / my (future) friends
    @JsonIgnore
    public List<Friendship> getFriendshipRequestsByCurrentUser() {
        Iterator<Friendship> friendshipIterator = friendshipsFriend1.iterator();
        return this.getOpenFriendshipRequestsByFriendType(friendshipIterator);
    }

    @JsonIgnoreProperties({"acceptedFriends", "requestedFriendsByCurrentUser", "requestedFriendsByFriend", "events", "ownedEvents", "acceptedFriends", "open", "accepted"})
    public List<User> getRequestedFriendsByCurrentUser() {
        Iterator<Friendship> friendshipIterator = this.getFriendshipRequestsByCurrentUser().iterator();
        return this.getFriendList(friendshipIterator);
    }

    // methode to determine friend from friendship
    @JsonIgnore
    public List<User> getFriendList(Iterator<Friendship> friendshipIterator) {
        List<User> friendshipRequester = new ArrayList<>();
        while (friendshipIterator.hasNext()) {
            Friendship friendshipByIterator = friendshipIterator.next();
            User friend1ByIterator = friendshipByIterator.getFriend1();
            User friend2ByIterator = friendshipByIterator.getFriend2();
            if (friend1ByIterator == this) {
                friendshipRequester.add(friend2ByIterator);
            } else {
                friendshipRequester.add(friend1ByIterator);
            }
        }
        return friendshipRequester;
    }

    // methode to get opened and not accepted friendships
    @JsonIgnore
    public List<Friendship> getOpenFriendshipRequestsByFriendType(Iterator<Friendship> friendshipIterator) {
        List<Friendship> friendshipRequests = new ArrayList<>();
        while (friendshipIterator.hasNext()) {
            Friendship friendshipByIterator = friendshipIterator.next();
            if (friendshipByIterator.isOpen() && !friendshipByIterator.isAccepted()) {
                friendshipRequests.add(friendshipByIterator);
            }
        }
        return friendshipRequests;
    }

    // methode to get opened and not accepted friendships
    @JsonIgnore
    public List<Friendship> getAcceptedFriendshipRequestsByFriendType(Iterator<Friendship> friendshipIterator) {
        List<Friendship> friendshipRequests = new ArrayList<>();
        while (friendshipIterator.hasNext()) {
            Friendship friendshipByIterator = friendshipIterator.next();
            if (friendshipByIterator.isAccepted()) {
                friendshipRequests.add(friendshipByIterator);
            }
        }
        return friendshipRequests;
    }

    public Friendship createAndAcceptFriendship(User friend) {
        Friendship friendship;
        // Check wether requested friendship already is requested by friend2
        if (this.getRequestedFriendsByFriend().contains(friend)) {
            for (Friendship friendshipToTest : this.getFriendshipRequestsByFriend()) {
                if (friendshipToTest.getFriend1() == friend) {
                    friendshipToTest.setAccepted(true);
                    friendshipToTest.setOpen();
                    return friendshipToTest;
                }
            }
        }
        // Check wether requested friendship already exists and is accepted
        else if (this.getAcceptedFriends().contains(friend)) {
            for (Friendship friendshipToTest : this.getFriendshipRequestsByFriend()) {
                if (friendshipToTest.getFriend2() == friend) {
                    return friendshipToTest;
                }
                if (friendshipToTest.getFriend1() == friend) {
                    return friendshipToTest;
                }
            }
        }
        // Check wether requested friendship already is requested by friend1
        else if (this.getRequestedFriendsByCurrentUser().contains(friend)) {
            for (Friendship friendshipToTest : this.getFriendshipRequestsByCurrentUser()) {
                if (friendshipToTest.getFriend2() == friend) {
                    return friendshipToTest;
                }
            }
        }
        // Create new Friendship
        else {
            friendship = new Friendship(this, friend);
            return friendship;
        }
        return null;
    }

    public Friendship declineAndDeleteFriendship(User friend){
        if (this.getAcceptedFriends().contains(friend)) {
            for (Friendship friendshipToTest : this.getAcceptedFriendships()) {
                if (friendshipToTest.getFriend2() == friend) {
                    friendshipToTest.setAccepted(false);
                    this.removeFriendshipFriend1(friendshipToTest);
                    friend.removeFriendshipFriend2(friendshipToTest);
                    return friendshipToTest;
                }
                if (friendshipToTest.getFriend1() == friend) {
                    this.removeFriendshipFriend2(friendshipToTest);
                    friend.removeFriendshipFriend1(friendshipToTest);
                    return friendshipToTest;
                }
            }
        } else if (this.getRequestedFriendsByCurrentUser().contains(friend)) {
            for (Friendship friendshipToTest : this.getFriendshipRequestsByCurrentUser()) {
                if (friendshipToTest.getFriend2() == friend) {
                    this.removeFriendshipFriend1(friendshipToTest);
                    friend.removeFriendshipFriend2(friendshipToTest);
                    return friendshipToTest;
                }
            }
        } else if (this.getRequestedFriendsByFriend().contains(friend)) {
            for (Friendship friendshipToTest : this.getFriendshipRequestsByFriend()) {
                if (friendshipToTest.getFriend1() == friend) {
                    this.removeFriendshipFriend2(friendshipToTest);
                    friend.removeFriendshipFriend1(friendshipToTest);
                    return friendshipToTest;
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public List<User> getFriendsOfAFriend(User friend) {
        List<User> friendsOfAFriend = new ArrayList<>(friend.getAcceptedFriends());
        friendsOfAFriend.removeAll(this.getAcceptedFriends());
        friendsOfAFriend.remove(this);
        return friendsOfAFriend;
    }

    @JsonIgnore
    public List<User> getFriendsOfAllFriends() {
        List<User> friendsOfAllFriend = new ArrayList<>();
        for (User friend : this.getAcceptedFriends()){
            List<User> friendsOfAFriend = new ArrayList<>(this.getFriendsOfAFriend(friend));
            friendsOfAFriend.removeAll(friendsOfAllFriend);
            friendsOfAllFriend.addAll(friendsOfAFriend);
        }
        return friendsOfAllFriend;
    }

    /* *************************
     *  Event Handling
     ************************ */

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
        if (!event.getMembers().contains(this)) {
            event.addMember(this);
        }
    }

    public void removeEvent(Event event) {
        this.events.remove(event);
        if (event.getMembers().contains(this)) {
            event.removeMember(this);
        }
    }

    public List<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(List<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public void removeOwnedEventFromList(Event event) {
        this.events.remove(event);
    }

    public void addOwnedEvent(Event ownedEvent) {
        this.ownedEvents.add(ownedEvent);
        if (!ownedEvent.getMembers().contains(this)){
            ownedEvent.addMember(this);
        }
    }

    @JsonIgnore
    public List<Event> getPublicEventsOfAUser(){
        return null;
    }

    @JsonIgnore
    public List<Event> getEventsOfAllFriends(){
        return null;
    }

    @JsonIgnore
    public List<Event> getEventsOfAllFriendsOfFriends(){
        return null;
    }

    @PreUpdate
    public void preUpdate() {
        updated = new Date();
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        created = now;
        updated = now;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}



