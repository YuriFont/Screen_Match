package br.com.yurifont.sreenmatch.UI;

import br.com.yurifont.sreenmatch.model.Episode;
import br.com.yurifont.sreenmatch.model.EpisodesData;
import br.com.yurifont.sreenmatch.model.SeasonData;
import br.com.yurifont.sreenmatch.model.SeriesData;
import br.com.yurifont.sreenmatch.service.ConsumeAPI;
import br.com.yurifont.sreenmatch.service.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class UI {
    private Scanner sc = new Scanner(System.in);
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=dda2e16f";
    private final String SEASON = "&season=";
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConvertData cd = new ConvertData();

    public void showMenu() {
        String menu = """
                1 - Search Series
                2 - Search Episodes
                
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

                case "0":
                    break ;

                default:
                    System.out.println("This option is invalid!!!");
            }
        } while (!response.equals("0"));
    }

    private void searchSeries() {
        SeriesData data = this.getDataSeries();
        System.out.println(data);
    }

    private SeriesData getDataSeries() {
        System.out.print("Enter the name of series want search: ");
        String series = sc.nextLine();
        String json = consumeAPI.getData(URL + series.replaceAll(" ", "_") + API_KEY);
        return cd.convertData(json, SeriesData.class);
    }

    private void searchEpisodes() {
        SeriesData data = getDataSeries();
        List<SeasonData> dataSeason = new ArrayList<>();

        for (int i = 1; i <= Integer.parseInt(data.totalSeasons()); i++) {
            String json = consumeAPI.getData(URL + data.title().replaceAll(" ", "_") + "&season=" + i + API_KEY);
            dataSeason.add(cd.convertData(json, SeasonData.class));
        }
        dataSeason.forEach(System.out::println);
    }
}
