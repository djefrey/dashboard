package fr.kirikou.Dashboard.dto;

import lombok.Data;

@Data
public class YoutubeStatsDTO {
    private String id;
    private String name;
    private long views;
    private long likes;
    private long comments;
    private String thumbnail;
}
