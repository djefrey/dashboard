package fr.kirikou.Dashboard.model;

import fr.kirikou.Dashboard.dto.UserDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private @Nullable @Column(length = 60) String password;
    private @Nullable @Column(unique = true) String email;
    private @CreationTimestamp @Temporal(TemporalType.DATE) Date registerDate;

    private @Nullable String googleMail;
    private @Nullable String googleToken;
    private @Nullable @Temporal(TemporalType.TIMESTAMP) Date googleTokenExpires;

    private @Nullable String redditName;
    private @Nullable String redditToken;
    private @Nullable @Temporal(TemporalType.TIMESTAMP) Date redditTokenExpires;

    public UserDTO toDTO() {
        UserDTO data = new UserDTO();
        data.setId(id);
        data.setName(name);
        data.setEmail(email);
        data.setRegister(registerDate);
        return data;
    }
}
