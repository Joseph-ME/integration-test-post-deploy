package com.mycompany.steps;

import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonHooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonHooks.class);
    private static final String BASE_URI_PROPERTY = "base.uri";

    @BeforeAll
    public static void setUp() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        RestAssured.baseURI = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty(BASE_URI_PROPERTY);
        LOGGER.info("Base URI configurada: {}", RestAssured.baseURI);
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        LOGGER.info("Número de procesadores disponibles: {}", availableProcessors);
    }
}
