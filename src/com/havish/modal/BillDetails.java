package com.havish.modal;

public class BillDetails {
    private int bill_id;
    private String bill_date;
    private int cust_id;
    private float tot_amount;
    private int rep_id;
    private int discount;

    public BillDetails() {
    }

    public BillDetails(String bill_date, int cust_id, float tot_amount, int rep_id, int discount) {
        this.bill_date = bill_date;
        this.cust_id = cust_id;
        this.tot_amount = tot_amount;
        this.rep_id = rep_id;
        this.discount = discount;
    }

    public BillDetails(int bill_id, String bill_date, int cust_id, float tot_amount, int rep_id, int discount) {
        this.bill_id = bill_id;
        this.bill_date = bill_date;
        this.cust_id = cust_id;
        this.tot_amount = tot_amount;
        this.rep_id = rep_id;
        this.discount = discount;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public float getTot_amount() {
        return tot_amount;
    }

    public void setTot_amount(float tot_amount) {
        this.tot_amount = tot_amount;
    }

    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
