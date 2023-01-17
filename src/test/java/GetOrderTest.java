import io.restassured.response.ValidatableResponse;
import org.example.OrderClient;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class GetOrderTest {
    private OrderClient orderClient;


    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void getOrders(){
        ValidatableResponse response = orderClient.get();
        int statusCode = response.extract().statusCode();
        assertEquals(200, statusCode);
        response.assertThat().body("orders", notNullValue());


    }

}
