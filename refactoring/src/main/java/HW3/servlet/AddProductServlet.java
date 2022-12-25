package HW3.servlet;

import HW3.dto.Product;
import HW3.repository.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        Product product = new Product(name, price);

        productRepository.add(product);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
