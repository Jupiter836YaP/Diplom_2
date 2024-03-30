import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.User;
import steps.UserSteps;

import static constant.ErrorMessage.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserParameterizedTest extends BaseTest {
    private final User user;

    public CreateUserParameterizedTest(User user) {
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        Faker faker = new Faker();
        return new Object[][] {
                {new User().setEmail(faker.internet().emailAddress()).setPassword(faker.internet().password())},
                {new User().setEmail(faker.internet().emailAddress())},
                {new User().setPassword(faker.internet().password())}
        };
    }

    @Test
    @DisplayName("Создание пользвателя без одного из обязательных полей")
    public void createUserWithoutRequiredField() {
        ValidatableResponse response = UserSteps.userCreate(user);
        accessToken = response.extract().path("accessToken");
        response.assertThat().body("message", equalTo(MESSAGE_FOR_CREATE_USER_WITHOUT_FIELD)).and().statusCode(403);
    }
}
