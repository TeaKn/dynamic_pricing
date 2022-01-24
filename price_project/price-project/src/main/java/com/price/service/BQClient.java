package com.price.service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.*;
import com.price.config.BQClientConfig;
import org.apache.logging.log4j.spi.LoggerContextKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class BQClient {

    @Autowired
    private final BQClientConfig bqClientConfig;
    
    @Autowired
    private WebClient bqClient;
    
    private BQClient(BQClientConfig bqClientConfig) {
        this.bqClientConfig = bqClientConfig;
    }

    public String normalizedDataWithCorrelationCoeff() throws IOException {
        String projectId = "dhimahi-dynamic-pricing-demo";
        String datasetName = "bqml_tutorial";

        String query2 = "SELECT ( (a.num_trips - AVG(a.num_trips) OVER () ) / NULLIF(STDDEV_POP(a.num_trips) OVER (), 0)) AS test_normalized, ( (b.prcp - AVG(b.prcp) OVER () ) / NULLIF(STDDEV_POP(b.prcp) OVER (), 0)) AS test_normalized_prcp, a.date, CORR(a.num_trips, b.prcp) OVER() FROM `dhimahi-dynamic-pricing-demo.bqml_tutorial.sum bike trips 2015 ny` a JOIN `dhimahi-dynamic-pricing-demo.bqml_tutorial.percipation 2015 ny` b USING (date)";

        String query = "SELECT ( (a.num_trips - AVG(a.num_trips) OVER () ) /"
                + " NULLIF(STDDEV_POP(a.num_trips) OVER (), 0) "
                + ") AS test_normalized, "
                + "( (b.prcp - AVG(b.prcp) OVER () ) /"
                + "NULLIF(STDDEV_POP(b.prcp) OVER (), 0) "
                + ") AS test_normalized_prcp, "
                + "a.date, CORR(a.num_trips, b.prcp) OVER() "
                + "FROM `" + projectId + "." + datasetName + "." + "sum bike trips 2015 ny` a "
                + "JOIN `" + projectId + "." + datasetName + "."  + "percipation 2015 ny` b USING (date)";


        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(query2)
                                .setUseLegacySql(false).build();
        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bqClientConfig.bigQueryInit.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        try {
            queryJob = queryJob.waitFor();
            // Check for errors
            if (queryJob == null) {
                throw new RuntimeException("Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                // You can also look at queryJob.getStatus().getExecutionErrors() for all
                // errors, not just the latest one.
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }
            // Get the results.
            TableResult result = queryJob.getQueryResults();
            Flux<String> returnValue = new Flux<String>() {
                @Override
                public void subscribe(CoreSubscriber<? super String> coreSubscriber) {

                }
            };
            return result.toString();

        } catch (BigQueryException | InterruptedException e) {
            System.out.println("Query not performed \n" + e);

        }
        return "some Error occured with the query";
    }

    public Mono<String> getArimaModel(String projectId, String datasetId, String modelId) {
        // TODO: IMPLEMENT GET ARIMA MODEL, ne radi 401
        // GET https://bigquery.googleapis.com/bigquery/v2/projects/{projectId}/datasets/{datasetId}/models/{modelId}
        return bqClient.get()
                .uri("/bigquery/v2/projects/{projectId}/datasets/{datasetId}/models/{modelId}", projectId, datasetId, modelId)
                .retrieve()
                .bodyToMono(String.class);

    }

    public String ArimaModel() throws IOException {

        String query = """
                #standardSQL
                CREATE OR REPLACE MODEL bqml_tutorials.nyc_citibike_arima_model_group
                OPTIONS
                  (model_type = 'ARIMA_PLUS',
                   time_series_timestamp_col = 'date',
                   time_series_data_col = 'num_trips',
                   time_series_id_col = 'start_station_name',
                   auto_arima_max_order = 5
                  ) AS
                SELECT
                   start_station_name,
                   EXTRACT(DATE from starttime) AS date,
                   COUNT(*) AS num_trips
                FROM
                  `bigquery-public-data`.new_york.citibike_trips
                WHERE start_station_name LIKE '%Central Park%'
                GROUP BY start_station_name, date
                """;

        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(query)
                        .setUseLegacySql(false).build();

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bqClientConfig.bigQueryInit.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        try {
            queryJob = queryJob.waitFor();
            // Check for errors
            if (queryJob == null) {
                throw new RuntimeException("Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                // You can also look at queryJob.getStatus().getExecutionErrors() for all
                // errors, not just the latest one.
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }
            // Get the results.
            TableResult result = queryJob.getQueryResults();
            Flux<String> returnValue = new Flux<String>() {
                @Override
                public void subscribe(CoreSubscriber<? super String> coreSubscriber) {

                }
            };
            return result.toString();

        } catch (BigQueryException | InterruptedException e) {
            System.out.println("Query not performed \n" + e);

        }
        return "some Error occured with the query";
    }


    public String explainForecast() throws IOException {

        String query = "SELECT * FROM ML.EXPLAIN_FORECAST(MODEL bqml_tutorials.nyc_citibike_arima_model, STRUCT(365 AS horizon, 0.9 AS confidence_level))";

        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(query)
                        .setUseLegacySql(false).build();

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bqClientConfig.bigQueryInit.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        try {
            queryJob = queryJob.waitFor();
            // Check for errors
            if (queryJob == null) {
                throw new RuntimeException("Job no longer exists");
            } else if (queryJob.getStatus().getError() != null) {
                // You can also look at queryJob.getStatus().getExecutionErrors() for all
                // errors, not just the latest one.
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }
            // Get the results.
            TableResult result = queryJob.getQueryResults();
            Flux<String> returnValue = new Flux<String>() {
                @Override
                public void subscribe(CoreSubscriber<? super String> coreSubscriber) {

                }
            };
            return result.toString();

        } catch (BigQueryException | InterruptedException e) {
            System.out.println("Query not performed \n" + e);

        }
        return "some Error occured with the query";
    }
}
