import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import steps.UserSteps;

import static constant.BaseUrl.BASE_URL;

public class BaseTest {
    protected String accessToken;

    @Before
    public void setUrl() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void clearData() {
        if (accessToken != null) {
            UserSteps.userDelete(accessToken);
        }
    }
}
