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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(String totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "titulo='" + title + '\'' +
                ", totalTemporadas=" + totalSeasons +
                ", avaliacao=" + imdbRating +
                ", genero=" + genre +
                ", atores='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse'" + plot + '\'' +
                '}';
    }
}
