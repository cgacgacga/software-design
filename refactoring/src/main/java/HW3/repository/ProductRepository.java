package HW3.repository;

import HW3.dto.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final String CONNECTION = "jdbc:sqlite:test.db";
    private final String NAME_PARAMETER = "name";
    private final String PRICE_PARAMETER = "price";

    public void add(Product product) {
        String script =
            """
            INSERT INTO PRODUCT (NAME, PRICE)
            VALUES
            """ + " " + getValuesForInsertion(product);

        executeUpdate(script);
    }

    public List<Product> getAll() {
        return executeQuery("SELECT NAME, PRICE FROM PRODUCT");
    }

    public Product getProductWithMaxPrice() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC, ID LIMIT 1")
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Product getProductWithMinPrice() {
        return executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE, ID LIMIT 1")
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Integer getQuantity() {
        return getAll().size();
    }

    public Long getProductPricesSum() {
        return getAll().stream()
            .mapToLong(Product::getPrice)
            .sum();
    }

    private void executeUpdate(String script) {
        try (Connection c = DriverManager.getConnection(CONNECTION)) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(script);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> executeQuery(String script) {
        try (Connection c = DriverManager.getConnection(CONNECTION)) {
            try (Statement statement = c.createStatement()) {
                ResultSet resultSet = statement.executeQuery(script);
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    products.add(
                        new Product(
                            resultSet.getString(NAME_PARAMETER),
                            Long.parseLong(resultSet.getString(PRICE_PARAMETER))
                        )
                    );
                }

                resultSet.close();
                statement.close();

                return products;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getValuesForInsertion(Product product) {
        return String.format("('%s', %d)", product.getName(), product.getPrice());
    }
}
