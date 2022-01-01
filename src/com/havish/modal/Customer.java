package com.havish.modal;

public class Customer{
    private int id;
    private String name;
    private String email;
    private String phone_no;
    private double tot_amount;
    public Customer(){

    }
    public Customer(int id,String name,String email,String phone_no){
        this.id=id;
        this.name=name;
        this.email=email;
        this.phone_no=phone_no;
    }
    public Customer(String name,String email,String phone_no){
        this.name=name;
        this.email=email;
        this.phone_no=phone_no;
    }
    public Customer(int id,String name,String email,String phone_no,double tot_amount){
        this.id=id;
        this.name=name;
        this.email=email;
        this.phone_no=phone_no;
        this.tot_amount=tot_amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getTot_amount() {
        return tot_amount;
    }

    public void setTot_amount(double tot_amount) {
        this.tot_amount = tot_amount;
    }
}
