package fr.kirikou.Dashboard.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Date register;
    private boolean hasGoogleAccount;
    private boolean hasRedditAccount;
}
