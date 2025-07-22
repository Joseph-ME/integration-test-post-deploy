package com.mycompany.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.http.ContentType;

import java.util.*;


import static net.serenitybdd.rest.SerenityRest.then;
import static net.serenitybdd.rest.RestRequests.given;
import static net.serenitybdd.rest.RestRequests.when;
import static org.hamcrest.Matchers.*;

public class OwnersDefinitions {
    @Then("el cuerpo de la respuesta debe ser una lista de propietarios valida")
    public void el_cuerpo_de_la_respuesta_debe_ser_una_lista_de_propietarios_valida() {
        then().body("$", instanceOf(List.class));
        then().body("$", not(empty()));

        then().body("firstName", everyItem(notNullValue()));
        then().body("lastName", everyItem(notNullValue()));
        then().body("address", everyItem(notNullValue()));
        then().body("city", everyItem(notNullValue()));
        then().body("telephone", everyItem(notNullValue()));
        then().body("id", everyItem(notNullValue()));
        then().body("pets", everyItem(instanceOf(List.class)));

        then().body("pets.name", everyItem(everyItem(notNullValue())));
        then().body("pets.birthDate", everyItem(everyItem(notNullValue())));
        then().body("pets.type", everyItem(everyItem(notNullValue())));
        then().body("pets.type.name", everyItem(everyItem(notNullValue())));
        then().body("pets.type.id", everyItem(everyItem(notNullValue())));
        then().body("pets.id", everyItem(everyItem(notNullValue())));
        then().body("pets.ownerId", everyItem(everyItem(notNullValue())));

        then().body("pets.visits", everyItem(everyItem(instanceOf(List.class))));
        then().body("pets.visits.date", everyItem(everyItem(everyItem(notNullValue()))));
        then().body("pets.visits.description", everyItem(everyItem(everyItem(notNullValue()))));
        then().body("pets.visits.id", everyItem(everyItem(everyItem(notNullValue()))));

        then().body("firstName", everyItem(instanceOf(String.class)));
        then().body("lastName", everyItem(instanceOf(String.class)));
        then().body("address", everyItem(instanceOf(String.class)));
        then().body("city", everyItem(instanceOf(String.class)));
        then().body("telephone", everyItem(instanceOf(String.class)));
        then().body("id", everyItem(instanceOf(Integer.class)));
        then().body("pets", everyItem(instanceOf(List.class)));

        then().body("firstName", everyItem(allOf(notNullValue(), instanceOf(String.class))));
        then().body("lastName", everyItem(allOf(notNullValue(), instanceOf(String.class))));
        then().body("address", everyItem(allOf(notNullValue(), instanceOf(String.class))));
        then().body("city", everyItem(allOf(notNullValue(), instanceOf(String.class))));
        then().body("telephone", everyItem(allOf(notNullValue(), instanceOf(String.class))));
        then().body("id", everyItem(allOf(notNullValue(), instanceOf(Integer.class))));
        then().body("pets", everyItem(instanceOf(List.class)));
    }

    @Then("el cliente comprueba el valor del campo {string} es igual a {string}")
    public void el_cliente_comprueba_el_valor_del_campo_es_igual_a(String campo, String valor) {
        then().body(campo, is(valor));
    }

    @Then("el cliente comprueba el valor del campo {string} es igual a {int}")
    public void el_cliente_comprueba_el_valor_del_campo_es_igual_a(String campo, Integer valor) {
        then().body(campo, is(valor));
    }

    @Then("el cuerpo de la respuesta contiene las propiedades en una sola columna")
    public void el_cuerpo_de_la_respuesta_contiene_las_propiedades_en_una_sola_columna(io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        rows.stream()
                .flatMap(List::stream)
                .forEach(campo -> {
                    then().body("$", hasKey(campo));
                    then().body(campo, notNullValue());
                });
    }

    @Then("el cuerpo de la respuesta contiene las propiedades en una sola fila")
    public void el_cuerpo_de_la_respuesta_contiene_las_propiedades_en_una_sola_fila(io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        rows.stream().findFirst().ifPresent(firstRow -> {
            firstRow.forEach(campo -> {
                then().body("$", hasKey(campo));
                then().body(campo, notNullValue());
            });
        });
    }

    @Then("el cuerpo de la respuesta contiene con las siguientes propiedades y valores")
    public void el_cuerpo_de_la_respuesta_contiene_con_las_siguientes_propiedades_y_valores(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach((campo, valor) -> {
            then().body(campo, is(parseValue(valor)));
        });
    }

    private Object parseValue(String valor) {
        if (valor.startsWith("$str{") && valor.endsWith("}")) {
            return valor.substring(5, valor.length() - 1);
        } else if (valor.matches("-?\\d+")) {
            return Integer.parseInt(valor);
        } else if (valor.matches("-?\\d+\\.\\d+")) {
            return Double.parseDouble(valor);
        } else {
            return valor;
        }
    }

    @Given("el cliente tiene los datos de un nuevo propietario")
    public void el_cliente_tiene_los_datos_de_un_nuevo_propietario(String docString) {
        given()
                .contentType(ContentType.JSON)
                .body(docString);
    }

    @When("el cliente realiza una peticion POST a {string} con los datos del nuevo propietario")
    public void el_cliente_realiza_una_peticion_post_a_con_los_datos_del_nuevo_propietario(String path) {
        when()
                .post(path)
                .andReturn();
    }
}
