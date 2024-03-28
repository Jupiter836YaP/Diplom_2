package steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import pojo.User;
import static constant.Endpoints.*;

public class UserSteps {
    @Step("Создание пользователя")
    public static ValidatableResponse userCreate(User user){
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();
    }

    @Step("Авторизация пользователя")
    public static ValidatableResponse userAuth(User user){
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(AUTH_USER)
                .then();
    }

    @Step("Изменение данных пользователя  с токеном")
    public static ValidatableResponse updateUserWithToken(User user, String accessToken) {
        return RestAssured.given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .patch(USER)
                .then();
    }

    @Step("Изменение данных пользователя  без токена")
    public static ValidatableResponse updateUserWithoutToken(User user) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .patch(USER)
                .then();
    }


    @Step("Удаление пользователя")
    public static void userDelete(String accessToken){
        RestAssured.given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER)
                .then();
    }
}
