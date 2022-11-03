package fr.kirikou.Dashboard.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@Service
public class GoogleOAuthService {
    @Autowired
    private Environment env;

    @Autowired
    private ResourceLoader resourceLoader;

    private GoogleClientSecrets secrets;

    @PostConstruct
    public void initAPI() {
        try {
            InputStreamReader reader = new InputStreamReader(resourceLoader.getResource("classpath:google_secrets.json").getInputStream());
            secrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOAuthURL() {
        GoogleAuthorizationCodeRequestUrl authUrl = new GoogleAuthorizationCodeRequestUrl(
                secrets.getWeb().getClientId(),
                secrets.getWeb().getRedirectUris().get(0),
                Arrays.asList(
                        "https://www.googleapis.com/auth/userinfo.profile"
                )
        );

        return authUrl.build();
    }
}
