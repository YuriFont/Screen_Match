package br.com.yurifont.sreenmatch.UI;

import br.com.yurifont.sreenmatch.model.SeasonData;
import br.com.yurifont.sreenmatch.model.Serie;
import br.com.yurifont.sreenmatch.model.SeriesData;
import br.com.yurifont.sreenmatch.repository.SerieRepository;
import br.com.yurifont.sreenmatch.service.ConsumeAPI;
import br.com.yurifont.sreenmatch.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    private final Scanner sc = new Scanner(System.in);
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=dda2e16f";
    private final ConsumeAPI consumeAPI = new ConsumeAPI();
    private final ConvertData cd = new ConvertData();
    private List<SeriesData> listSeries = new ArrayList<>();
    private SerieRepository repository;

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
        repository.save(new Serie(data));
        System.out.println(data);
    }

    private SeriesData getDataSeries() {
        System.out.print("\nEnter the name of series want search: ");
        String series = sc.nextLine();
        String json = consumeAPI.getData(URL + series.replaceAll(" ", "_") + API_KEY);
        return cd.convertData(json, SeriesData.class);
    }

    private void searchEpisodes() {
        SeriesData data = getDataSeries();
        listSeries.add(data);
        List<SeasonData> dataSeason = new ArrayList<>();
        String SEASON = "&season=";

        for (int i = 1; i <= Integer.parseInt(data.totalSeasons()); i++) {
            String json = consumeAPI.getData(URL + data.title().replaceAll(" ", "_") + SEASON + i + API_KEY);
            dataSeason.add(cd.convertData(json, SeasonData.class));
        }
        dataSeason.forEach(System.out::println);
    }

    private void listSearchSeries() {
        List<Serie> series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
        System.out.println();
    }
}
