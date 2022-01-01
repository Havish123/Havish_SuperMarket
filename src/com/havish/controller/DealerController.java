package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.Dealer;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DealerController {
    private static DealerController instance=null;
    private DealerController(){

    }

    Scanner sc=new Scanner(System.in);

    public static DealerController getInstance(){
        if(instance==null){
            instance=new DealerController();
            return instance;
        }
        return instance;
    }

    public int addDealer(){
        Dealer dealer=new Dealer();
        System.out.println("Enter the Dealer Name");
        dealer.setDealer_name(sc.next());
        System.out.println("Enter the Dealer Email");
        dealer.setEmail(sc.next());
        System.out.println("Enter Dealer Phone Number");
        dealer.setPhone_no(sc.next());
        try{
            dealer.setDealer_id(SuperMarketDAO.getInstance().insertDealer(dealer));
            ModalController.getInstance().addDealer(dealer);
        }catch (SQLException e){
            System.out.println(e);
        }
        return dealer.getDealer_id();
    }

    public void DealerDetails(){
        Map<Integer,Dealer> dealerMap=ModalController.getInstance().getDealerMap();
        Set<Integer> keys=dealerMap.keySet();
        System.out.println("Dealer List");
        System.out.println(String.format("%15s %10s %10s %10s %18s %10s %10s", "ID", "|", "Name", "|", "Email","|","Phone Number"));
        for (Integer key :
                keys) {
            Dealer dealer=dealerMap.get(key);
            System.out.println(String.format("%15d %10s %-10s %10s %-18s %10s %10s", dealer.getDealer_id(), "|", dealer.getDealer_name(), "|", dealer.getEmail(),"|",dealer.getPhone_no()));
        }
        System.out.println();
    }

}
