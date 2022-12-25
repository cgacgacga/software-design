package HW3.servlet;

import HW3.servlet.tools.DataBaseTestTools;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AddProductServletTest {
    private AddProductServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;

    private final String NAME_PARAMETER = "name";
    private final String PRICE_PARAMETER = "price";
    private final String OK_STATUS = "OK";

    @Before
    public void setup() throws IOException {
        DataBaseTestTools.createProductTable();
        servlet = new AddProductServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);

        when(request.getParameter(NAME_PARAMETER)).thenReturn("Jeans");
        when(request.getParameter(PRICE_PARAMETER)).thenReturn("2000");

        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void simpleAddProductTest() throws IOException, SQLException {
        servlet.doGet(request, response);

        List<List<String>> resultSet = DataBaseTestTools.selectFromProduct(
            NAME_PARAMETER,
            PRICE_PARAMETER
        );

        assertThat(resultSet.size()).isEqualTo(1);
        assertThat(resultSet.get(0)).containsExactly("Jeans", "2000");
        verify(writer, times(1)).println(eq(OK_STATUS));
    }

    @After
    public void cleanUp() {
        DataBaseTestTools.deleteFromProduct();
    }
}
