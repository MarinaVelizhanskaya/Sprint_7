import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private CourierCredentials courierCredentials;
    private Courier sameCourier;
    private Courier courierWithoutPassword;
    private Courier courierWithoutLogin;
    private int id;


    @Before
    public void setUp(){
        courier = CourierGenerator.getRandom();
        courierWithoutPassword = CourierGenerator.getRandomWithoutPassword();
        courierWithoutLogin = CourierGenerator.getRandomWithoutLogin();
        sameCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        courierClient = new CourierClient();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    public void courierCanBeCreated() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(201, statusCode);
        boolean messageResponse = response.extract().path("ok");
        assertTrue(messageResponse);

    }

    @Test
    public void sameCouriersCanNotBeCreated() {
        courierClient.create(courier);
        ValidatableResponse RepeatResponse = courierClient.create(sameCourier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        id = loginResponse.extract().path("id");
        int statusCode = RepeatResponse.extract().statusCode();
        assertEquals(409, statusCode);
        String messageResponse = RepeatResponse.extract().path("message");
        assertEquals("Этот логин уже используется", messageResponse);
    }

    @Test
    public void generateCourierWithoutPassword(){
        ValidatableResponse response = courierClient.create(courierWithoutPassword);
        int statusCode = response.extract().statusCode();
        assertEquals(400, statusCode);
        String messageResponse = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", messageResponse);

    }
    @Test
    public void generateCourierWithoutLogin(){
        ValidatableResponse response = courierClient.create(courierWithoutLogin);
        int statusCode = response.extract().statusCode();
        assertEquals(400, statusCode);
        String messageResponse = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", messageResponse);

    }




}
