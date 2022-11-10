package fr.kirikou.Dashboard.controller;

import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import fr.kirikou.Dashboard.dto.CityLocationDTO;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    @Autowired
    private Environment env;

    private String apiKey;

    private final String INTERNAL_ERROR_CODE = "{\"error\": \"Internal server error\"}";

    @PostConstruct
    public void init() {
        apiKey = env.getProperty("openweather.apiKey");
    }

    @GetMapping(value = "/geolocation", produces = "application/json")
    public String getGeolocation(@RequestParam String city,
                                 @RequestParam(required = false) String state,
                                 @RequestParam(required = false) String country,
                                 HttpServletResponse response) {
        String fetchUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city;

        if (state != null)
            fetchUrl += "," + state;

        if (country != null)
            fetchUrl += "," + country;

        fetchUrl += "&appid=" + apiKey;

        Optional<Object> data = fetchOpenWeatherData(fetchUrl, response);

        if (data.isPresent()) {
            JSONArray arr = (JSONArray) data.get();

            if (arr.size() > 0) {
                JSONObject cityData = (JSONObject) arr.get(0);

                CityLocationDTO locData = new CityLocationDTO();
                locData.setLat((double) cityData.get("lat"));
                locData.setLon((double) cityData.get("lon"));
                locData.setCity((String) cityData.get("name"));
                locData.setState((String) cityData.get("state"));
                locData.setCountry((String) cityData.get("country"));

                return new Gson().toJson(locData);
            } else {
                return "{\"error\": \"Unknown city\"}";
            }
        } else {
            return INTERNAL_ERROR_CODE;
        }
    }

    @GetMapping(value = "/current", produces = "application/json")
    public String getCurrentWeather(@RequestParam double lat,
                                    @RequestParam double lon,
                                    HttpServletResponse response) {
        String fetchUrl = "https://api.openweathermap.org/data/2.5/weather";
        fetchUrl += String.format(Locale.US, "?lat=%f", lat);
        fetchUrl += String.format(Locale.US, "&lon=%f", lon);
        fetchUrl += "&units=metric";
        fetchUrl += "&appid=" + apiKey;

        Optional<Object> data = fetchOpenWeatherData(fetchUrl, response);

        if (data.isPresent()) {
            JSONObject obj = (JSONObject) data.get();
            return obj.toJSONString();
        } else {
            return INTERNAL_ERROR_CODE;
        }
    }

    @GetMapping(value = "/pollution", produces = "application/json")
    public String getPollution(@RequestParam double lat,
                               @RequestParam double lon,
                               HttpServletResponse response) {
        String fetchUrl = "https://api.openweathermap.org/data/2.5/air_pollution";
        fetchUrl += String.format(Locale.US, "?lat=%f", lat);
        fetchUrl += String.format(Locale.US, "&lon=%f", lon);
        fetchUrl += "&appid=" + apiKey;

        Optional<Object> data = fetchOpenWeatherData(fetchUrl, response);

        if (data.isPresent()) {
            JSONObject obj = (JSONObject) data.get();
            return obj.toJSONString();
        } else {
            return INTERNAL_ERROR_CODE;
        }
    }

    private Optional<Object> fetchOpenWeatherData(String fetchUrl, HttpServletResponse response) {
        try {
            URL url = new URL(fetchUrl);
            InputStream in = url.openConnection().getInputStream();
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
