package pl.connectis.spotifyapicli.authorization;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class AuthorizationCode {
    private String code;
}
