package com.example.qaguru_16_restassured;

import com.example.qaguru_16_restassured.models.CreateUser;
import com.example.qaguru_16_restassured.models.UserData;
import com.example.qaguru_16_restassured.specs.Specs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.qaguru_16_restassured.specs.Specs.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTests {

    @Test
    @DisplayName("Проверка пользователья по id")
    void checkUserNameById() {
       UserData data = request.when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpec)
                .log().body().extract().as(UserData.class);
        assertEquals(2,data.getUser().getId());
    }
    @Test
    @DisplayName("Проверка получения списка пользователей")
    void checkListOfUsers(){
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .log().body()
                .body("data.findAll{it.id}.last_name.flatten()",hasItem("Howell"));
    }

    @Test
    @DisplayName("Проверка что пользователь не найден")
    void checkUserNotFound() {
        request.when()
                .get("https://reqres.in/api/users/23")
                .then()
                .spec(Specs.responseSpecError404)
                .log().all();
    }

    @Test
    @DisplayName("Проверка создания пользователя")
    void checkCreateUser() {
        CreateUser body = new CreateUser();
        body.setName("Smith");
        body.setJob("agent");

        given()
                .spec(request)
                .body(body)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .spec(responseSpec201)
                .body("name", is("Smith"))
                .body("job", is("agent"))
                .extract().as(UserData.class);
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя")
    void checkUpdateUser() {
        CreateUser body = new CreateUser();
        body.setName("Erlond");
        body.setJob("rivindel president");

        given()
                .spec(request)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .spec(responseSpec)
                .body("name", is("Erlond"))
                .body("job", is("rivindel president"))
                .extract().as(UserData.class);
    }

    @Test
    @DisplayName("Проверка удадения пользователя")
    void checkDeleteUser() {
        given()
                .spec(request).when()
                .delete("/users/2")
                .then()
                .spec(responseSpec204)
                .log().all();
    }

}
