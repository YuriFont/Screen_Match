package br.com.yurifont.sreenmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Double imdbRating;
    private String totalSeasons;
    @Enumerated(EnumType.STRING)
    private Category genre;
    private String actors;
    private String poster;
    private String plot;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodes = new ArrayList<>();

    public Serie() {
    }

    public Serie(SeriesData series) {
        this.title = series.title();
        this.totalSeasons = series.totalSeasons();
        this.imdbRating = OptionalDouble.of(Double.parseDouble(series.imdbRating())).orElse(0.0);
        this.genre = Category.fromString(series.genre().split(",")[0].trim());
        this.actors = series.actors();
        this.poster = series.poster();
        this.plot = series.plot();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(e -> e.setSerie(this));
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "\nTitle - " + this.getTitle() + "\n"
                + "Rating - " + this.getImdbRating() + "\n"
                + "Total seasons - " + this.getTotalSeasons() + "\n"
                + "Genre - " + this.getGenre() + "\n"
                + "Actor - " + this.getActors() + "\n"
                + "Poster - " + this.getPoster() + "\n"
                + "Plot - " + this.getPlot() + "\n"
                + "Episodes - " + this.getEpisodes() + "\n";
    }
}
