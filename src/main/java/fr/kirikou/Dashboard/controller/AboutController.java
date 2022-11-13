package fr.kirikou.Dashboard.controller;

import fr.kirikou.Dashboard.dto.UserDTO;
import fr.kirikou.Dashboard.dto.UserRegisterDTO;
import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.service.DashboardUserLogin;
import fr.kirikou.Dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Optional;
import java.net.InetAddress;

@RestController
public class AboutController {

    @GetMapping("/about.json")
    public JSONObject aboutJson(HttpServletRequest request) throws Exception {
        JSONObject toSend = new JSONObject();

        JSONObject client = new JSONObject();
        String ip = request.getRemoteAddr();
        ip = ip.substring(ip.indexOf("/") + 1, ip.length());
        client.put("host", ip);
        toSend.put("client", client);

        JSONObject server = new JSONObject();
        server.put("current_time", (System.currentTimeMillis() / 1000));

        JSONArray services = new JSONArray();

        // --- Weather Service ---
        JSONObject weatherService = new JSONObject();
        {
            weatherService.put("name", "weather");

            // Weather widget
            JSONObject first = new JSONObject();
            {
                first.put("name", "Weather Report");
                first.put("description", "Display the current weather and temperature for a city");

                JSONArray param = new JSONArray();
                JSONObject params = new JSONObject();
                params.put("name", "city");
                params.put("type", "string");
                param.add(param);
                first.put("params", params);
            }

            // Pollution Widget
            JSONObject second = new JSONObject();
            {
                second.put("name", "Pollution Report");
                second.put("description", "Display the current pollution for a city");

                JSONArray param = new JSONArray();
                JSONObject params = new JSONObject();
                params.put("name", "city");
                params.put("type", "string");
                param.add(param);
                second.put("params", params);
            }

            // Widgets Array
            JSONArray widgets = new JSONArray();
            widgets.add(first);
            widgets.add(second);
            weatherService.put("widgets", widgets);
        }
        // --- End Weather Service ---



        // --- Youtube Service ---
        JSONObject ytbService = new JSONObject();
        {
            ytbService.put("name", "youtube");

            // Stats Widget
            JSONObject first = new JSONObject();
            {
                first.put("name", "Youtube Stats");
                first.put("description", "Display the statistics of a given video");

                JSONArray param = new JSONArray();
                JSONObject params = new JSONObject();
                params.put("name", "url");
                params.put("type", "string");
                param.add(param);
                first.put("params", params);
            }

            // Latest Widget
            JSONObject second = new JSONObject();
            {
                second.put("name", "Youtube Latest");
                second.put("description", "Display the statistics of the latest video of a given channel");

                JSONArray param = new JSONArray();
                JSONObject params = new JSONObject();
                params.put("name", "channel name");
                params.put("type", "string");
                param.add(param);
                second.put("params", params);
            }

            // Widgets Array
            JSONArray widgets = new JSONArray();
            widgets.add(first);
            widgets.add(second);
            ytbService.put("widgets", widgets);
        }
        // --- End Youtube Service ---



        // --- Reddit Service ---
        JSONObject redditService = new JSONObject();
        {
            redditService.put("name", "reddit");

            // Stats Widget
            JSONObject first = new JSONObject();
            {
                first.put("name", "Reddit Karma");
                first.put("description", "Display the karma of a given user");

                JSONArray param = new JSONArray();
                JSONObject params = new JSONObject();
                params.put("name", "username");
                params.put("type", "string");
                param.add(param);
                first.put("params", params);
            }

            // Latest Widget
            JSONObject second = new JSONObject();
            {
                second.put("name", "Reddit Top Post");
                second.put("description", "Display the top post of a given subreddit");

                JSONArray param = new JSONArray();
                JSONObject params = new JSONObject();
                params.put("name", "subreddit");
                params.put("type", "string");
                param.add(param);
                second.put("params", params);
            }

            // Widgets Array
            JSONArray widgets = new JSONArray();
            widgets.add(first);
            widgets.add(second);
            redditService.put("widgets", widgets);
        }
        // --- End Reddit Service ---

        services.add(weatherService);
        services.add(ytbService);
        services.add(redditService);
        server.put("services", services);

        toSend.put("server", server);

        return toSend;
    }
}
