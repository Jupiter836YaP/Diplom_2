import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.Order;
import pojo.User;
import steps.OrderSteps;
import steps.UserSteps;

import java.util.ArrayList;
import java.util.List;
import static constant.ErrorMessage.*;
import static constant.Data.*;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {
    Faker faker = new Faker();

    @Test
    @DisplayName("Создание заказа в авторизованной зоне с передачей ингредиентов")
    public void createOrderWithAuthAndIngredients() {
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        accessToken = UserSteps.userCreate(user).extract().path("accessToken");
        UserSteps.userAuth(user);
        List<String> ingredients = OrderSteps.getIngredient().extract().path("data._id");
        Order order = new Order(ingredients.subList(1,3));
        ValidatableResponse response = OrderSteps.createOrderWithAuth(order, accessToken);
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа в авторизованной зоне без передачи ингредиентов")
    public void createOrderWithAuthAndWithoutIngredients() {
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        accessToken = UserSteps.userCreate(user).extract().path("accessToken");
        UserSteps.userAuth(user);
        Order order = new Order();
        ValidatableResponse response = OrderSteps.createOrderWithAuth(order, accessToken);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_CREATE_ORDER_WITHOUT_INGREDIENTS)).and().statusCode(400);
    }

    @Test
    @DisplayName("Создание заказа в неавторизованной зоне с передачей ингредиентов")
    public void createOrderWithoutAuth() {
        List<String> ingredients = OrderSteps.getIngredient().extract().path("data._id");
        Order order = new Order(ingredients.subList(0,4));
        ValidatableResponse response = OrderSteps.createOrderWithoutAuth(order);
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа в неавторизованной зоне без передачи ингредиентов")
    public void createOrderWithoutAuthAndIngredients() {
        Order order = new Order();
        ValidatableResponse response = OrderSteps.createOrderWithoutAuth(order);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_CREATE_ORDER_WITHOUT_INGREDIENTS)).and().statusCode(400);
    }

    @Test
    @DisplayName("Создание заказа с невалидным хешом")
    public void createOrderWithInvalidHash() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(INVALID_HASH);
        Order order = new Order(ingredients);
        ValidatableResponse response = OrderSteps.createOrderWithoutAuth(order);
        response.assertThat().statusCode(500);
    }

}
