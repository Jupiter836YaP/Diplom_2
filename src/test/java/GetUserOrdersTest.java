import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.User;
import steps.OrderSteps;
import steps.UserSteps;
import static constant.ErrorMessage.*;

import static org.hamcrest.Matchers.equalTo;

public class GetUserOrdersTest extends BaseTest {

    Faker faker = new Faker();
    @Test
    @DisplayName("Получение заказов пользователя в авторизованной зоне")
    public void getUserOrdersWithAuth() {
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        accessToken = UserSteps.userCreate(user).extract().path("accessToken");
        UserSteps.userAuth(user);
        ValidatableResponse response = OrderSteps.getUserOrderWithAuth(accessToken);
        response.assertThat().body("success", equalTo(true)).statusCode(200);
    }

    @Test
    @DisplayName("Получение заказов пользователя в неавторизованной зоне")
    public void getUserOrdersWithoutAuth() {
        ValidatableResponse response = OrderSteps.getUserOrderWithoutAuth();
        response.assertThat().body("message", equalTo(MESSAGE_FOR_GET_ORDERS_WITHOUT_AUTH)).statusCode(401);
    }
}
