package learn.microservices.loans.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "loans")
@Getter
@Setter
public class DevInfoDTO {
    private String name;
    private String email;
}
