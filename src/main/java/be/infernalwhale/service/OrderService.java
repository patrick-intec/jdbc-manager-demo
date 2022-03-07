package be.infernalwhale.service;

import be.infernalwhale.data.OrderDAO;
import be.infernalwhale.model.Order;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();

    public void addOrder(Order order) throws SQLException {
        int orderCount = 0;
        do {
            String orderNumber = calculateOrderNumber();
            // check if number exists in db.... (if it does, regenerate new number)
            orderCount = orderDAO.countOrdersByOrderNumber(orderNumber);
        } while (orderCount > 0);

        // set number in order (as a property)

        // save order
        orderDAO.createOrder(order);
    }

    private String calculateOrderNumber() {
        String lastOrderNumber = orderDAO.getLastOrderNumber();

        if ("".equals(lastOrderNumber)) {
            return this.generateOrderNumberAtStartOfMonth();
        }

        int monthLastOrder = Integer.parseInt(lastOrderNumber.substring(8, 10));



        // Get last orderNumber from DB...
        // calculate new orderNumber (if not new month)

        // in case of no orders: generate orderNumber



    }

    private String generateOrderNumberAtStartOfMonth() {
        StringBuilder result = new StringBuilder("ORD-");
        result.append(LocalDate.now().getYear());
        result.append((LocalDate.now().getMonthValue() < 10) ? "0" + LocalDate.now().getMonthValue() : LocalDate.now().getMonthValue());
        result.append("0001");

        return result.toString();
    }
}
