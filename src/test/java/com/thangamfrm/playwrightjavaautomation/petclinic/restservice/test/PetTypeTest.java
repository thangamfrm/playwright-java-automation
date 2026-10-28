package com.thangamfrm.playwrightjavaautomation.petclinic.restservice.test;

import com.thangamfrm.playwrightjavaautomation.test.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class PetTypeTest extends BaseTest {

  private static final Logger LOG = LoggerFactory.getLogger(PetTypeTest.class);

  @BeforeClass
  public void setUp() {
    super.setUp();
    RestAssured.baseURI = getProperty("spring_petclinic_restservice_url");
  }

  @Test
    public void testPetTypeCRUDOperations() {
    String petTypeName = "cat-" + getUniqueId();
    LOG.info(String.format("PetType Name: %s", petTypeName));

    // Verify Create PetType
    JsonPath jsonPathCreatePetType = given()
        .contentType(ContentType.JSON)
        .body("{\"name\":\"" + petTypeName + "\"}")
        .when()
        .urlEncodingEnabled(false)
        .post("/pettypes")
        .then()
        .statusCode(201)
        .extract().jsonPath();

    String petTypeId = jsonPathCreatePetType.getString("id");
    LOG.info(String.format("Pet Type ID: %s", petTypeId));

    // Verify Get PetType
    JsonPath jsonPathGetPetType = given()
        .when()
        .get("/pettypes/" + petTypeId)
        .then()
        .statusCode(200)
        .extract().jsonPath();

    assertEquals(jsonPathGetPetType.getString("name"), petTypeName, "PetTypeName didn't match!");

    // // Verify Update PetType
    petTypeName = petTypeName + "-updated";
    String jsonStr = "{\"id\":" + petTypeId + ", \"name\":\"" + petTypeName + "\"}";
    LOG.info(String.format("PUT Json: %s", jsonStr));

    JsonPath jsonPathPutPetType = given()
        .contentType(ContentType.JSON)
        .body(jsonStr)
        .when()
        .urlEncodingEnabled(false)
        .put("/pettypes/" + petTypeId)
        .then()
        .statusCode(204)
        .extract().jsonPath();

    // Verify Updated PetType - Get again and assert name
    jsonPathGetPetType = given()
        .when()
        .get("/pettypes/" + petTypeId)
        .then()
        .statusCode(200)
        .extract().jsonPath();

    assertEquals(jsonPathGetPetType.getString("name"), petTypeName, "Updated PetTypeName didn't match!");

    // Verify Delete PetType
    given()
        .contentType(ContentType.JSON)
        .when()
        .urlEncodingEnabled(false)
        .delete("/pettypes/" + petTypeId)
        .then()
        .statusCode(204)
        .log().all();

    // Verify PetType Deleted - Read again and ensure the Operation failed
    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/pettypes/" + petTypeId)
        .then()
        .statusCode(404)
        .log().all();
  }

}
