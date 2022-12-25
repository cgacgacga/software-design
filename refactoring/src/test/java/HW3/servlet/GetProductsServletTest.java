package HW3.servlet;

import HW3.servlet.tools.DataBaseTestTools;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GetProductsServletTest {
    private GetProductsServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;

    private final Map<String, Integer> EXPECTED_PRODUCTS = Map.of(
        "Jeans", 2000,
        "T-shirt", 1000,
        "Dress", 5000
    );

    @Before
    public void setup() throws IOException {
        DataBaseTestTools.createProductTable();
        DataBaseTestTools.insertIntoProduct(EXPECTED_PRODUCTS);
        servlet = new GetProductsServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void simpleGetProductsTest() throws IOException {
        servlet.doGet(request, response);

        for (var entry : EXPECTED_PRODUCTS.entrySet()) {
            String name = entry.getKey();
            Integer price = entry.getValue();

            verify(writer, times(1))
                .println(eq(name + "\t" + price + "</br>"));
        }
    }

    @After
    public void cleanUp() {
        DataBaseTestTools.deleteFromProduct();
    }
}
