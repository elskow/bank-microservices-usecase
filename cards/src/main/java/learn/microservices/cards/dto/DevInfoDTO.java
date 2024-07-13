package learn.microservices.cards.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cards")
@Getter
@Setter
public class DevInfoDTO {
    private String name;
    private String email;
}
