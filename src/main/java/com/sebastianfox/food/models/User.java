package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.*;
//import com.sebastianfox.food.enums.PrivacyTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {

    /* #############################
        Variables
    ############################# */

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "user_id")
    private UUID id;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    /* *************************
     *  personal data
     ************************ */

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    /* *************************
     *  Security - Password and Salt
     ************************ */

    @JsonIgnore
    @Column(name = "password")
    private byte[] password;

    @JsonIgnore
    @Column(name = "salt")
    private byte[] salt;

    /* *************************
     *  Facebook - id / mail / socialAcoount
     ************************ */

    @Column(name = "facebook_account_id")
    private long facebookAccountId;

    @Column(name = "facebook_account_email")
    private String facebookAccountEmail;

    @Column(name = "facebook_user_name")
    private String facebookAccountUserName;

    @Column(name = "facebook_account")
    private boolean facebookAccount = false;

    /* *************************
     *  Events - list of evets and ownd events
     ************************ */

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @JsonIgnoreProperties({"text", "date", "privacyType", "eventType", "description", "maxParticipants", "location",
            "owner", "members", "interesteds", "tags", "acceptedFriends", "requestedFriendsByFriend",
            "requestedFriendsByCurrentUser"})
    private List<Event> events;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"text", "date", "privacyType", "eventType", "description", "maxParticipants", "location",
            "owner", "members", "interesteds", "tags", "acceptedFriends", "requestedFriendsByFriend",
            "requestedFriendsByCurrentUser"})
    private List<Event> ownedEvents;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_interested_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @JsonIgnoreProperties({"text", "date", "privacyType", "eventType", "description", "maxParticipants", "location",
            "owner", "members", "interesteds", "tags", "acceptedFriends", "requestedFriendsByFriend",
            "requestedFriendsByCurrentUser"})
    private List<Event> interestedEvents;

    /* *************************
     *  Friendships
     ************************ */

    @OneToMany(mappedBy = "friend1", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @JsonIgnoreProperties({"friend1", "friend2", "open", "accepted"})
    @JsonManagedReference(value = "friend1")
    private List<Friendship> friendshipsFriend1;

    @OneToMany(mappedBy = "friend2", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @JsonIgnoreProperties({"friend1", "friend2", "open", "accepted"})
    @JsonManagedReference(value = "friend2")
    private List<Friendship> friendshipsFriend2;

    /* *************************
     *  Status
     ************************ */

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private Date created;

    /* #############################
     *  Constructor
     ############################# */

    public User() {
        this.friendshipsFriend1 = new ArrayList<>();
        this.friendshipsFriend2 = new ArrayList<>();
        this.events = new ArrayList<>();
        this.ownedEvents = new ArrayList<>();
        this.interestedEvents = new ArrayList<>();
    }

    /* #############################
     *  Methods
     ############################# */

    /**
     * @param appUser data transformed as user from app
     */
    public void mergeDataFromOtherInstance(User appUser) {
        this.userName = appUser.userName;
        this.name = appUser.name;
        this.description = appUser.description;
        this.email = appUser.email;
    }

    /* #############################
     *  Getter and Setter methods
     ############################# */

    /* *************************
     *  User Information Handling
     ************************ */

    /**
     * Get id
     * @return Integer id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set id
     * @param id of user
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Get userName
     * @return String userName of user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set userName
     * @param userName of user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get name
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get email
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set email
     * @param email address of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get description
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* *************************
     *  Security Handling
     ************************ */

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

    /**
     * Set salt
     * @param salt to secure password
     */
    @JsonProperty
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /* *************************
     *  Facebook Handling
     ************************ */

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

    // Owned Events

    public List<Event> getOwnedEvents() {
        return ownedEvents;
    }

    public void setOwnedEvents(List<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    public void removeOwnedEventFromList(Event event) {
        this.ownedEvents.remove(event);
    }

    public void addOwnedEvent(Event ownedEvent) {
        this.ownedEvents.add(ownedEvent);
        if (!ownedEvent.getMembers().contains(this)) {
            ownedEvent.addMember(this);
        }
    }

    // Interested Events

    public List<Event> getInterestedEvents() {
        return interestedEvents;
    }

    public void setInterestedEvents(List<Event> interestedEvents) {
        this.interestedEvents = interestedEvents;
    }

    public void addInterestedEvent(Event interestedEvent) {
        this.interestedEvents.add(interestedEvent);
        if (!interestedEvent.getInteresteds().contains(this)) {
            interestedEvent.addInterested(this);
        }
    }

    public void removeInterestedEvent(Event interestedEvent) {
        this.interestedEvents.remove(interestedEvent);
        if (interestedEvent.getInteresteds().contains(this)) {
            interestedEvent.removeInterested(this);
        }
    }

    /* **********************************************************
        Friendship Handling
     ********************************************************** */

    @JsonIgnore
    public List<Friendship> getFriendshipsFriend1() {
        return friendshipsFriend1;
    }

    public void setFriendshipsFriend1(List<Friendship> friendshipsFriend1) {
        this.friendshipsFriend1 = friendshipsFriend1;
    }

    /**
     *  add friend to array of friends
     *
     *  @param friendship user
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
    @JsonIgnoreProperties({"userName", "email", "name", "description", "facebookAccountId", "facebookAccountEmail",
            "facebookAccountUserName", "facebookAccount", "events", "ownedEvents", "interestedEvents",
            "friendshipsFriend1", "friendshipsFriend2", "acceptedFriends", "requestedFriendsByFriend",
            "requestedFriendsByCurrentUser", "friend1", "friend2", "open", "accepted"})
    public List<Friendship> getAcceptedFriendships() {
        Iterator<Friendship> friendshipIterator1 = friendshipsFriend1.iterator();
        List<Friendship> acceptedFriendships =
                new ArrayList<>(this.getAcceptedFriendshipRequestsByFriendType(friendshipIterator1));
        Iterator<Friendship> friendshipIterator2 = friendshipsFriend2.iterator();
        acceptedFriendships.addAll(this.getAcceptedFriendshipRequestsByFriendType(friendshipIterator2));
        return acceptedFriendships;
    }

    @JsonIgnoreProperties({"userName", "email", "name", "description", "facebookAccountId", "facebookAccountEmail",
            "facebookAccountUserName", "facebookAccount", "events", "ownedEvents", "interestedEvents",
            "friendshipsFriend1", "friendshipsFriend2", "acceptedFriends", "requestedFriendsByFriend",
            "requestedFriendsByCurrentUser", "friend1", "friend2", "open", "accepted"})
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

    @JsonIgnoreProperties({"userName", "email", "name", "description", "facebookAccountId", "facebookAccountEmail",
            "facebookAccountUserName", "facebookAccount", "events", "ownedEvents", "interestedEvents",
            "friendshipsFriend1", "friendshipsFriend2", "acceptedFriends", "requestedFriendsByFriend",
            "requestedFriendsByCurrentUser", "friend1", "friend2", "open", "accepted"})
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

    @JsonIgnoreProperties({"userName", "email", "name", "description", "facebookAccountId", "facebookAccountEmail",
            "facebookAccountUserName", "facebookAccount", "events", "ownedEvents", "interestedEvents",
            "friendshipsFriend1", "friendshipsFriend2", "acceptedFriends", "requestedFriendsByFriend",
            "requestedFriendsByCurrentUser", "friend1", "friend2", "open", "accepted"})
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

    @SuppressWarnings({"Duplicates", "UnusedReturnValue"})
    public Friendship createAndAcceptFriendship(User friend) {
        Friendship friendship;
        // Check wether requested friendship already is requested by friend2
        if (this.getRequestedFriendsByFriend().contains(friend)) {
            List<Friendship> friendships = this.getFriendshipRequestsByFriend()
                    .stream()
                    .filter(c -> c.getFriend1() == friend)
                    .collect(Collectors.toList());
            if (friendships.size() != 1) {
                throw new IllegalStateException();
            }
            friendships.get(0).setAccepted(true);
            friendships.get(0).setOpen();
            return friendships.get(0);
        }
        // Check wether requested friendship already exists and is accepted
        else if (this.getAcceptedFriends().contains(friend)) {

            List<Friendship> friendships = this.getFriendshipRequestsByFriend()
                    .stream()
                    .filter(c -> c.getFriend2() == friend || c.getFriend1() == friend)
                    .collect(Collectors.toList());
            if (friendships.size() != 1) {
                throw new IllegalStateException();
            }
            return friendships.get(0);
        }
        // Check wether requested friendship already is requested by friend1
        else if (this.getRequestedFriendsByCurrentUser().contains(friend)) {

            List<Friendship> friendships = this.getFriendshipRequestsByFriend()
                    .stream()
                    .filter(c -> c.getFriend2() == friend)
                    .collect(Collectors.toList());
            if (friendships.size() != 1) {
                throw new IllegalStateException();
            }
            return friendships.get(0);
        }
        // Create new Friendship
        else {
            friendship = new Friendship(this, friend);
            return friendship;
        }
    }

    @JsonIgnore
    public List<User> getFriendsOfAFriend(User friend) {
        List<User> friendsOfAFriend = new ArrayList<>(friend.getAcceptedFriends());
        // check if this removal is necessary
        friendsOfAFriend.removeAll(this.getAcceptedFriends());
        friendsOfAFriend.remove(this);
        return friendsOfAFriend;
    }

    @JsonIgnore
    public List<User> getFriendsOfAllFriends() {
        List<User> friendsOfAllFriend = new ArrayList<>();
        for (User friend : this.getAcceptedFriends()) {
            List<User> friendsOfAFriend = new ArrayList<>(this.getFriendsOfAFriend(friend));
            // check if this removal is necessary
            friendsOfAFriend.removeAll(friendsOfAllFriend);
            friendsOfAllFriend.addAll(friendsOfAFriend);
        }
        return friendsOfAllFriend;
    }

    @JsonIgnore
    public List<User> getAllAvailableConnections() {
        List<User> availableConnections = new ArrayList<>(this.getAcceptedFriends());
        availableConnections.addAll(getFriendsOfAllFriends());
        return availableConnections;
    }

    @SuppressWarnings({"Duplicates", "UnusedReturnValue"})
    public Friendship declineAndDeleteFriendship(User friend) {
        if (this.getAcceptedFriends().contains(friend)) {
            List<Friendship> friendships = this.getAcceptedFriendships()
                    .stream()
                    .filter(c -> c.getFriend2() == friend || c.getFriend1() == friend)
                    .collect(Collectors.toList());
            if (friendships.size() != 1) {
                throw new IllegalStateException();
            }
            friendships.get(0).setAccepted(false);
            friendships.get(0).getFriend1().removeFriendshipFriend1(friendships.get(0));
            friendships.get(0).getFriend2().removeFriendshipFriend2(friendships.get(0));
            return friendships.get(0);
        } else if (this.getRequestedFriendsByCurrentUser().contains(friend)) {
            List<Friendship> friendships = this.getFriendshipRequestsByCurrentUser()
                    .stream()
                    .filter(c -> c.getFriend2() == friend)
                    .collect(Collectors.toList());
            if (friendships.size() != 1) {
                throw new IllegalStateException();
            }
            friendships.get(0).setAccepted(false);
            friendships.get(0).getFriend1().removeFriendshipFriend1(friendships.get(0));
            friendships.get(0).getFriend2().removeFriendshipFriend2(friendships.get(0));
            return friendships.get(0);

        } else if (this.getRequestedFriendsByFriend().contains(friend)) {
            List<Friendship> friendships = this.getFriendshipRequestsByFriend()
                    .stream()
                    .filter(c -> c.getFriend1() == friend)
                    .collect(Collectors.toList());
            if (friendships.size() != 1) {
                throw new IllegalStateException();
            }
            friendships.get(0).setAccepted(false);
            friendships.get(0).getFriend1().removeFriendshipFriend1(friendships.get(0));
            friendships.get(0).getFriend2().removeFriendshipFriend2(friendships.get(0));
            return friendships.get(0);
        }
        return null;
    }

    /* *************************
     *  Status
     ************************ */

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonIgnore
    public List<Event> getEventsOfAllFriendsOfFriends() {
        return null;
    }

    /* *************************
     *  Persistence information
     ************************ */

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

    /* ***************************************************************************************************************
        Provisions for further needs
     *************************************************************************************************************** */

    /* **********************************************************
        Events by connections and privacy types
     ********************************************************** */

//    @JsonIgnore
//    public List<Event> getEventsOfAUserByPrivacyTypes(User user, PrivacyTypes[] privacyTypes) {
//        List<Event> events = new ArrayList<>();
//        for (Event event : user.getEvents()) {
//            if (Arrays.asList(privacyTypes).contains(event.getPrivacyType())) {
//                events.add(event);
//            }
//        }
//        return events;
//    }

//    @JsonIgnore
//    public List<Event> getEventsOfAllConnections() {
//        List<Event> events = new ArrayList<>();
//        for (User user : getAcceptedFriends()) {
//            PrivacyTypes[] privacyTypes = {PrivacyTypes.FRIENDS, PrivacyTypes.FRIENDSOFFRIENDS, PrivacyTypes.PUBLIC};
//            events.addAll(this.getEventsOfAUserByPrivacyTypes(user, privacyTypes));
//        }
//        for (User user : getFriendsOfAllFriends()) {
//            PrivacyTypes[] privacyTypes = {PrivacyTypes.FRIENDSOFFRIENDS, PrivacyTypes.PUBLIC};
//            events.addAll(this.getEventsOfAUserByPrivacyTypes(user, privacyTypes));
//        }
//        return events;
//    }
}