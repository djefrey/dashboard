package fr.kirikou.Dashboard.controller;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.gson.Gson;
import fr.kirikou.Dashboard.dto.YoutubeStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/youtube")
public class YoutubeController {

    @Autowired
    private Environment env;

    private YouTube youtube;

    private static final String UNKNOWN_VIDEO = "{\"error\":\"Unknown video\"}";
    private static final String INTERNAL_ERROR = "{\"error\":\"Internal server error\"}";

    @PostConstruct
    public void init() {
        youtube = new YouTube.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {}
        }).setApplicationName("Dashboard")
          .setYouTubeRequestInitializer(new YouTubeRequestInitializer(env.getProperty("google.apiKey"))).build();
    }

    @GetMapping(value = "/stats", produces = "application/json")
    public String getStats(@RequestParam String videoId,
                           HttpServletResponse response) {
        try {
            YouTube.Videos.List list = youtube.videos().list("snippet, statistics");
            list.setId(videoId);
            List<Video> videos = list.execute().getItems();

            if (videos.size() == 0) {
                response.setStatus(404);
                return UNKNOWN_VIDEO;
            }

            Video video = videos.get(0);
            YoutubeStatsDTO stats = new YoutubeStatsDTO();
            stats.setId(videoId);
            stats.setTitle(video.getSnippet().getTitle());
            stats.setViews(video.getStatistics().getViewCount().longValue());
            stats.setLikes(video.getStatistics().getLikeCount().longValue());
            stats.setComments(video.getStatistics().getCommentCount().longValue());
            stats.setThumbnail(video.getSnippet().getThumbnails().getMedium().getUrl());

            return new Gson().toJson(stats);
        } catch (IOException e) {
            System.err.println("ERROR:\n -> Video: " + videoId + "\n -> " + e.getMessage());
            response.setStatus(500);
            return INTERNAL_ERROR;
        }
    }

    @GetMapping(value = "/latest", produces = "application/json")
    public String getLatest(@RequestParam String channelName,
                            HttpServletResponse response) {
        try {
            YouTube.Search.List channelSearch = youtube.search().list("id,snippet");
            channelSearch.setQ(channelName);
            channelSearch.setType("channel");
            channelSearch.setMaxResults(1L);

            List<SearchResult> channelSearchResult = channelSearch.execute().getItems();
            if (channelSearchResult.size() == 0) {
                response.setStatus(404);
                return UNKNOWN_VIDEO;
            }

            SearchResult result = channelSearchResult.get(0);

            YouTube.Search.List videoSearch = youtube.search().list("id");
            videoSearch.setChannelId(result.getId().getChannelId());
            videoSearch.setType("video");
            videoSearch.setOrder("date");
            videoSearch.setMaxResults(1L);

            List<SearchResult> videoSearchResult = videoSearch.execute().getItems();
            if (videoSearchResult.size() == 0) {
                response.setStatus(404);
                return UNKNOWN_VIDEO;
            }

            SearchResult videoResult = videoSearchResult.get(0);
            Video video = youtube.videos().list("id,snippet,statistics")
                    .setId(videoResult.getId().getVideoId())
                    .execute()
                    .getItems().get(0);

            YoutubeStatsDTO stats = new YoutubeStatsDTO();
            stats.setId(video.getId());
            stats.setTitle(video.getSnippet().getTitle());
            stats.setViews(video.getStatistics().getViewCount().longValue());
            stats.setLikes(video.getStatistics().getLikeCount().longValue());
            stats.setComments(video.getStatistics().getCommentCount().longValue());
            stats.setThumbnail(video.getSnippet().getThumbnails().getMedium().getUrl());

            return new Gson().toJson(stats);
        } catch (IOException e) {
            System.err.println("ERROR:\n -> Channel: " + channelName + "\n -> " + e.getMessage());
            response.setStatus(500);
            return INTERNAL_ERROR;
        }
    }
}
