package com.havish.modal;

public class Customer_Purchase {

    private int bill_id;
    private int cust_id;
    private int stock_id;
    private String stock_name;
    private int quantity;
    private float total_amount;

    public Customer_Purchase() {
    }

    public Customer_Purchase(int cust_id, int stock_id, int quantity, float total_amount, String date) {
        this.cust_id = cust_id;
        this.stock_id = stock_id;
        this.quantity = quantity;
        this.total_amount = total_amount;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }


}
