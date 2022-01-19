package com.price.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    private static final String SRGSSR_API_BASE_URL = "https://api.srgssr.ch";

    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrations,
                new UnAuthenticatedServerOAuth2AuthorizedClientRepository()
        );
        oauth.setDefaultClientRegistrationId("srf-meteo");
        return WebClient.builder()
                .baseUrl(SRGSSR_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .filter(oauth)
                .build();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange().pathMatchers("/**").permitAll().and().build();
    }
}


