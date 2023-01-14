import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.CourierCredentialsGenerator;



import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;


public class LoginCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private CourierCredentials courierCredentials;
    private CourierCredentials courierCredentialsWithoutPassword;
    private CourierCredentials courierCredentialsWithoutLogin;
    private CourierCredentials nonexistentCourier;
    private int id;

    @Before
    public void setUp(){
        courier = CourierGenerator.getRandom();
        nonexistentCourier = CourierCredentialsGenerator.getRandom();
        courierClient = new CourierClient();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        courierCredentialsWithoutPassword = new CourierCredentials(courier.getLogin());
        courierCredentialsWithoutLogin = new CourierCredentials(courier.getPassword());
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }


    @Test
    public void courierCanBeLogin() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        id = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(200, statusCode);
        loginResponse.assertThat().body("id", notNullValue());
    }

    @Test
    public  void courierLoginWithoutPassword() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentialsWithoutPassword);
        id = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(400, statusCode);
        String message = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", message);

    }

    @Test
    public  void courierLoginWithoutLogin() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentialsWithoutLogin);
        id = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(400, statusCode);
        String message = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", message);

    }
    @Test
    public void nonexistentCourierLogin() {
        ValidatableResponse loginResponse = courierClient.login(nonexistentCourier);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(404, statusCode);
        String message = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }



}
