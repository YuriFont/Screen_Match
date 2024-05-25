package br.com.yurifont.sreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(@JsonAlias("Title") String title,
                         String imdbRating,
                         String totalSeasons,
                         @JsonAlias("Genre") String genre,
                         @JsonAlias("Actors") String actors,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String plot) {
    @Override
    public String toString() {
        return "\nTitle - " + this.title() + "\n"
                + "Rating - " + this.imdbRating() + "\n"
                + "Total seasons - " + this.totalSeasons() + "\n";
    }
}
