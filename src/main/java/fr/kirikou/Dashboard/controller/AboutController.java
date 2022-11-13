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

        // weather
        JSONObject weather = new JSONObject();
        weather.put("name", "weather");

        JSONObject firstWeatherWidget = new JSONObject();
        firstWeatherWidget.put("name", "Weather Report");
        firstWeatherWidget.put("description", "Display the current weather and temperature for a city");

        JSONArray weatherParams = new JSONArray();
        JSONObject weatherParam = new JSONObject();
        weatherParam.put("name", "city");
        weatherParam.put("type", "string");
        weatherParams.add(weatherParam);
        firstWeatherWidget.put("params", weatherParams);

        JSONArray weatherWidgets = new JSONArray();
        weatherWidgets.add(firstWeatherWidget);
        weather.put("widgets", weatherWidgets);
        // weather

        // pollution
        JSONObject pollution = new JSONObject();
        pollution.put("name", "pollution");

        JSONObject firstPollutionWidget = new JSONObject();
        firstPollutionWidget.put("name", "Pollution Report");
        firstPollutionWidget.put("description", "Display the current pollution level for a city");

        JSONArray pollutionParams = new JSONArray();
        JSONObject pollutionParam = new JSONObject();
        pollutionParam.put("name", "city");
        pollutionParam.put("type", "string");
        pollutionParams.add(pollutionParam);
        firstPollutionWidget.put("params", pollutionParams);

        JSONArray pollutionWidgets = new JSONArray();
        pollutionWidgets.add(firstPollutionWidget);
        pollution.put("widgets", pollutionWidgets);
        // pollution

        // Youtube
        JSONObject youtube = new JSONObject();
        youtube.put("name", "youtube stats");

        JSONObject firstYoutubeWidget = new JSONObject();
        firstYoutubeWidget.put("name", "Video Stats");
        firstYoutubeWidget.put("description", "Show stats for a youtube video");

        JSONArray youtubeParams = new JSONArray();
        JSONObject youtubeParam = new JSONObject();
        youtubeParam.put("name", "video");
        youtubeParam.put("type", "string");
        youtubeParams.add(youtubeParam);
        firstYoutubeWidget.put("params", youtubeParams);

        JSONObject secondYoutubeWidget = new JSONObject();
        secondYoutubeWidget.put("name", "Channel Stats");
        secondYoutubeWidget.put("description", "Show stats for a youtube channel");

        JSONArray youtubeSecondParams = new JSONArray();
        JSONObject youtubeSecondParam = new JSONObject();
        youtubeSecondParam.put("name", "video");
        youtubeSecondParam.put("type", "string");
        youtubeSecondParams.add(youtubeSecondParam);
        secondYoutubeWidget.put("params", youtubeSecondParams);

        JSONArray youtubeWidgets = new JSONArray();
        youtubeWidgets.add(firstYoutubeWidget);
        youtubeWidgets.add(secondYoutubeWidget);
        youtube.put("widgets", youtubeWidgets);
        // Youtube

        services.add(weather);
        services.add(pollution);
        services.add(youtube);
        server.put("services", services);

        toSend.put("server", server);
        return toSend;
    }
}
