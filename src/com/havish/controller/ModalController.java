package com.havish.controller;


import com.havish.dao.SuperMarketDAO;
import com.havish.modal.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModalController {
    private static ModalController instance;
    private static Map<Integer, Customer> customerMap=null;
    private static Map<Integer, Representative> representativeMap=null;
    private static Map<Integer, Stock> stockMap=null;
    private static Map<Integer, Dealer> dealerMap=null;
    private static Map<Integer, BillDetails> billDetailsMap=null;
    private static Map<Integer, List<Customer_Purchase>> customer_purchaseMap=null;
    private static Map<Integer,Stock_Purchase_Bill> stock_purchase_billMap=null;

    private ModalController(){

    }

    public static ModalController getInstance(){
        if(instance==null){
            instance=new ModalController();
            return instance;
        }
        return instance;
    }

    //Get all Data from Database
    public Map<Integer,Customer> getCustomerMap(){
        if(customerMap==null){
            try{
                customerMap=SuperMarketDAO.getInstance().getAllCustomer();

            }catch (SQLException e){
                System.out.println(e);
            }
        }
        return customerMap;
    }
    public Map<Integer, Representative> getRepresentativeMap() {
        if(representativeMap==null){
            try{
                representativeMap= SuperMarketDAO.getInstance().getAllRep();
            }catch (SQLException e){
                System.out.println(e);
            }

        }
        return representativeMap;
    }
    public Map<Integer, Stock> getStockMap() {
        if(stockMap==null){
            try{
                stockMap=SuperMarketDAO.getInstance().getAllStock();
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        return stockMap;
    }
    public Map<Integer,BillDetails> getBillDetailsMap(){
        if(billDetailsMap==null){
            try{
                billDetailsMap=SuperMarketDAO.getInstance().getAllBills();
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        return billDetailsMap;
    }
    public Map<Integer,Stock_Purchase_Bill> getStock_purchase_billMap(){
        if(stock_purchase_billMap==null){
            try{
                stock_purchase_billMap=SuperMarketDAO.getInstance().getAllPurchaseBill();
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        return stock_purchase_billMap;
    }
    public Map<Integer,Dealer> getDealerMap(){
        if(dealerMap==null){
            try{
                dealerMap=SuperMarketDAO.getInstance().getAllDealer();
            }catch (SQLException e){
                //System.out.println(e);
            }
        }
        return dealerMap;
    }

    //Add Data into Map
    public void addStock(Stock stock){
        if(stockMap==null){
            try{
                stockMap=SuperMarketDAO.getInstance().getAllStock();
            }catch (SQLException e){
                System.out.println(e);
            }
            stockMap.put(stock.getStockId(),stock);
        }else {
            stockMap.put(stock.getStockId(),stock);
        }
    }
    public void addRepresentative(Representative rep) {
        if(representativeMap==null){
            try{
                representativeMap=SuperMarketDAO.getInstance().getAllRep();
            }catch (SQLException e){
                System.out.println(e);
            }
            representativeMap.put(rep.getRep_id(),rep);
        }else{
            representativeMap.put(rep.getRep_id(),rep);
        }
    }
    public void addBillDetail(BillDetails billDetails){
        if(billDetailsMap==null){
            try{
                billDetailsMap=SuperMarketDAO.getInstance().getAllBills();
            }catch (SQLException e){
                System.out.println(e);
            }
            billDetailsMap.put(billDetails.getBill_id(),billDetails);
        }
        billDetailsMap.put(billDetails.getBill_id(),billDetails);
    }
    public void addCustomerPurchase(int bill_id,List<Customer_Purchase> customer_purchases){
        if(customer_purchaseMap==null){
            customer_purchaseMap=new HashMap<>();
            customer_purchaseMap.put(bill_id,customer_purchases);
        }
        customer_purchaseMap.put(bill_id,customer_purchases);
    }
    public void addCustomer(Customer c){
        if(customerMap==null){
            try{
                customerMap=SuperMarketDAO.getInstance().getAllCustomer();
                customerMap.put(c.getId(),c);
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        customerMap.put(c.getId(),c);
    }
    public void addDealer(Dealer dealer){
        if(dealerMap==null){
            try{
                dealerMap=SuperMarketDAO.getInstance().getAllDealer();
                dealerMap.put(dealer.getDealer_id(),dealer);
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        dealerMap.put(dealer.getDealer_id(),dealer);
    }
    public void addPurchaseBill(Stock_Purchase_Bill bill){
        if(stock_purchase_billMap==null){
            try{
                stock_purchase_billMap=SuperMarketDAO.getInstance().getAllPurchaseBill();
                stock_purchase_billMap.put(bill.getPurchase_id(),bill);
            }catch (SQLException e){
                System.out.println(e);
            }
            stock_purchase_billMap.put(bill.getPurchase_id(),bill);
        }
    }

    //Update Details in Map
    public void updateStocks(List<Sales> salesList){
        for (Sales sale :
                salesList) {
            Stock stock=stockMap.get(sale.getProduct_id());
            stock.setStockAvailable(stock.getStockAvailable()-sale.getQuantity());
            stock.setStockSales(stock.getStockSales()+sale.getQuantity());
        }
    }
    public void updateStock(List<Purchase> purchaseList){
        for (Purchase purchase :
                purchaseList) {
            Stock stock=stockMap.get(purchase.getStock_id());
            stock.setStockAvailable(stock.getStockAvailable()+purchase.getQuantity());
        }
    }


}
