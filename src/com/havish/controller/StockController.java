package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.Representative;
import com.havish.modal.Stock;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class StockController {
    private static StockController instance=null;
    private StockController(){

    }

    public static StockController getInstance(){
        if(instance==null){
            instance=new StockController();
            return instance;
        }
        return instance;
    }


    Scanner sc=new Scanner(System.in);
    //Add new Stock into Database
    public void addStock(){
        Stock stock=new Stock();
        System.out.println("Enter the product name");
        stock.setStockName(sc.next());
        System.out.println("Enter the Stock price");
        stock.setStockPrice(sc.nextFloat());
        System.out.println("Enter Available stock");
        stock.setStockAvailable(sc.nextInt());
        stock.setStockSales(0);
        try{
            int result= SuperMarketDAO.getInstance().insertStock(stock);
            System.out.println("Product Id is:"+result);
            stock.setStockId(result);
            ModalController.getInstance().addStock(stock);
        }catch (SQLException e){
            System.out.println(e);
        }


    }

    //Update Stock Price
    public void updateStockPrice(){
        Map<Integer,Stock> stockMap=ModalController.getInstance().getStockMap();
        //Set<Integer> keys=stockMap.keySet();
        System.out.println("Enter the Stock ID");
        int id=sc.nextInt();
        if(stockMap.containsKey(id)){
            System.out.println("Enter the New Sale Price");
            float amount=sc.nextFloat();
            try{
                SuperMarketDAO.getInstance().updateStockPrice(id,amount);
            }catch (SQLException e){
                System.out.println(e);
            }
        }else{
            System.out.println("Invalid Stock ID");
        }
    }

    //View Stock Details
    public void viewStock(){
        Map<Integer,Stock> stockMap=ModalController.getInstance().getStockMap();
        Set<Integer> keys=stockMap.keySet();
        if(!keys.isEmpty()){
            System.out.println("Stock Details");
            System.out.println(String.format("%8s %10s %28s %10s %10s %10s %18s %10s %14s %10s %8s ", "ID", "|", "Name", "|", "PRICE","|","DATE","|","SALES","|","Available"));
            for(Integer key : keys) {
                Stock stock=stockMap.get(key);
                System.out.println(String.format("%8d %10s %28s %10s %10.2f %10s %18s %10s %14d %10s %8d ", stock.getStockId(), "|", stock.getStockName(), "|", stock.getStockPrice(),"|",stock.getUpdateDate(),"|",stock.getStockSales(),"|",stock.getStockAvailable()));
            }
        }else{
            System.out.println("No Stock Available");
        }

    }

    //View Stock ID, Name,Price
    public void viewStockList(){
        Map<Integer,Stock> stockMap=ModalController.getInstance().getStockMap();
        Set<Integer> keys=stockMap.keySet();
        if(!keys.isEmpty()){
            System.out.println("Stock Details");
            System.out.println(String.format("%8s %10s %28s %10s %10s", "ID", "|", "Name", "|", "PRICE"));
            for(Integer key : keys) {
                Stock stock=stockMap.get(key);
                System.out.println(String.format("%8d %10s %28s %10s %10.2f", stock.getStockId(), "|", stock.getStockName(), "|", stock.getStockPrice()));
            }
        }else{
            System.out.println("No StockList Available");
        }
    }

    //View Low Available Stocks
    public void viewLowStocks(){
        try{
            SuperMarketDAO.getInstance().getLowAvailableStocks();
        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
