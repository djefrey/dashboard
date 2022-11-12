package fr.kirikou.Dashboard.controller;

import com.google.gson.Gson;
import fr.kirikou.Dashboard.DashboardUtils;
import fr.kirikou.Dashboard.dto.RedditKarmaDTO;
import fr.kirikou.Dashboard.dto.RedditPostDTO;
import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.service.DashboardUserLogin;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/reddit")
public class RedditController {

    private static final String NOT_LINKED_ERROR = "{\"error\":\"No account linked\"}";
    private static final String EXPIRED_TOKEN_ERROR = "{\"error\":\"Expired token\"}";
    private static final String INTERNAL_ERROR_CODE = "{\"error\": \"Internal server error\"}";

    @GetMapping(value = "/karma", produces = "application/json")
    public String getKarma(@RequestParam String username, HttpServletResponse response) {
        User user = DashboardUtils.getActualUser();
        Optional<String> err;

        if ((err = verifyToken(user)).isPresent())
            return err.get();

        String url = "https://oauth.reddit.com/user/" + username + "/about.json";
        Optional<Object> data = fetchRedditWithOauth(url, user.getRedditToken(), response);

        if (data.isPresent()) {
            JSONObject obj = (JSONObject) data.get();
            JSONObject karmaData = (JSONObject) obj.get("data");
            RedditKarmaDTO result = new RedditKarmaDTO();
            result.setCommentKarma((int) karmaData.get("comment_karma"));
            result.setTotalKarma((int) karmaData.get("total_karma"));
            return new Gson().toJson(result);
        } else {
            return INTERNAL_ERROR_CODE;
        }
    }

    @GetMapping(value = "/top", produces = "application/json")
    public String getTopPost(@RequestParam String subreddit, HttpServletResponse response) {
        User user = DashboardUtils.getActualUser();
        Optional<String> err;

        if ((err = verifyToken(user)).isPresent())
            return err.get();

        String url = "https://reddit.com/r/" + subreddit + "/top/.json";
        Optional<Object> data = fetchRedditWithOauth(url, user.getRedditToken(), response);

        if (data.isPresent()) {
            JSONObject obj = (JSONObject) data.get();
            JSONObject info = (JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) obj.get("data")).get("children")).get(0)).get("data");

            RedditPostDTO result = new RedditPostDTO();
            result.setTitle((String) info.get("title"));
            result.setAuthor((String) info.get("author"));
            result.setUps((int) info.get("ups"));
            result.setThumbnail((String) info.get("thumbnail"));
            return new Gson().toJson(result);
        } else {
            return INTERNAL_ERROR_CODE;
        }
    }

    private Optional<String> verifyToken(User user) {
        if (user.getRedditToken() == null || user.getRedditTokenExpires() == null) {
            return Optional.of(NOT_LINKED_ERROR);
        } else if (user.getRedditTokenExpires().before(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))) {
            return Optional.of(EXPIRED_TOKEN_ERROR);
        }
        return Optional.empty();
    }

    private Optional<Object> fetchRedditWithOauth(String fetchUrl, String oauth, HttpServletResponse response) {
        try {
            URL url = new URL(fetchUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + oauth);
            conn.setRequestProperty(HttpHeaders.USER_AGENT, "Dashboard by Kirikou Corp.");

            InputStream in = conn.getInputStream();
            JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);
            Object obj = parser.parse(in);

            return Optional.of(obj);
        } catch (IOException | ParseException e) {
            System.err.println("ERROR: \n -> Fetch " + fetchUrl + "\n -> " + e.getMessage());
            response.setStatus(500);
            return Optional.empty();
        }
    }
}
