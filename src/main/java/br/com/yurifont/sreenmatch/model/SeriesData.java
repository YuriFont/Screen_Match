package br.com.yurifont.sreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record SeriesData(@JsonAlias("Title") String title,
                         String imdbRating,
                         String totalSeasons) {
}
