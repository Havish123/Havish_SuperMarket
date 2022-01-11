package com.havish.controller;


import com.havish.dao.SuperMarketDAO;
import com.havish.modal.*;

import java.sql.SQLException;
import java.util.*;

public class ModalController {
    private static ModalController instance;
    private static Map<Integer, Customer> customerMap=null;
    private static Map<Integer, Representative> representativeMap=null;
    private static Map<Integer, Stock> stockMap=null;
    private static Map<Integer, Dealer> dealerMap=null;
    private static Map<Integer, BillDetails> billDetailsMap=null;
    private static Map<Integer, List<Customer_Purchase>> customer_purchaseMap=null;
    private static Map<Integer,Stock_Purchase_Bill> stock_purchase_billMap=null;
    private static Map<Integer,List<Stock_Purchase>> stockPurchaseMap=null;

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
    public Map<Integer,List<Customer_Purchase>> getCustPurchaseMap(){
        if(customer_purchaseMap==null){
            customer_purchaseMap=new HashMap<>();
            getBillDetailsMap();
            for (Integer key :
                    billDetailsMap.keySet()) {
                customer_purchaseMap.put(key,SuperMarketDAO.getInstance().getCustomerPurchase(key));
            }
        }
        return customer_purchaseMap;
    }
    public Map<Integer,List<Stock_Purchase>> getStockPurchaseMap(){
        getStock_purchase_billMap();
        if(stockPurchaseMap==null){
            stockPurchaseMap=new HashMap<>();
            for (Integer key :
                    stock_purchase_billMap.keySet()) {
                stockPurchaseMap.put(key,SuperMarketDAO.getInstance().getAllPurchase(key));
            }

        }
        return stockPurchaseMap;
    }

    //Add Data into Map
    public void addStock(Stock stock){
        if(stockMap==null){
            getStockMap();
        }
        stockMap.put(stock.getStockId(),stock);
    }
    public void addRepresentative(Representative rep) {
        if(representativeMap==null){
            getRepresentativeMap();
        }
        representativeMap.put(rep.getRep_id(),rep);
    }
    public void addBillDetail(BillDetails billDetails){
        if(billDetailsMap==null){
            getBillDetailsMap();
        }
        billDetailsMap.put(billDetails.getBill_id(),billDetails);
    }
    public void addCustomerPurchase(int bill_id,List<Customer_Purchase> purchaseList){

    }
    public void addCustomerPurchases(BillDetails billDetails,List<Sales> salesList){
        List<Customer_Purchase> list=new ArrayList<>();
        for (Sales sale :
                salesList) {
            Customer_Purchase customer_purchase=new Customer_Purchase();
            customer_purchase.setStock_id(sale.getProduct_id());
            customer_purchase.setQuantity(sale.getQuantity());
            customer_purchase.setBill_id(billDetails.getBill_id());
            customer_purchase.setTotal_amount(sale.getAmount());
            customer_purchase.setCust_id(billDetails.getCust_id());
            customer_purchase.setStock_name(sale.getStock_name());
            list.add(customer_purchase);
        }
        addCustomerPurchase(billDetails.getBill_id(),list);
    }
    public void addStockPurchase(int purchase_id,List<Stock_Purchase> stock_purchases){
        if(stockPurchaseMap==null){
            getStockPurchaseMap();
        }
        stockPurchaseMap.put(purchase_id,stock_purchases);
    }
    public void addStockPur(Stock_Purchase_Bill bill,List<Purchase> list){
        List<Stock_Purchase> purchaseList=new ArrayList<>();
        for (Purchase p :
                list) {
            Stock_Purchase purchase=new Stock_Purchase();
            purchase.setStock_id(p.getStock_id());
            purchase.setPrice(p.getPrice());
            purchase.setPurchase_id(bill.getPurchase_id());
            purchase.setDealer_id(bill.getDealer_id());
            purchase.setTotal_amount(p.getTot_amount());
            purchase.setQuantity(p.getQuantity());
            purchaseList.add(purchase);
        }
        addStockPurchase(bill.getPurchase_id(),purchaseList);
    }
    public void addCustomer(Customer c){
        if(customerMap==null){
            getCustomerMap();
        }
        customerMap.put(c.getId(),c);
    }
    public void addDealer(Dealer dealer){
        if(dealerMap==null){
            getDealerMap();
        }
        dealerMap.put(dealer.getDealer_id(),dealer);
    }
    public void addPurchaseBill(Stock_Purchase_Bill bill){
        if(stock_purchase_billMap==null){
           getStock_purchase_billMap();
        }
        stock_purchase_billMap.put(bill.getPurchase_id(),bill);
    }

    //Update Details in Map
    public void updateStocks(List<Sales> salesList){
        if(stockMap==null){
            try{
                stockMap= SuperMarketDAO.getInstance().getAllStock();
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        for (Sales sale :
                salesList) {
            Stock stock=stockMap.get(sale.getProduct_id());
            stock.setStockAvailable(stock.getStockAvailable()-sale.getQuantity());
            stock.setStockSales(stock.getStockSales()+sale.getQuantity());
        }
    }
    public void updateStock(List<Purchase> purchaseList){
        if(stockMap==null){
            try{
                stockMap= SuperMarketDAO.getInstance().getAllStock();
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        for (Purchase purchase :
                purchaseList) {
            Stock stock=stockMap.get(purchase.getStock_id());
            stock.setStockAvailable(stock.getStockAvailable()+purchase.getQuantity());
        }
    }
    public void updateSaleRep(int sales,float amount,int rep_id){
        if(representativeMap==null){
            try{
                representativeMap= SuperMarketDAO.getInstance().getAllRep();
            }catch (SQLException e){
                System.out.println(e);
            }
        }
        representativeMap.get(rep_id).setTotal_amount(representativeMap.get(rep_id).getTotal_amount()+amount);
        representativeMap.get(rep_id).setTotal_sales(representativeMap.get(rep_id).getTotal_sales()+sales);
    }


}
