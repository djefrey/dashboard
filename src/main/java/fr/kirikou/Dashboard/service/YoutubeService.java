package fr.kirikou.Dashboard.service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class YoutubeService {
    @Autowired
    private Environment env;

    private YouTube youtube;

    @PostConstruct
    public void initAPI() {
        youtube = new YouTube.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), request -> {})
                .setApplicationName("Dashboard")
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer(env.getProperty("google.apiKey")))
                .build();
    }
}
