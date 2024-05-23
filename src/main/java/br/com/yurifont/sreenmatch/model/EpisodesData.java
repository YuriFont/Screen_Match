package br.com.yurifont.sreenmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesData(@JsonProperty("Title") String title,
                           @JsonProperty("Episode") Integer episodeNumber,
                           @JsonProperty("imdbRating") String rating,
                           @JsonProperty("Released") String releaseDate) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Title: ").append(this.title());
        sb.append(", Number: ").append(this.episodeNumber());
        sb.append(", Rating: ").append(this.rating());
        sb.append(", Release date: ").append(this.releaseDate()).append("\n");
        return sb.toString();
    }
}
