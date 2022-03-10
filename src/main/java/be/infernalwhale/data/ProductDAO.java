package be.infernalwhale.data;

import be.infernalwhale.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO() throws SQLException {
        this.connection = ConnectionManager.getConnection();
    }

    public void saveProducts(List<Product> products) throws SQLException {
        for (Product p : products) {
            this.saveProduct(p);
        }
    }

    public void saveProduct(Product product) throws SQLException {
        String sql = "INSERT INTO order_products (order_id, product_name, amount, price_per_unit) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(sql);

        statement.setInt(1, product.getOrderId());
        statement.setString(2, product.getProductName());
        statement.setInt(3, product.getAmount());
        statement.setBigDecimal(4, product.getPricePerUnit());

        statement.executeUpdate();
    }

    public List<Product> getProductForOrderID(int id) throws SQLException {
        String sql = "SELECT * FROM order_products WHERE order_id = " + id;
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);

        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setOrderId(rs.getInt("order_id"));
            p.setProductName(rs.getString("product_name"));
            p.setAmount(rs.getInt("amount"));
            p.setPricePerUnit(rs.getBigDecimal("price_per_unit"));

            products.add(p);
        }

        return products;
    }

    public void deleteProductsByOrderID(int orderId) throws SQLException {
        String sql = "DELETE FROM order_products WHERE order_id = " + orderId;
        Statement statement = connection.createStatement();

        statement.executeUpdate(sql);
    }
}
