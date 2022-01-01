package com.havish.modal;
public class Sales {
    private int quantity;
    private int stock_id;
    private String stock_name;
    private float amount;

    public void setStock_name(String product_name) {
        this.stock_name = product_name;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setProduct_id(int product_id) {
        this.stock_id = product_id;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return stock_id;
    }

    public int getQuantity() {
        return quantity;
    }

}
