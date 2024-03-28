package steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import pojo.Order;
import static constant.Endpoints.*;

public class OrderSteps {
    @Step("Создание заказа с авторизацией")
    public static ValidatableResponse createOrderWithAuth(Order order, String accessToken) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Создание заказа без авторизации")
    public static ValidatableResponse createOrderWithoutAuth(Order order) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Получение ингредиентов")
    public static ValidatableResponse getIngredient() {
        return RestAssured.given()
                .get(INGREDIENTS)
                .then();
    }


    @Step("Получение заказов пользователя c авторизацией")
    public static ValidatableResponse getUserOrderWithAuth(String accessToken) {
        return RestAssured.given()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS)
                .then();
    }

    @Step("Получение заказов пользователя без авторизации")
    public static ValidatableResponse getUserOrderWithoutAuth() {
        return RestAssured.given()
                .get(ORDERS)
                .then();
    }
}
