package HW3.servlet;

import HW3.dto.Product;
import HW3.repository.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServlet extends HttpServlet {
    ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var products = productRepository.getAll();

        response.getWriter().println("<html><body>");

        for (Product product : products) {
            response.getWriter().println(
                product.getName() + "\t" + product.getPrice() + "</br>"
            );
        }

        response.getWriter().println("</body></html>");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
