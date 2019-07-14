package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("unused")
@Entity
@Table(name="friendships")
public class Friendship {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "friendship_id")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "friend1_id", nullable=false)
//    @JsonIgnoreProperties({ "friend1", "friend2", "friendships1", "friendships2", "events", "ownedEvents" })
    @JsonIgnoreProperties({"userName", "email", "name", "description", "facebookAccountId", "facebookAccountEmail", "facebookAccountUserName", "facebookAccount", "events", "ownedEvents", "interestedEvents", "friendshipsFriend1", "friendshipsFriend2", "acceptedFriends", "requestedFriendsByFriend", "requestedFriendsByCurrentUser"})
    @JsonBackReference(value="friend1")
    private User friend1;

    @ManyToOne()
    @JoinColumn(name = "friend2_id", nullable=false)
//    @JsonIgnoreProperties({ "friend1", "friend2", "friendships1", "friendships2", "events", "ownedEvents" })
    @JsonIgnoreProperties({"userName", "email", "name", "description", "facebookAccountId", "facebookAccountEmail", "facebookAccountUserName", "facebookAccount", "events", "ownedEvents", "interestedEvents", "friendshipsFriend1", "friendshipsFriend2", "acceptedFriends", "requestedFriendsByFriend", "requestedFriendsByCurrentUser"})
    @JsonBackReference(value="friend2")
    private User friend2;

    private boolean accepted = false;

    private boolean open = true;

    @JsonIgnore
    private Date updated;

    @JsonIgnore
    private Date created;

    /**
     * Constructor
     */
    public Friendship(){}

    Friendship(User friend1, User friend2) {
        this.friend1 = friend1;
        this.friend2 = friend2;

        if (!friend1.getFriendshipsFriend1().contains(this)){
            friend1.addFriendshipFriend1(this);
        }

        if (!friend2.getFriendshipsFriend2().contains(this)){
            friend2.addFriendshipFriend2(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    User getFriend1() {
        return friend1;
    }

    public void setFriend1(User friend1) {
        this.friend1 = friend1;
        if (!friend1.getFriendshipsFriend1().contains(this)){
            friend1.addFriendshipFriend1(this);
        }
    }

    User getFriend2() {
        return friend2;
    }

    public void setFriend2(User friend2) {
        this.friend2 = friend2;
        if (!friend2.getFriendshipsFriend2().contains(this)){
            friend2.addFriendshipFriend2(this);
        }
    }

    boolean isAccepted() {
        return accepted;
    }

    void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    boolean isOpen() {
        return open;
    }

    void setOpen() {
        this.open = false;
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
