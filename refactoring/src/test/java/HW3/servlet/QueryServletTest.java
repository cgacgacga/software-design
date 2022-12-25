package HW3.servlet;

import HW3.servlet.tools.DataBaseTestTools;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class QueryServletTest {
    private QueryServlet servlet;
    private StringWriter stringWriter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;

    private final String COMMAND_PARAMETER = "command";

    private final Map<String, Long> EXPECTED_PRODUCTS = Map.of(
        "Jeans", 2000L,
        "T-shirt", 1000L,
        "Dress", 5000L
    );

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
            Arguments.of(
                "max",
                getResponse("<h1>Product with max price: </h1>\nDress\t5000</br>")
            ),
            Arguments.of(
                "min",
                getResponse("<h1>Product with min price: </h1>\nT-shirt\t1000</br>")
            ),
            Arguments.of(
                "sum",
                getResponse("Summary price: \n8000")
            ),
            Arguments.of(
                "count",
                getResponse("Number of products: \n3")
            ),
            Arguments.of(
                "krakazyabra",
                getResponseWithoutHtml("Unknown command: krakazyabra")
            )
        );
    }

    @BeforeEach
    public void setup() throws IOException {
        DataBaseTestTools.createProductTable();
        DataBaseTestTools.insertIntoProduct(EXPECTED_PRODUCTS);
        servlet = new QueryServlet();
        stringWriter = new StringWriter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
    }

    @ParameterizedTest(name = "{0} command")
    @MethodSource("provideArguments")
    public void queryTestMax(String command, String expected) throws IOException {
        when(request.getParameter(COMMAND_PARAMETER)).thenReturn(command);

        servlet.doGet(request, response);

        writer.flush();
        assertThat(stringWriter.toString()).isEqualTo(expected);
    }

    @AfterEach
    public void cleanUp() {
        DataBaseTestTools.deleteFromProduct();
    }

    private static String getResponse(String responseBody) {
        return String.format("<html><body>\n%s\n</body></html>\n", responseBody);
    }

    private static String getResponseWithoutHtml(String responseBody) {
        return String.format("%s\n", responseBody);
    }
}
