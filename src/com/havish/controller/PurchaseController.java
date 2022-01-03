package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PurchaseController {
    private static PurchaseController instance=null;
    Scanner sc=new Scanner(System.in);

    private PurchaseController(){

    }

    public static PurchaseController getInstance(){
        if(instance==null){
            instance=new PurchaseController();
            return instance;
        }
        return instance;
    }

    //New Purchase from Dealers
    public void AddPurchase(){
        Map<Integer, Stock> stockMap=ModalController.getInstance().getStockMap();
        Map<Integer, Dealer> dealerMap=ModalController.getInstance().getDealerMap();
        Stock_Purchase stock_purchase=new Stock_Purchase();
        List<Purchase> purchaseList=new ArrayList<>();
        Stock_Purchase_Bill stockPurchaseBill=new Stock_Purchase_Bill();
        Set<Integer> stockKeys=stockMap.keySet();
        int dealer_id;
        boolean flag=true;
        boolean isFind=false;
        boolean exit=false;
        float tot_amount=0;
        int stock_id=0;
        String op="";
        Set<Integer> dealerKeys=dealerMap.keySet();
        if(!dealerKeys.isEmpty()){
            System.out.println("Enter the Dealer id");
            dealer_id=sc.nextInt();
            if(dealerMap.containsKey(dealer_id)){
                stockPurchaseBill.setDealer_id(dealer_id);
            }else {
                System.out.println("Invalid Dealer Id");
                AddPurchase();
            }
        }else{
            dealer_id=DealerController.getInstance().addDealer();
        }
        System.out.println("Enter the products  #-to finish");
        System.out.println();

        while (flag){
            Purchase purchase=new Purchase();
            System.out.println("Enter the product id ");
            op=sc.next();
            if(op.equals("#")){
                exit=true;
                break;
            }else{
                stock_id=Integer.parseInt(op);
            }
            if(!stockKeys.isEmpty()){
                if(stockMap.containsKey(stock_id)){
                    Stock stock=stockMap.get(stock_id);
                    purchase.setStock_id(stock_id);
                    System.out.println("Enter the quantity");
                    int quantity=sc.nextInt();
                    purchase.setQuantity(quantity);
                    System.out.println("Enter the Price");
                    float st_amount=sc.nextFloat();
                    purchase.setPrice(st_amount);
                    float amount=quantity*st_amount;
                    purchase.setTot_amount(amount);
                    tot_amount+=amount;
                    System.out.println("Old Price : "+stock.getStockPrice()+"\nDo you want update Sale Price(y/n)");
                    char ch=sc.next().charAt(0);
                    if(ch=='y'){
                        System.out.println("Enter the Sale Price...");
                        float price=sc.nextFloat();
                        try{
                            SuperMarketDAO.getInstance().updateStockPrice(stock_id,price);
                        }catch (SQLException e){
                            System.out.println(e);
                        }
                    }
                    purchaseList.add(purchase);
                    System.out.println("1.Add\n2.Finish Purchase bill");
                    int choice=sc.nextInt();
                    if(choice==1){
                        continue;
                    }else{
                        flag=false;
                    }
                }else{
                    System.out.println("Invalid Stock ID.....\nDo you want to Add(y/n)?");
                    char ch=sc.next().charAt(0);
                    if(ch=='y'){
                        StockController.getInstance().addStock();
                    }else {
                        continue;
                    }
                }
            }else {
                System.out.println("No Stocks available in Database");
            }

        }
        if(exit){
            PageController.login();
        }else{
            stockPurchaseBill.setPurchase_date(LocalDate.now().toString());
            stockPurchaseBill.setTotal_amount(tot_amount);
            try{
                int purchase_id=SuperMarketDAO.getInstance().insertStockPurchase(stockPurchaseBill);
                stockPurchaseBill.setPurchase_id(purchase_id);
                ModalController.getInstance().addPurchaseBill(stockPurchaseBill);
                SuperMarketDAO.getInstance().updateStock(purchaseList);
                SuperMarketDAO.getInstance().insertStockPurchaseDetails(stockPurchaseBill,purchaseList);
            }catch (SQLException e){
                System.out.println(e);
            }

        }
    }

    //View Purchase Details
    public void purchaseDetails(){
        Map<Integer,Stock_Purchase_Bill> billMap=ModalController.getInstance().getStock_purchase_billMap();
        Map<Integer,Dealer> dealerMap=ModalController.getInstance().getDealerMap();
        Set<Integer> keys=billMap.keySet();
        System.out.println("Purchase Details");
        System.out.println(String.format("%15s %10s %10s %10s %18s %10s %10s", "ID", "|", "DealerName", "|", "Date","|","Amount"));
        for (Integer key :
                keys) {
            Stock_Purchase_Bill purchase=billMap.get(key);
            Dealer dealer=dealerMap.get(purchase.getDealer_id());
            System.out.println(String.format("%15d %10s %10s %10s %18s %10s %10.0f", purchase.getPurchase_id(), "|", dealer.getDealer_name(), "|", purchase.getPurchase_date(),"|",purchase.getTotal_amount()));
        }
    }

    //View Today Purchase History
    public void viewTodayPurchase(){
        try{
            SuperMarketDAO.getInstance().getTodayPurchase();
        }catch (SQLException e){
            System.out.println(e);
        }
    }


}
