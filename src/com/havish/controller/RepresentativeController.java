package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.Biller;
import com.havish.modal.Manager;
import com.havish.modal.Representative;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class RepresentativeController {
    private static RepresentativeController instance;
    Scanner sc=new Scanner(System.in);
    private RepresentativeController(){

    }

    //Get Representative instance
    public static RepresentativeController getInstance(){
        if(instance==null){
            instance=new RepresentativeController();
            return instance;
        }
        return instance;
    }

    //Add New Representative
    public void addRepresentative() {
        Representative rep = null;
        System.out.println("Enter the Representative Designation\n1.Bill\n2.Product Manager");
        int type=sc.nextInt();
        if(type==1){
            rep=new Biller();
            rep.setRep_type("biller");
            rep.setTotal_amount(0);
            rep.setTotal_sales(0);
        }else if(type==2){
            rep=new Manager();
            rep.setRep_type("manager");
        }
        //Representative rep=new Representative();
        System.out.println("Enter the Representative name");
        rep.setRep_name(sc.next());
        System.out.println("Enter the Representative Age");
        rep.setAge(sc.nextInt());
        System.out.println("Enter the Representative Email");
        rep.setRep_email(sc.next());
        System.out.println("Enter the Representative Phone number");
        rep.setRep_phoneno(sc.next());
        System.out.println("Enter the representative Salary");
        rep.setSalary(sc.nextInt());
        rep.setDoj(LocalDate.now().toString());
        System.out.println("Enter the Representative Passcode");
        rep.setPasscode(sc.next());

        try{
            int id=SuperMarketDAO.getInstance().insertRepresentative(rep);
            System.out.println("Representative Id is: "+id);
            rep.setRep_id(id);
            ModalController.getInstance().addRepresentative(rep);
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //Update Representative Salary
    public void updateSalary(){
        System.out.println("Enter the representative ID");
        int rep_id=sc.nextInt();
        System.out.println("Enter the new Salary of the Representative");
        int salary=sc.nextInt();
        try{
            SuperMarketDAO.getInstance().updateSalary(rep_id,salary);
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //Update Representative Phone number
    public void updatePhoneNo(){
        System.out.println("Enter the representative ID");
        int rep_id=sc.nextInt();
        System.out.println("Enter the new Phone number of the Representative");
        String phone_no=sc.next();
        try{
            SuperMarketDAO.getInstance().updatePhoneNumber(rep_id,phone_no);
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //Update Representative Designation
    public void updateRepType(){
        System.out.println("Enter the representative ID");
        int rep_id=sc.nextInt();
        System.out.println("Enter the Representative Designation\n1.Bill\n2.Product Manager");
        int type=sc.nextInt();
        try{
            if(type==1){
                SuperMarketDAO.getInstance().updateDesignation(rep_id,"biller");
            }else if(type==2){
                SuperMarketDAO.getInstance().updateDesignation(rep_id,"manager");
            }
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //View Representative Details
    public void getAllRep(){
        Map<Integer, Representative> representativeMap=ModalController.getInstance().getRepresentativeMap();
        Set<Integer> keys = representativeMap.keySet();
        System.out.println("Representative List");
        System.out.println(String.format("%8s %10s %16s %10s %8s %10s %18s %10s %10s %10s %8s %10s %11s %10s %8s %10s %12s %10s %11s %10s %8s", "ID", "|", "Name", "|", "Age","|","Email","|","Phone","|","Salary","|","Designation","|","Passcode","|","DOJ","|","Tot_sales","|","Tot_amount"));

        if (!keys.isEmpty())
        {
            for(Integer key : keys) {
                Representative rep = representativeMap.get(key);
                System.out.println(String.format("%8d %10s %16s %10s %8d %10s %18s %10s %10s %10s %8d %10s %11s %10s %8s %10s %12s %10s %11d %10s %8.2f", rep.getRep_id(), "|", rep.getRep_name(), "|", rep.getAge(),"|",rep.getRep_email(),"|",rep.getRep_phoneno(),"|",rep.getSalary(),"|",rep.getRep_type(),"|",rep.getPasscode(),"|",rep.getDoj(),"|",rep.getTotal_sales(),"|",rep.getTotal_amount()));
            }
        }
    }

    //Update Sales
    public void updateSales(int saleCount,float tot_amount){
        Representative rep=PageController.getRep();
        ModalController.getInstance().updateSaleRep(saleCount,tot_amount,rep.getRep_id());
        try{
            SuperMarketDAO.getInstance().updateSalesRep(rep.getRep_id(),saleCount,tot_amount);
        }catch (SQLException e){
            System.out.println(e);
        }

    }



}
