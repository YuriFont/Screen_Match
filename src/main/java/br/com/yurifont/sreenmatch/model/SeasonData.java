package br.com.yurifont.sreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Season") String seasonNumber,
                         @JsonAlias("Episodes") List<EpisodesData> episodes) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Season - ").append(this.seasonNumber()).append("\n");
        for (EpisodesData episode : episodes)
            sb.append(episode).append("\n");
        return sb.toString();
    }
}
