package app;

import app.client.StocksClient;
import app.model.Stock;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import app.client.StocksClientImpl;
import app.users.UserDaoInMemory;

import java.util.List;

public class UserAndExchangeTest {

    @ClassRule
    public static GenericContainer simpleWebServe
            = new FixedHostPortGenericContainer("testExchange")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    private UserDaoInMemory usersDao;
    private StocksClient client;

    @Before
    public void before() {
        client = new StocksClientImpl("http://127.0.0.1", 8080);
        usersDao = new UserDaoInMemory(client);

        int id1 = client.addCompany("Газпром");
        client.addStocks(id1, 100);
        client.changeCost(id1, 100);

        int id2 = client.addCompany("Лукойл");
        client.addStocks(id2, 1);
        client.changeCost(id2, 10);

        int id3 = client.addCompany("Детский Мир");
        client.addStocks(id3, 5);
        client.changeCost(id3, 1000);

        int id4 = client.addCompany("ООО ржав гвоздь");
        client.addStocks(id4, 1000);
        client.changeCost(id4, 5);
    }

    @Test
    @Description("Test add stocks and change cost")
    public void testAddStocksAndChangeCost() {
        int id5 = client.addCompany("company5");
        Assert.assertTrue(client.addStocks(id5, 1));
        Assert.assertTrue(client.changeCost(id5, 1));
    }

    @Test
    @Description("Test add user")
    public void testAddUser() {
        Assert.assertEquals(0, usersDao.addUser("Kostya"));
        Assert.assertEquals(1, usersDao.addUser("Dima"));
    }

    @Test
    @Description("Test buy and sell stock")
    public void testBuyAndSellStock() {
        Assert.assertEquals(0, usersDao.addUser("Kostya"));

        Assert.assertTrue(usersDao.changeMoney(0, 5));
        Assert.assertFalse(usersDao.buyStock(0, 1,  1));
        Assert.assertTrue(usersDao.changeMoney(0, 15));

        Assert.assertFalse(usersDao.buyStock(0, 1,  0));
        Assert.assertTrue(usersDao.buyStock(0, 1,  1));
        Assert.assertEquals(20, usersDao.getUserMoney(0), 0.0001);

        Assert.assertTrue(usersDao.soldStock(0, 1, 1));
        Assert.assertEquals(20, usersDao.getUserMoney(0), 0.0001);

        Assert.assertFalse(usersDao.soldStock(0, 1, 1));
    }

    @Test
    @Description("Test get stocks")
    public void testGetStocks() {
        Assert.assertEquals(0, usersDao.addUser("Юрий"));

        Assert.assertTrue(usersDao.changeMoney(0, 100000));
        Assert.assertTrue(usersDao.buyStock(0, 0,  10));
        Assert.assertTrue(usersDao.buyStock(0, 2,  10));

        Assert.assertEquals(100000, usersDao.getUserMoney(0), 0.1);

        List<Stock> stocks = usersDao.getUserStocks(0);

        Assert.assertEquals(2, stocks.size());
        Assert.assertEquals(0, stocks.get(0).getId());
        Assert.assertEquals(2, stocks.get(1).getId());
        Assert.assertEquals(100, stocks.get(0).getPrice(), 0.1);
        Assert.assertEquals(1000, stocks.get(1).getPrice(), 0.1);
        Assert.assertEquals(10, stocks.get(0).getNumber());
        Assert.assertEquals(10, stocks.get(1).getNumber());
    }

    @Test
    @Description("Test change cost")
    public void testChangeCost() {
        Assert.assertEquals(0, usersDao.addUser("Олег"));
        Assert.assertTrue(usersDao.changeMoney(0, 100000));
        Assert.assertTrue(usersDao.buyStock(0, 3,  1000));

        Assert.assertEquals(100000, usersDao.getUserMoney(0), 0.1);

        Assert.assertTrue(client.changeCost(3, 20));
        Assert.assertEquals(25.0, client.getStockPrice(3), 0.1);
        Assert.assertEquals(120000, usersDao.getUserMoney(0), 0.1);
    }
}
