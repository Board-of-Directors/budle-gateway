package ru.nsu.fit.directors.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Configuration
public class CorsConfiguration {

    private static final String ALLOWED_HEADERS = "*";
    private static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOW_CREDENTIALS = "true";

    @Bean
    @Primary
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request) || CorsUtils.isPreFlightRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                String origin =
                    Optional.ofNullable(request.getHeaders().get("Origin"))
                        .map(list -> list.get(0))
                        .orElse(ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Origin", origin);
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
                headers.add("Access-Control-Allow-Credentials", ALLOW_CREDENTIALS);
                headers.add("Access-Control-Expose-Headers", ALLOWED_HEADERS);
                if (request.getMethod() == HttpMethod.OPTIONS || CorsUtils.isPreFlightRequest(request)) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

}