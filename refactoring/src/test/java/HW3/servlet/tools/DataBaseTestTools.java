package HW3.servlet.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataBaseTestTools {
    private static final String CONNECTION = "jdbc:sqlite:test.db";

    public static void createProductTable() {
        String script =
            """
            CREATE TABLE IF NOT EXISTS PRODUCT
            (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NAME TEXT,
                PRICE INT
            )
            """;

        executeUpdate(script);
    }

    public static void deleteFromProduct() {
        String script =
            """
            DELETE FROM PRODUCT
            """;

        executeUpdate(script);
    }

    public static List<List<String>> selectFromProduct(String ...columnNames) {
        String script =
            """
            SELECT NAME, PRICE FROM PRODUCT
            """;

        return executeQuery(script, columnNames);
    }

    public static void insertIntoProduct(Map<String, Integer> products) {
        String values = products.entrySet().stream()
            .map(entry -> String.format("('%s', %d)", entry.getKey(), entry.getValue()))
            .collect(Collectors.joining(", "));

        String script =
            """
            INSERT INTO PRODUCT (NAME, PRICE)
            VALUES
            """ + " " + values;

        executeUpdate(script);
    }

    private static void executeUpdate(String script) {
        try (Connection c = DriverManager.getConnection(CONNECTION)) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(script);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<List<String>> executeQuery(String script, String ...columnNames) {
        try (Connection c = DriverManager.getConnection(CONNECTION)) {
            try (Statement statement = c.createStatement()) {
                ResultSet resultSet = statement.executeQuery(script);
                List<List<String>> queryResult = new ArrayList<>();
                while (resultSet.next()) {
                    List<String> values = new ArrayList<>();
                    for (String columnName : columnNames) {
                        values.add(resultSet.getString(columnName));
                    }
                    queryResult.add(values);
                }
                return queryResult;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
