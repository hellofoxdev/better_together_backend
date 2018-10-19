package com.sebastianfox.food.entity;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity // This tells Hibernate to make a table out of this class
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="message_id")
    private Integer id;

    private String identifier;

    private String msgGerman;

    private String msgEnglish;

    public String getMsgByLanguage(String language) {
        if (language.toUpperCase().equals("GERMAN")) {
            return msgGerman;
        }
        else {
            return msgEnglish;
        }
    }

    /**
     * Getter and Setter
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMsgGerman() {
        return msgGerman;
    }

    public void setMsgGerman(String msgGerman) {
        this.msgGerman = msgGerman;
    }

    public String getMsgEnglish() {
        return msgEnglish;
    }

    public void setMsgEnglish(String msgEnglish) {
        this.msgEnglish = msgEnglish;
    }
}
