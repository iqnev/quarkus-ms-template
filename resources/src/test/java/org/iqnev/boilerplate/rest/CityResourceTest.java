package org.iqnev.boilerplate.rest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.iqnev.boilerplate.BaseTest;
import org.iqnev.boilerplate.dtos.CityDto;
import org.iqnev.boilerplate.dtos.StateDto;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class CityResourceTest extends BaseTest {

  @BeforeEach
  @Override
  public void setup() {
    super.setup();
  }

  @Test
  void count_shouldReturnTotalCityCount() {

    ValidatableResponse actualResponse = given().when().get("/cities/count").then();

    actualResponse.statusCode(StatusCode.OK);

    String result = actualResponse.extract().asString();

    assertThat(Long.parseLong(result)).isEqualTo(0);

    executeScript(TEST_INSERT_RECORDS_SCRIPT);

    actualResponse = given().when().get("/cities/count").then();

    actualResponse.statusCode(StatusCode.OK);

    result = actualResponse.extract().asString();

    assertThat(Long.parseLong(result)).isEqualTo(3);
  }

  @Test
  @SneakyThrows
  void createCity_shouldCreateCityWithValidRequest() {

    executeScript(TEST_INSERT_RECORDS_SCRIPT);

    final CityDto expectedCity =
        CityDto.builder()
            .name("Test City")
            .state(
                StateDto.builder()
                    .id("4C1BQ4ESCRVBVZQYVVADQR8L")
                    .region("West")
                    .name("California")
                    .build())
            .build();

    final ValidatableResponse actualResponse =
        given().contentType(ContentType.JSON).body(expectedCity).when().post("/cities").then();

    actualResponse.statusCode(StatusCode.OK);

     final CityDto actualCity =
        objectMapper.readValue(actualResponse.extract().asString(), CityDto.class);

    assertThat(actualCity).isNotNull();

    assertThat(actualCity)
        .usingRecursiveComparison()
        .ignoringFields(fetchFieldNamesToIgnore())
        .isEqualTo(expectedCity);
  }

  private String[] fetchFieldNamesToIgnore() {
    return new String[] {"id", "state.id"};
  }
}
