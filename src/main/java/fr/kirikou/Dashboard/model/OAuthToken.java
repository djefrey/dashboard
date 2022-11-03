package fr.kirikou.Dashboard.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tokens")
public class OAuthToken {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Enumerated(EnumType.ORDINAL) OAuthType type;
    private String accessToken;
    private @Temporal(TemporalType.TIMESTAMP) Date accessExpire;
    private String refreshToken;

    public OAuthToken(OAuthType type, String accessToken, Date accessExpire, String refreshToken) {
        this.type = type;
        this.accessToken = accessToken;
        this.accessExpire = accessExpire;
        this.refreshToken  = refreshToken;
    }

    public enum OAuthType {
        GOOGLE,
    }
}
