package com.havish.modal;

public class Stock_Purchase_Bill {
    private int purchase_id;
    private int dealer_id;
    private String purchase_date;
    private float total_amount;

    public Stock_Purchase_Bill() {
    }

    public Stock_Purchase_Bill(int dealer_id, String purchase_date, float total_amount) {
        this.dealer_id = dealer_id;
        this.purchase_date = purchase_date;
        this.total_amount = total_amount;
    }

    public Stock_Purchase_Bill(int purchase_id, int dealer_id, String purchase_date, float total_amount) {
        this.purchase_id = purchase_id;
        this.dealer_id = dealer_id;
        this.purchase_date = purchase_date;
        this.total_amount = total_amount;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }
}
