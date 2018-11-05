package com.sebastianfox.food.entity.event.movie;

import com.sebastianfox.food.entity.event.Event;

import javax.persistence.*;

@Entity
@Table(name="movie_events")
public class MovieEvent extends Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="movie_event_id")
    private Integer id;

    private String movieTitle;

    private String genre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
