package HW3.util;

import HW3.dto.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponsePrinter {
    public static void printMaxPriceResult(
        HttpServletResponse response,
        Product product
    ) throws IOException {
        printBody(
            response,
            "<h1>Product with max price: </h1>",
            product.getName() + "\t" + product.getPrice() + "</br>"
        );
    }

    public static void printMinPriceResult(
        HttpServletResponse response,
        Product product
    ) throws IOException {
        printBody(
            response,
            "<h1>Product with min price: </h1>",
            product.getName() + "\t" + product.getPrice() + "</br>"
        );
    }

    public static void printCountResult(
        HttpServletResponse response,
        Integer count
    ) throws IOException {
        printBody(
            response,
            "Number of products: ",
            count.toString()
        );
    }

    public static void printSumResult(
        HttpServletResponse response,
        Long sum
    ) throws IOException {
        printBody(
            response,
            "Summary price: ",
            sum.toString()
        );
    }

    public static void printUnknownCommand(
        HttpServletResponse response,
        String command
    ) throws IOException {
        response.getWriter().println("Unknown command: " + command);
    }

    private static void printBody(
        HttpServletResponse response,
        String title,
        String value
    ) throws IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println(title);
        response.getWriter().println(value);
        response.getWriter().println("</body></html>");

        setParams(response);
    }

    private static void setParams(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
