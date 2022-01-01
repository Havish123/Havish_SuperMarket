package com.havish.modal;

public class Stock_Purchase {
    private int stock_id;
    private int purchase_id;
    private int dealer_id;
    private int quantity;
    private float price;
    private float total_amount;

    public Stock_Purchase() {
    }

    public Stock_Purchase(int stock_id, int purchase_id, int quantity, float price, float total_amount) {
        this.stock_id = stock_id;
        this.purchase_id = purchase_id;
        this.quantity = quantity;
        this.price = price;
        this.total_amount = total_amount;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }
}
