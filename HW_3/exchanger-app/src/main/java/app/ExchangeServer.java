package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ExchangeServer {
    public static void main(String[] args) {
        SpringApplication dockerApp = new SpringApplication(ExchangeServer.class);
        dockerApp.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        dockerApp.run(args);
    }
}
