package HW3.servlet;

import HW3.dto.Product;
import HW3.repository.ProductRepository;
import HW3.util.ResponsePrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryServlet extends HttpServlet {
    ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max" -> {
                Product maxPriceProduct = productRepository.getProductWithMaxPrice();
                ResponsePrinter.printMaxPriceResult(response, maxPriceProduct);
            }
            case "min" -> {
                Product minPriceProduct = productRepository.getProductWithMinPrice();
                ResponsePrinter.printMinPriceResult(response, minPriceProduct);
            }
            case "count" -> {
                Integer count = productRepository.getQuantity();
                ResponsePrinter.printCountResult(response, count);
            }
            case "sum" -> {
                Long sum = productRepository.getProductPricesSum();
                ResponsePrinter.printSumResult(response, sum);
            }
            default -> {
                ResponsePrinter.printUnknownCommand(response, command);
            }
        }
    }

}
