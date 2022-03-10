package be.infernalwhale.service;

import be.infernalwhale.data.OrderDAO;
import be.infernalwhale.data.ProductDAO;
import be.infernalwhale.model.Order;
import be.infernalwhale.model.Product;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderService() throws SQLException {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }

    public void addOrder(Order order, List<Product> products) throws SQLException {
        int orderCount = 0;
        String orderNumber;
        do {
            orderNumber = calculateOrderNumber();
            // check if number exists in db.... (if it does, regenerate new number)
            orderCount = orderDAO.countOrdersByOrderNumber(orderNumber);
        } while (orderCount > 0);

        // set number in order (as a property)
        order.setOrderNumber(orderNumber);

        // save order
        // Get order id
        int orderId = orderDAO.createOrder(order);

        // set orderid in all products
        products.forEach(product -> product.setOrderId(orderId));

        // save products to db
        productDAO.saveProducts(products);
    }

    private String calculateOrderNumber() throws SQLException {
        String lastOrderNumber = orderDAO.getLastOrderNumber();

        if ("".equals(lastOrderNumber)) {
            return this.generateOrderNumberAtStartOfMonth();
        }

        int monthLastOrder = Integer.parseInt(lastOrderNumber.substring(8, 10));
        int currentMonth = LocalDate.now().getMonthValue();

        if (currentMonth != monthLastOrder) return this.generateOrderNumberAtStartOfMonth();

        // calculate new orderNumber (if not new month)
        // From db:     ORD-202203-0001
        // To Generate: ORD-202203-0002
        int currentIndex = Integer.parseInt(lastOrderNumber.substring(11));
        int newIndex = currentIndex++;

        return generateOrderNumberPrefix() + String.format("%04d", newIndex);
    }

    private String generateOrderNumberPrefix() {
        StringBuilder result = new StringBuilder("ORD-");
        result.append(LocalDate.now().getYear());
        result.append((LocalDate.now().getMonthValue() < 10) ? "0" + LocalDate.now().getMonthValue() : LocalDate.now().getMonthValue());
        result.append("-");

        return result.toString();
    }

    private String generateOrderNumberAtStartOfMonth() {
        StringBuilder result = new StringBuilder(generateOrderNumberPrefix());
        result.append("0001");

        return result.toString();
    }

    public Order getOrderById(int id) throws SQLException {
        // Get order
        Order order = orderDAO.getOrderById(id);

        // Get products for order
        List<Product> products = productDAO.getProductForOrderID(id);

        order.setProducts(products);

        return order;
    }

    public List<Order> getNotSentOrders() throws SQLException {
        return orderDAO.getNotSentOrders();
    }

    public void sendOrder(int id) throws SQLException {
        orderDAO.setOrderSend(id);
    }

    public void deleteOrderById(int id) throws SQLException {
        productDAO.deleteProductsByOrderID(id);
        orderDAO.deleteOrderByID(id);
    }
}
