package com.sebastianfox.food.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Entity // This tells Hibernate to make a table out of this class
@Table(name="label_texts")
public class LabelText {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="label_text_id")
    private Integer id;

    private String identifier;

    @JsonIgnore
    private String lblGerman;

    @JsonIgnore
    private String lblEnglish;

    private String text = "";

    public void setTextByLanguage(String language) {
        if (language.toUpperCase().equals("DE")) {
            this.setText(lblGerman);
        }
        else {
            this.setText(lblEnglish);
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

    public String getLblGerman() {
        return lblGerman;
    }

    public void setLblGerman(String lblGerman) {
        this.lblGerman = lblGerman;
    }

    public String getLblEnglish() {
        return lblEnglish;
    }

    public void setLblEnglish(String lblEnglish) {
        this.lblEnglish = lblEnglish;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
