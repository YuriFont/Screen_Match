package br.com.yurifont.sreenmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesData(@JsonProperty("Title") String title,
                           @JsonProperty("Episode") Integer episodeNumber,
                           @JsonProperty("imdbRating") String rating,
                           @JsonProperty("Released") String releaseDate) {
}
