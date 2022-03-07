package be.infernalwhale.data;

import be.infernalwhale.model.Order;

import java.sql.*;

public class OrderDAO {
    private Connection connection;

    public OrderDAO() throws SQLException {
        connection = ConnectionManager.getConnection();
    }

    public void createOrder(Order order) throws SQLException {
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
        System.out.println(result);
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
}
