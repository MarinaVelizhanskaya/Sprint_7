import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.example.OrderClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class CreateOrderTest {
    private OrderClient orderClient;
    private final String[] colors;

    public CreateOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void OrderCreated() {
        String firstName = "firstName";
        String lastName = "lastName";
        String address = "address";
        String metroStation = "metroStation";
        String phone = "phone";
        int rentTime = 2;
        String deliveryDate = "2020-06-06";
        String comment = "comment";
        Order order = new  Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
        ValidatableResponse response = orderClient.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals(201, statusCode);
        response.assertThat().body("track", notNullValue());

    }


}
