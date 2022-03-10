package be.infernalwhale;

import be.infernalwhale.model.Order;
import be.infernalwhale.model.Product;
import be.infernalwhale.service.OrderService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
        order.setProducts(List.of(new Product()));

        try {
            OrderService service = new OrderService();
            service.addOrder(order, List.of(new Product())); // TODO: Replace Empty List with Actual List
            System.out.println("Order saved to database succesfully...");

            Order orderFromDB = service.getOrderById(1);
            System.out.println(orderFromDB.toString());
            orderFromDB.getProducts().forEach(System.out::println);

            List<Order> notSentOrders = service.getNotSentOrders();
            notSentOrders.forEach(System.out::println);

            Order beforeUpdate = service.getOrderById(1);
            System.out.println(beforeUpdate);
            service.sendOrder(1);
            Order afterUpdate = service.getOrderById(1);
            System.out.println(afterUpdate);

            service.deleteOrderById(1);

        } catch (SQLException e) {
            System.out.println("Well... shit went sideways....");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}
