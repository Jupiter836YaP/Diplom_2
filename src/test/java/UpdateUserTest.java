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
public class UpdateUserTest extends BaseTest {

    private final String email = "r.mazaev@gmail.com";
    private final String password = "testPwd";
    private final String name = "Roman";

    private final String emailNew;
    private final String passwordNew;
    private final String nameNew;

    public UpdateUserTest(String emailNew, String passwordNew, String nameNew) {
        this.emailNew = emailNew;
        this.passwordNew = passwordNew;
        this.nameNew = nameNew;
    }


    @Parameterized.Parameters
    public static Object[][] testData()  {
        return new Object[][] {
                {"r.mazaev@gmail.com", "testPwd", "oman"},
                {"r.mazaev@gmail.com", "testPwd1", "Roman"},
                {"mazaev@gmail.com", "testPwd", "Roman"},
        };
    }

    @Test
    @DisplayName("Обновление данных пользователя без авторизации")
    public void updateUserWithoutAuthTest() {
        User user = new User(email, password, name);
        User userNew = new User(emailNew, passwordNew, nameNew);
        accessToken = UserSteps.userCreate(user).extract().path("accessToken");
        ValidatableResponse response = UserSteps.updateUserWithoutToken(userNew);
        response.assertThat().body("message", equalTo(MESSAGE_FOR_UPDATE_WITHOUT_AUTH)).and().statusCode(401);
    }

    @Test
    @DisplayName("Обновление данных пользователя с авторизацией")
    public void updateUserWithAuthTest() {
        User user = new User(email, password, name);
        User userNew = new User(emailNew, passwordNew, nameNew);
        accessToken = UserSteps.userCreate(user).extract().path("accessToken");
        ValidatableResponse response = UserSteps.updateUserWithToken(userNew, accessToken);
        response.assertThat().body("success", equalTo(true)).and().statusCode(200);
    }
}
