package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.Customer;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CustomerController {
    private static CustomerController instance=null;
    Scanner sc=new Scanner(System.in);
    private CustomerController(){

    }

    public static CustomerController getInstance(){
        if(instance==null){
            instance=new CustomerController();
            return instance;
        }
        return instance;
    }

    //Add New Customer
    public int addCustomer(){
        Customer customer=new Customer();
        System.out.println("Enter the Customer Name");
        customer.setName(sc.next());
        System.out.println("Enter the Customer Email");
        customer.setEmail(sc.next());
        System.out.println("Enter the Customer Phone no");
        customer.setPhone_no(sc.next());
        try{
            customer.setId(SuperMarketDAO.getInstance().insertCustomer(customer));
            ModalController.getInstance().addCustomer(customer);
            return customer.getId();
        }catch (SQLException e){
            System.out.println(e);
        }
        return 0;
    }

    //Show Customer Details
    public void printCustomerDetails(){
        Map<Integer,Customer> customerMap=ModalController.getInstance().getCustomerMap();
        Set<Integer> keys=customerMap.keySet();
        if(!keys.isEmpty()){
            System.out.println("Customer Details");
            System.out.println(String.format("%15s %10s %10s %10s %18s %10s %10s", "ID", "|", "Name", "|", "Email","|","Phone Number"));
            for (Integer key :
                    keys) {
                Customer c=customerMap.get(key);
                System.out.println(String.format("%15d %10s %-10s %10s %-18s %10s %10s", c.getId(), "|", c.getName(), "|", c.getEmail(),"|",c.getPhone_no()));
            }
        }

        System.out.println();
    }

    //Get top 5 Customer List by total Purchase Amount
    public void getTopList(){
        try{
            SuperMarketDAO.getInstance().getTopCustomers();
        }catch (SQLException e){
            System.out.println(e);
        }

    }

}
