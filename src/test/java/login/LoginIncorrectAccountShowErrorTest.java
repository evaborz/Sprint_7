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

import static org.junit.Assert.assertEquals;

public class LoginIncorrectAccountShowErrorTest {

    private static final String ACCOUNT_ERROR = "Учетная запись не найдена";

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
    @DisplayName("система вернет ошибку, если неправильно указан логин или пароль")
    public void loginIncorrectAccountShowError() {
        steps.create(account);
        CourierAccount wrongAccount = new CourierAccount(faker.funnyName().name(), account.getPassword(),
                account.getFirstName());
        testData.add(wrongAccount);
        ValidatableResponse response = steps.login(wrongAccount);
        assertEquals("Авторизация с неверным логином или паролем должна вернуть ошибку",
                response.extract().body().jsonPath().getString("message"), ACCOUNT_ERROR);
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}
