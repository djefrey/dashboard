package fr.kirikou.Dashboard;

import lombok.Getter;

@Getter
public enum OauthService {
    GOOGLE("google", "email"),
    REDDIT("reddit", "name");

    private String registrationId;
    private String identifier;

    OauthService(String registrationId, String identifier) {
        this.registrationId = registrationId;
        this.identifier = identifier;
    }

    public static OauthService getFromRegistrationId(String clientId) {
        for (OauthService service : values()) {
            if (service.registrationId.equals(clientId))
                return service;
        }
        return null;
    }
}
