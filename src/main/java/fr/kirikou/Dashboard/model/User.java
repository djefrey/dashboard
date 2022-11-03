package fr.kirikou.Dashboard.model;

import fr.kirikou.Dashboard.dto.UserDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private @Column(length = 60) String password;
    private @Column(unique = true) String email;
    private @CreationTimestamp @Temporal(TemporalType.DATE) Date registerDate;
    private @OneToOne OAuthToken googleToken;

    public UserDTO toDTO() {
        UserDTO data = new UserDTO();
        data.setId(id);
        data.setName(name);
        data.setEmail(email);
        data.setRegister(registerDate);
        return data;
    }
}
