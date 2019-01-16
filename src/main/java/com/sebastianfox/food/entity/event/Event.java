package com.sebastianfox.food.entity.event;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sebastianfox.food.entity.user.User;
import com.sebastianfox.food.entity.event.Tag;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="event_id")
    private Integer id;

    private String title;

    private String location;

    private Date date;

    private boolean publicEvent = false;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable=false)
    @JsonIgnoreProperties({"events", "ownedEvents"})
    private User owner;

    private String description;

    private Integer maxParticipants;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events", "ownedEvents"})
    private Set<User> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events", "ownedEvents"})
    private Set<User> memberRequests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "events")
    @JsonIgnoreProperties({"events"})
    private Set<Tag> tags = new HashSet<>();


    //   Getter and Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public boolean isPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(boolean publicEvent) {
        this.publicEvent = publicEvent;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        if (!owner.getOwnedEvents().contains(this)){
            owner.addOwnedEvent(this);
        }
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addMember(User member) {
        this.members.add(member);
        if (!member.getEvents().contains(this)) {
            member.addEvent(this);
        }
    }

    public void removeMember(User member) {
        this.members.remove(member);
        if (member.getEvents().contains(this)) {
            member.removeEvent(this);
        }
    }

    public void clearMembers() {
        for( User member: members )
        {
            if (member.getEvents().contains(this)) {
                member.removeEvent(this);
            }
            this.members.remove(member);
        }
    }

    public Set<User> getMemberRequests() {
        return memberRequests;
    }

    public void setMemberRequests(Set<User> memberRequests) {
        this.memberRequests = memberRequests;
    }

    public void addMemberRequest(User memberRequest) {
        this.memberRequests.add(memberRequest);
        if (!memberRequest.getEvents().contains(this)) {
            memberRequest.addEvent(this);
        }
    }

    public void removeMemberRequest(User memberRequest) {
        this.memberRequests.remove(memberRequest);
        if (memberRequest.getEvents().contains(this)) {
            memberRequest.removeEvent(this);
        }
    }

    public void clearMemberRequests() {
        for( User memberRequest: memberRequests )
        {
            if (memberRequest.getEvents().contains(this)) {
                memberRequest.removeEvent(this);
            }
            this.memberRequests.remove(memberRequest);
        }
    }


    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        if (!tag.getEvents().contains(this)) {
            tag.addEvent(this);
        }
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        if (tag.getEvents().contains(this)) {
            tag.removeEvent(this);
        }
    }

    public void clearTags() {
        for( Tag tag: tags )
        {
            if (tag.getEvents().contains(this)) {
                tag.removeEvent(this);
            }
            this.tags.remove(tag);
        }
    }

    public void removeFromOwnersList() {
        this.owner.removeOwnedEventFromList(this);
    }
}
