package be.infernalwhale;

import be.infernalwhale.data.ConnectionManager;
import be.infernalwhale.data.OrderDAO;
import be.infernalwhale.model.Order;
import be.infernalwhale.service.OrderService;

import java.sql.*;
import java.time.LocalDate;

public class TestMain {
    public static void main(String[] args) {
        Order order = new Order();
        order.setOrderNumber("OrderNumber001");
        order.setOrderClient("Test client");
        order.setOrderAddress("Test straat 42");
        order.setOrderPostalCode(4242);
        order.setOrderCity("Test City");
        order.setVatFree(true);
        order.setSend(false);
        order.setOrderDate(Date.valueOf(LocalDate.now()));

        OrderService service = new OrderService();

        try {
            service.addOrder(order);
            System.out.println("Order saved to database succesfully...");
        } catch (SQLException e) {
            System.out.println("Well... shit went sideways....");
            System.out.println("Reason: " + e.getMessage());
        }

    }
}
