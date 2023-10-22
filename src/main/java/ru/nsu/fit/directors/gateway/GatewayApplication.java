package ru.nsu.fit.directors.gateway;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    @Value("${swagger.baseUrl}")
    private String baseUrl;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenApi() {
        Server server = new Server();
        server.setUrl(baseUrl);
        return new OpenAPI().servers(List.of(server));
    }
}
