package br.com.yurifont.sreenmatch.model;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate releaseDate;
    @ManyToOne
    private Serie serie;

    public Episode() {
    }

    public Episode(String season, EpisodesData e) {
        this.season = Integer.parseInt(season);
        this.title = e.title();
        this.episodeNumber = e.episodeNumber();
        try {
            this.rating = Double.valueOf(e.rating());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        }
        try {
            this.releaseDate = LocalDate.parse(e.releaseDate());
        } catch (DateTimeException ex) {
            this.releaseDate = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Season: " + this.getSeason() +
                ", Title: " + this.getTitle() +
                ", EpisodeNumber: " + this.getEpisodeNumber() +
                ", Rating: " + this.getRating() +
                ", ReleaseDate: " + this.getReleaseDate();
    }
}
