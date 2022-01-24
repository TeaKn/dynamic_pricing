package com.price.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import org.apache.logging.log4j.spi.LoggerContextKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class BQClientConfig {

    // TODO: IMPLEMENT CLIENT CONFIG CLASS

    private static final String GOOGLE_API_BASE_URL = "https://bigquery.googleapis.com";

    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests.

    public BigQuery bigQueryInit() throws IOException {
        return BigQueryOptions.newBuilder().setProjectId("dhimahi-dynamic-pricing-demo")
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:dhimahi-dynamic-pricing-demo-cb98c398daf8.json"))))
                .build().getService();
    }

    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(GOOGLE_API_BASE_URL)
                .build();
    }


}
