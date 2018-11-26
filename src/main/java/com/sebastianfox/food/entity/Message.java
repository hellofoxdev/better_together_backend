package com.sebastianfox.food.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonIgnore
    private String msgGerman;

    @JsonIgnore
    private String msgEnglish;

    private String text = "";

    public void setTextByLanguage(String language) {
        if (language.toUpperCase().equals("DE")) {
            this.setText(msgGerman);
        }
        else {
            this.setText(msgGerman);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
