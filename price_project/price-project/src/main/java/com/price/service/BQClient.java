package com.price.service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class BQClient {

    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests.
    BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId("dhimahi-dynamic-pricing-demo")
            .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:dhimahi-dynamic-pricing-demo-cb98c398daf8.json"))))
            .build().getService();

    public BQClient() throws IOException {
    }

    public String normalizedDataWithCorrelationCoeff() {
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
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

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
        return "some Error";
    }

    public com.google.api.services.bigquery.model.Model getArimaModel() {
        // TODO: IMPLEMENT GET ARIMA MODEL
        return new com.google.api.services.bigquery.model.Model();
    }
}
