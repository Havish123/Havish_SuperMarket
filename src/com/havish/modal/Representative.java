package com.havish.modal;

public class Representative {
    private int rep_id;
    private String rep_name;
    private int age;
    private int salary;
    private String rep_phoneno;
    private String rep_email;
    private String doj;
    private String rep_type;
    private String passcode;
    private int total_sales;
    private float total_amount;
    public Representative(){

    }

    public Representative(int rep_id, String rep_name, int age, int salary, String rep_phoneno, String rep_email, String doj, String rep_type) {
        super();
        this.rep_id = rep_id;
        this.rep_name = rep_name;
        this.age = age;
        this.salary = salary;
        this.rep_phoneno = rep_phoneno;
        this.rep_email = rep_email;
        this.doj = doj;
        this.rep_type = rep_type;
    }

    public Representative(int rep_id, String rep_name,String passcode,String rep_type) {
        super();
        this.rep_id = rep_id;
        this.rep_name = rep_name;
        this.passcode=passcode;
        this.rep_type=rep_type;
    }



    public int getRep_id() {
        return rep_id;
    }

    public void setRep_id(int rep_id) {
        this.rep_id = rep_id;
    }

    public String getRep_name() {
        return rep_name;
    }

    public void setRep_name(String rep_name) {
        this.rep_name = rep_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getRep_phoneno() {
        return rep_phoneno;
    }

    public void setRep_phoneno(String rep_phoneno) {
        this.rep_phoneno = rep_phoneno;
    }

    public String getRep_email() {
        return rep_email;
    }

    public void setRep_email(String rep_email) {
        this.rep_email = rep_email;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getRep_type() {
        return rep_type;
    }

    public void setRep_type(String rep_type) {
        this.rep_type = rep_type;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public int getTotal_sales() {
        return total_sales;
    }

    public void setTotal_sales(int total_sales) {
        this.total_sales = total_sales;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }
}
