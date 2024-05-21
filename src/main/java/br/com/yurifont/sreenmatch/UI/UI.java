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

//        List<EpisodesData> episodesDatas = seasons.stream()
//                .flatMap(s -> s.episodes().stream())
//                .toList();
//
//        episodesDatas.stream()
//                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodesData::rating).reversed());

        List<Episode> episodes = seasons.stream()
                .flatMap(s -> s.episodes().stream()
                    .map(e -> new Episode(s.seasonNumber(), e))
                ).collect(Collectors.toList());
//
//        System.out.print("Enter the excerpt from title want search: ");
//        String excerptTitle = sc.nextLine();
//
//        Optional<Episode> findEpisode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(excerptTitle.toUpperCase()))
//                .findFirst();
//        if (findEpisode.isPresent()) {
//            System.out.println(findEpisode.get());
//        } else {
//            System.out.println("Episode not found!!!");
//        }


//        System.out.println("What year do you want to watch the episodes from?");
//        int year = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate searchDate = LocalDate.of(year, 1, 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes.stream()
//                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getSeason() +
//                                " Episode: " + e.getEpisodeNumber() +
//                                " Title: " + e.getTitle() +
//                                " Release Date: " + e.getReleaseDate().format(dtf)
//                ));
        Map<Integer, Double> seasonMap = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));
        System.out.println(seasonMap);

        DoubleSummaryStatistics statistics = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("Average note: " + statistics.getAverage());
        System.out.println("Best episode: " + statistics.getMax());
        System.out.println("Worst episode: " + statistics.getMin());
        System.out.println("Number of episodes evaluated: " + statistics.getCount());
    }
}
