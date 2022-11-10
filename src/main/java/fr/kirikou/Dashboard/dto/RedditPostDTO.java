package fr.kirikou.Dashboard.dto;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class RedditPostDTO {
    private String title;
    private String author;
    private int ups;
    private @Nullable String thumbnail;
}
