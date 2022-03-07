package be.infernalwhale.model;

import java.sql.Date;

public class Order {
    private int id;
    private String orderNumber;
    private String orderClient;
    private String orderAddress;
    private int orderPostalCode;
    private String orderCity;
    private boolean vatFree;
    private boolean send;
    private Date orderDate;

    public int getId() {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Order setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getOrderClient() {
        return orderClient;
    }

    public Order setOrderClient(String orderClient) {
        this.orderClient = orderClient;
        return this;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public Order setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
        return this;
    }

    public int getOrderPostalCode() {
        return orderPostalCode;
    }

    public Order setOrderPostalCode(int orderPostalCode) {
        this.orderPostalCode = orderPostalCode;
        return this;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public Order setOrderCity(String orderCity) {
        this.orderCity = orderCity;
        return this;
    }

    public boolean isVatFree() {
        return vatFree;
    }

    public Order setVatFree(boolean vatFree) {
        this.vatFree = vatFree;
        return this;
    }

    public boolean isSend() {
        return send;
    }

    public Order setSend(boolean send) {
        this.send = send;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderClient='" + orderClient + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderPostalCode=" + orderPostalCode +
                ", orderCity='" + orderCity + '\'' +
                ", vatFree=" + vatFree +
                ", send=" + send +
                ", orderDate=" + orderDate +
                '}';
    }
}
