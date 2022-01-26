package com.price;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.*;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class GetModel {

    public static void main(String[] args) {
        String datasetName = "bqml_tutorials";
        String modelName = "nyc_citibike_arima_model_group";
        getModel(datasetName, modelName);
        listModels(datasetName);
    }

    public static void getModel(String datasetName, String modelName) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigQueryInit = BigQueryOptions.newBuilder()
                    .setProjectId("dhimahi-dynamic-pricing-demo")
                    .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:dhimahi-dynamic-pricing-demo-cb98c398daf8.json"))))
                    .build()
                    .getService();

            ModelId modelId = ModelId.of(datasetName, modelName);
            Model model = bigQueryInit.getModel(modelId);
            System.out.println("Model: " + model.getDescription());

            System.out.println("Successfully retrieved model");
        } catch (BigQueryException e) {
            System.out.println("Cannot retrieve model \n" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listModels(String datasetName) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigQueryInit = BigQueryOptions.newBuilder()
                    .setProjectId("dhimahi-dynamic-pricing-demo")
                    .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(ResourceUtils.getFile("classpath:dhimahi-dynamic-pricing-demo-cb98c398daf8.json"))))
                    .build()
                    .getService();

            Page<Model> models = bigQueryInit.listModels(datasetName, BigQuery.ModelListOption.pageSize(100));
            if (models == null) {
                System.out.println("Dataset does not contain any models.");
                return;
            }
            models
                    .iterateAll()
                    .forEach(model -> System.out.printf("Success! Model ID: %s \n", model.getModelId()));
        } catch (BigQueryException e) {
            System.out.println("Models not listed in dataset due to error: \n" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
