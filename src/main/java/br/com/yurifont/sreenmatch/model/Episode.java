package br.com.yurifont.sreenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(String season, EpisodesData e) {
        this.season = Integer.parseInt(season);
        this.title = e.title();
        this.episodeNumber = e.episodeNumber();
        try {
            this.rating = Double.valueOf(e.rating());
            this.releaseDate = LocalDate.parse(e.releaseDate());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        } catch (DateTimeException ex) {
            this.releaseDate = null;
        }
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

    @Override
    public String toString() {
        return "Season: " + this.getSeason() +
                ", Title: " + this.getTitle() +
                ", EpisodeNumber: " + this.getEpisodeNumber() +
                ", Rating: " + this.getRating() +
                ", ReleaseDate: " + this.getReleaseDate();
    }
}
