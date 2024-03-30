import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.User;
import steps.UserSteps;

import static constant.ErrorMessage.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {
    Faker faker = new Faker();
    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUniqueUserTest() {
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        ValidatableResponse response = UserSteps.userCreate(user);
        accessToken = response.extract().path("accessToken");
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    public void createExistUserTest() {
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        accessToken = UserSteps.userCreate(user).extract().path("accessToken");
        ValidatableResponse response = UserSteps.userCreate(user);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_CREATE_EXIST_USER)).and().statusCode(403);
    }
}
