package by.n1jel.auction.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ClientProperties {
    private String baseUrl = "http://localhost:8080" ;
}
