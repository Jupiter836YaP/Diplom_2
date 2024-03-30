import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.User;
import steps.UserSteps;

import static constant.Data.*;
import static constant.ErrorMessage.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends BaseTest {
    Faker faker = new Faker();
    @Test
    @DisplayName("Авторизация под существующим пользователем")
    public void loginExistUserTest() {
        User user = new User()
                .setEmail(faker.internet().emailAddress())
                .setName(faker.name().firstName())
                .setPassword(faker.internet().password());

        accessToken = UserSteps.userCreate(user).extract().path("accessToken");
        ValidatableResponse response = UserSteps.userAuth(user);
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Авторизация с некоррекным email")
    public void loginUserWithIncorrectPasswordTest() {
        User userCreate = new User(EMAIL,PASSWORD,NAME);
        User userAuth = new User()
                .setEmail(INCORRECT_EMAIL)
                .setPassword(PASSWORD);
        accessToken = UserSteps.userCreate(userCreate).extract().path("accessToken");
        ValidatableResponse response = UserSteps.userAuth(userAuth);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_AUTH_INCORRECT_DATA)).and().statusCode(401);
    }

    @Test
    @DisplayName("Авторизация с некоррекным паролем")
    public void loginUserWithIncorrectEmailTest() {
        User userCreate = new User(EMAIL,PASSWORD,NAME);
        User userAuth = new User()
                .setEmail(EMAIL)
                .setPassword(INCORRECT_PASSWORD);
        accessToken = UserSteps.userCreate(userCreate).extract().path("accessToken");
        ValidatableResponse response = UserSteps.userAuth(userAuth);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_AUTH_INCORRECT_DATA)).and().statusCode(401);
    }
}
