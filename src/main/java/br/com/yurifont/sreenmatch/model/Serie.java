package br.com.yurifont.sreenmatch.model;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Double imdbRating;
    private String totalSeasons;
    private Category genre;
    private String actors;
    private String poster;
    private String plot;

    public Serie(SeriesData series) {
        this.title = series.title();
        this.totalSeasons = series.totalSeasons();
        this.imdbRating = OptionalDouble.of(Double.parseDouble(series.imdbRating())).orElse(0.0);
        this.genre = Category.fromString(series.genre().split(",")[0].trim());
        this.actors = series.actors();
        this.poster = series.poster();
        this.plot = series.poster();
    }
}
