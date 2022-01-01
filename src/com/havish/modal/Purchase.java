package com.havish.modal;

public class Purchase {
    private int stock_id;
    private int quantity;
    private float price;
    private float tot_amount;



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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTot_amount() {
        return tot_amount;
    }

    public void setTot_amount(float tot_amount) {
        this.tot_amount = tot_amount;
    }
}
