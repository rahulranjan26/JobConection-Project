package com.example.springboot.apigateway.filter;


import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.io.ObjectInputFilter;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final AuthFilter authFilter;

    public AuthenticationFilter(AuthFilter authFilter) {
        super(Config.class);
        this.authFilter = authFilter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("AuthenticationFilter : {}", exchange.getRequest().getURI());

            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            final String token = tokenHeader.split("Bearer ")[1];
            try {

                Long userId = authFilter.getUserIdFromToken(token);
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(r -> r.header("X-User-Id", String.valueOf(userId)))
                        .build();
                return chain.filter(mutatedExchange);
            } catch (JwtException e) {
                log.error("JWT Exception : {}", e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

        };
    }


    static class Config {

    }
}
