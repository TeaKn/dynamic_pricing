package com.price.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileInputStream;
import java.io.IOException;


@Configuration
public class BQClientConfig {

    // application credentials provide the required information about the caller making a request to a Google cloud api
    // presenting application credentials in requests to google cloud apis only identifies the caller as a registered application
    // if authentication is required the client must also identify the principal running the application, such as a user account or service account

    // to build an application using google cloud apis, follow these general steps:
    // - choose and use the provided Google cloud client libraries
    // - determine the correct authentication flow for your application
    // - pass the application credentials to the client libraries at application startup time, ideally through application default credentials


    // TODO: IMPLEMENT CLIENT CONFIG CLASS

    private static final String GOOGLE_API_BASE_URL = "https://bigquery.googleapis.com";
    public BigQuery bigQueryInit;
    //public Storage storage;


    @Bean // instantiate a new web client
    public WebClient bqClient() throws IOException {

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests.

        bigQueryInit = BigQueryOptions.newBuilder().setProjectId("dhimahi-dynamic-pricing-demo")
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:dhimahi-dynamic-pricing-demo-cb98c398daf8.json"))))
                .build().getService();

        //BigQuery bigqueryInit = BigQueryOptions.getDefaultInstance().getService();

        return WebClient.builder()
                .baseUrl(GOOGLE_API_BASE_URL)
                //.defaultHeader(HttpHeaders.AUTHORIZATION())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }


}
