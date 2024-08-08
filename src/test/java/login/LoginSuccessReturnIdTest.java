package login;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.Steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginSuccessReturnIdTest {

    private final Faker faker = new Faker(new Locale("en"));
    private final Steps steps = new Steps();
    private CourierAccount account;
    private List<CourierAccount> testData;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        account = new CourierAccount(faker.funnyName().name(), faker.internet().password(), faker.name().firstName());
        testData.add(account);
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    public void loginSuccessReturnId() {
        steps.create(account);
        ValidatableResponse response = steps.login(account);
        assertThat("Успешный запрос возвращает \"id\": int", response.extract().body().jsonPath().
                getInt("id"), notNullValue());
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}
