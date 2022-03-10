package be.infernalwhale.data;

import be.infernalwhale.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;

    public OrderDAO() throws SQLException {
        connection = ConnectionManager.getConnection();
    }

    public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO order_table (order_number, order_client, order_delivery_address," +
                " order_delivery_postalcode, order_delivery_city, is_vat_free, is_send, order_date)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, order.getOrderNumber());
        statement.setString(2, order.getOrderClient());
        statement.setString(3, order.getOrderAddress());
        statement.setInt(4, order.getOrderPostalCode());
        statement.setString(5, order.getOrderCity());
        statement.setBoolean(6, order.isVatFree());
        statement.setBoolean(7, order.isSend());
        statement.setDate(8, order.getOrderDate());

        int result = statement.executeUpdate();

        return getLastOrderId();
    }

    public int countOrdersByOrderNumber(String orderNumber) throws SQLException {
        String sql = "SELECT COUNT(*) AS CNT FROM order_table WHERE order_number LIKE ?";
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, orderNumber);

        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt("CNT");
    }

    public String getLastOrderNumber() throws SQLException {
        String sql = "SELECT MAX(order_number) AS MX FROM order_table;";
        Statement statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) return rs.getString("MX");
        return "";
    }

    private int getLastOrderId() throws SQLException {
        String sql = "SELECT MAX(id) AS MX FROM order_table";
        Statement statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        rs.next();
        return rs.getInt("MX");
    }

    public Order getOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM order_table WHERE id = " + id;
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);

        List<Order> orders = parseOrderResultSet(rs);
//        if (orders.size() > 1) throw new NonUniqueResultException("Found more than 1 order with same id: " + id);

        return orders.get(0);
    }

    public List<Order> getNotSentOrders() throws SQLException {
        String sql = "SELECT * FROM order_table WHERE is_send = false";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);

        return parseOrderResultSet(rs);
    }

    private List<Order> parseOrderResultSet(ResultSet resultSet) throws SQLException {
        List<Order> orderList = new ArrayList<>();

        while (resultSet.next()) {
            Order result = new Order();
            result.setId(resultSet.getInt("id"));
            result.setOrderNumber(resultSet.getString("order_number"));
            // TODO: Read ALL properties... Too much typing for me...
            result.setOrderDate(resultSet.getDate("order_date"));
            orderList.add(result);
        }

        return orderList;
    }

    public void setOrderSend(int id) throws SQLException {
        String sql = "UPDATE order_table SET is_send = true WHERE id = " + id;
        Statement updateStatement = connection.createStatement();

        updateStatement.executeUpdate(sql);
    }

    public void deleteOrderByID(int id) throws SQLException {
        String sql = "DELETE FROM order_table WHERE id = " + id;
        Statement statement = connection.createStatement();

        statement.executeUpdate(sql);
    }
}
