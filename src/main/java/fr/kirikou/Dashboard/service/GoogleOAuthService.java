package fr.kirikou.Dashboard.service;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import fr.kirikou.Dashboard.exceptions.RefreshTokenExpiredException;
import fr.kirikou.Dashboard.model.OAuthToken;
import fr.kirikou.Dashboard.model.User;
import fr.kirikou.Dashboard.repository.OAuthTokenRepository;
import fr.kirikou.Dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Service
public class GoogleOAuthService {
    @Autowired
    private Environment env;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OAuthTokenRepository tokenRepository;

    private GoogleClientSecrets secrets;

    @PostConstruct
    public void initAPI() {
        try {
            InputStreamReader reader = new InputStreamReader(resourceLoader.getResource("classpath:google_secrets.json").getInputStream());
            secrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), reader);

            System.out.println(getAuthCodeURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // https://developers.google.com/identity/protocols/oauth2/scopes
    public String getAuthCodeURL() {
        GoogleAuthorizationCodeRequestUrl authUrl = new GoogleAuthorizationCodeRequestUrl(
                secrets.getWeb().getClientId(),
                secrets.getWeb().getRedirectUris().get(0),
                Arrays.asList(
                        "https://www.googleapis.com/auth/userinfo.profile",

                        "https://www.googleapis.com/auth/youtube.readonly",
                        "https://www.googleapis.com/auth/youtube.readonly"
                )
        );

        return authUrl.build();
    }

    public void requestAndSaveToken(User user, String code) {
        GoogleAuthorizationCodeTokenRequest request = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                secrets.getWeb().getClientId(),
                secrets.getWeb().getClientSecret(),
                code,
                secrets.getWeb().getRedirectUris().get(0)
        );

        try {
            GoogleTokenResponse response = request.execute();
            Date expireDate = new Date(System.currentTimeMillis() + response.getExpiresInSeconds() * 1000);

            OAuthToken token = new OAuthToken(
                    OAuthToken.OAuthType.GOOGLE,
                    response.getAccessToken(),
                    expireDate,
                    response.getRefreshToken());

            if (user.getGoogleToken() != null) {
                tokenRepository.delete(user.getGoogleToken());
            }

            user.setGoogleToken(token);

            tokenRepository.save(token);
            userRepository.save(user);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void refreshUserToken(User user) throws Exception {
        OAuthToken token = user.getGoogleToken();

        if (token == null)
            throw new Exception("User do not have a token");

        if (token.getRefreshToken() == null)
            throw new RefreshTokenExpiredException();

        GoogleRefreshTokenRequest request = new GoogleRefreshTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                token.getRefreshToken(),
                secrets.getWeb().getClientId(),
                secrets.getWeb().getClientSecret());

        try {
            GoogleTokenResponse response = request.execute();
            Date expireDate = new Date(System.currentTimeMillis() + response.getExpiresInSeconds() * 1000);

            token.setAccessToken(response.getAccessToken());
            token.setAccessExpire(expireDate);
            token.setRefreshToken(response.getRefreshToken());
            tokenRepository.save(token);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
