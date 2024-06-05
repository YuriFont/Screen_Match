package br.com.yurifont.sreenmatch.UI;

import br.com.yurifont.sreenmatch.model.*;
import br.com.yurifont.sreenmatch.repository.SerieRepository;
import br.com.yurifont.sreenmatch.service.ConsumeAPI;
import br.com.yurifont.sreenmatch.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class UI {
    private final Scanner sc = new Scanner(System.in);
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=dda2e16f";
    private final ConsumeAPI consumeAPI = new ConsumeAPI();
    private final ConvertData cd = new ConvertData();
    private List<SeriesData> listSeriesData = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> listSerie = new ArrayList<>();
    private Optional<Serie> searchSerie;

    public UI(SerieRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        String menu = """
                
                1 - Search series
                2 - Search episodes
                3 - List search series
                4 - Search series by actor
                5 - List top 5 series
                6 - Search series by category
                7 - Filter by total seasons and rating
                8 - Search episode by section
                9 - Search top 5 episodes by serie
                
                0 - Exit
                """;
        String response;

        do {
            System.out.println(menu);
            response = sc.nextLine();

            switch (response) {
                case "1":
                    searchSeries();
                    break ;

                case "2":
                    searchEpisodes();
                    break ;

                case "3":
                    listSearchSeries();
                    break ;

                case "4":
                    searchSeriesByActor();
                    break ;

                case "5":
                    searchTop5Series();
                    break ;

                case "6":
                    searchSeriesByCategory();
                    break ;

                case "7":
                    filterBySeasonsAndRating();
                    break ;

                case "8":
                    searchEpisodeBySection();
                    break ;

                case "9":
                    searchTopEpisodesBySerie();
                    break ;

                case "0":
                    break ;

                default:
                    System.out.println("This option is invalid!!!");
            }
        } while (!response.equals("0"));
    }

    private void searchSeries() {
        SeriesData data = this.getDataSeries();
        if (data != null) {
            repository.save(new Serie(data));
            System.out.println(data);
        }
    }

    private SeriesData getDataSeries() {
        System.out.print("\nEnter the name of series want search: ");
        String series = sc.nextLine();
        this.listSerie = repository.findAll();

        searchSerie = listSerie.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(series.toLowerCase()))
                .findFirst();

        if (searchSerie.isPresent()) {
            System.out.println("\n" + searchSerie.get()+ "\n");
            return null;
        }
        String json = consumeAPI.getData(URL + series.replaceAll(" ", "_") + API_KEY);
        return cd.convertData(json, SeriesData.class);
    }

    private void searchEpisodes() {
        listSearchSeries();
        System.out.println("What series do you want search episodes?");
        String nameSerie = sc.nextLine();

        Optional<Serie> serie = listSerie.stream()
                        .filter(s -> s.getTitle().toLowerCase().contains(nameSerie.toLowerCase()))
                        .findFirst();

        if (serie.isPresent()) {
            Serie mySerie = serie.get();
            List<SeasonData> dataSeason = new ArrayList<>();
            String SEASON = "&season=";

            for (int i = 1; i <= mySerie.getTotalSeasons(); i++) {
                String json = consumeAPI.getData(URL + mySerie.getTitle().replaceAll(" ", "_") + SEASON + i + API_KEY);
                dataSeason.add(cd.convertData(json, SeasonData.class));
            }
            dataSeason.forEach(System.out::println);

            List<Episode> listEpisodes = dataSeason.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.seasonNumber(), e)))
                    .collect(Collectors.toList());

            mySerie.setEpisodes(listEpisodes);
            repository.save(mySerie);
        } else {
            System.out.println("This series is not in the database, choose 1 to add it.\n");
        }

    }

    private void listSearchSeries() {
        this.listSerie = repository.findAll();
        listSerie.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
        System.out.println();
    }

    private void searchSeriesByActor() {
        System.out.println("\nWhat is the name of the Actor or Actress you want to search for?");
        String actorName = sc.nextLine();
        List<Serie> seriesFound = repository.findByActorsContainingIgnoreCase(actorName);
        seriesFound.forEach(System.out::println);
    }

    private void searchTop5Series() {
        List<Serie> topSeries = repository.findTop5ByOrderByImdbRatingDesc();
        topSeries.forEach(System.out::println);
    }

    private void searchSeriesByCategory() {
        System.out.println("What category of series do you want search?");
        String categoryPtBr = sc.nextLine();
        Category category = Category.fromStringPtBr(categoryPtBr);
        List<Serie> listSeriesByCategory = repository.findByGenre(category);
        listSeriesByCategory.forEach(System.out::println);
    }

    private void filterBySeasonsAndRating() {
        System.out.print("Enter the maximum number of seasons the series must have:");
        int numSeasons = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the minimum evaluation score that the series must have:");
        double rating = sc.nextDouble();
        sc.nextLine();
        List<Serie> listSeries = repository.filterBySeasonsAndRating(numSeasons, rating);
        listSeries.forEach(System.out::println);
    }

    private void searchEpisodeBySection() {
        System.out.print("Enter the name of episode for search: ");
        String sectionOfEpisode = sc.nextLine();
        List<Episode> episodes = repository.searchEpisodeBySection(sectionOfEpisode);
        if (episodes.isEmpty())
            System.out.println("No episodes found!!!");
        else
            episodes.forEach(e ->
                    System.out.printf("\nSerie - %s\nSeason - %d\nTitle - %s\nNumber - %d\n\n", e.getSerie().getTitle(), e.getSeason(), e.getTitle(), e.getEpisodeNumber()));

    }

    private void searchTopEpisodesBySerie() {
        searchSeries();
        if (searchSerie.isPresent()) {
            Serie serie = searchSerie.get();
            List<Episode> topEpisodes = repository.searchTopEpisodesBySerie(serie);
            System.out.println("\nTop 5 episodes of " + serie.getTitle());
            topEpisodes.forEach(e ->
                    System.out.printf("\nTitle - %s\nSeason - %d\nNumber - %d\nRating - %.1f\n", e.getTitle(), e.getSeason(), e.getEpisodeNumber(), e.getRating()));
        }
    }
}
