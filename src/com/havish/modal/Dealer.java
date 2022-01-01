package com.havish.modal;

public class Dealer {
    private int dealer_id;
    private String dealer_name;
    private String email;
    private String phone_no;

    public Dealer() {
    }

    public Dealer(int dealer_id, String dealer_name, String email, String phone_no) {
        this.dealer_id = dealer_id;
        this.dealer_name = dealer_name;
        this.email = email;
        this.phone_no = phone_no;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(int dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
