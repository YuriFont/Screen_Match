package br.com.yurifont.sreenmatch.UI;

import br.com.yurifont.sreenmatch.model.Episode;
import br.com.yurifont.sreenmatch.model.SeasonData;
import br.com.yurifont.sreenmatch.model.Serie;
import br.com.yurifont.sreenmatch.model.SeriesData;
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

    public UI(SerieRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        String menu = """
                1 - Search series
                2 - Search episodes
                3 - List search series
                
                0 - Exit
                """;
        String response;

        do {
            System.out.println(menu);
            response = sc.nextLine();

            switch (response) {
                case "1":
                    searchSeries();
                    break;

                case "2":
                    searchEpisodes();
                    break ;

                case "3":
                    listSearchSeries();
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

        Optional<Serie> mySerie= listSerie.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(series.toLowerCase()))
                .findFirst();

        if (mySerie.isPresent()) {
            System.out.println("\n" + mySerie.get()+ "\n");
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

            for (int i = 1; i <= Integer.parseInt(mySerie.getTotalSeasons()); i++) {
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
}
