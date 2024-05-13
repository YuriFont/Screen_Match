package br.com.yurifont.sreenmatch.UI;

import br.com.yurifont.sreenmatch.model.EpisodesData;
import br.com.yurifont.sreenmatch.model.SeasonData;
import br.com.yurifont.sreenmatch.model.SeriesData;
import br.com.yurifont.sreenmatch.service.ConsumeAPI;
import br.com.yurifont.sreenmatch.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    private Scanner sc = new Scanner(System.in);
    private final String URL = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=dda2e16f";
    private final String SEASON = "&season=";
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConvertData cd = new ConvertData();

    public void showMenu() {
        System.out.print("Enter the name of the series you want to search for: ");
        String nameSeries = sc.nextLine();
        String json = consumeAPI.getData(URL + nameSeries.replaceAll(" ", "_") + API_KEY);
        SeriesData serie = cd.getData(json, SeriesData.class);
        System.out.println(serie + "\n");

        List<SeasonData> seasons = new ArrayList<>();
        for (int i = 1; i <= Integer.parseInt(serie.totalSeasons()); i++) {
            json = consumeAPI.getData(URL + nameSeries.replaceAll(" ", "_")  + SEASON + i + API_KEY);
            SeasonData seasonData = cd.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }

        List<EpisodesData> episodes = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .toList();

        episodes.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodesData::rating).reversed())
                .forEach(System.out::println);
    }
}
