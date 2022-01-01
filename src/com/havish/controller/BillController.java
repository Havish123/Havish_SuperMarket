package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.*;

import java.sql.SQLException;
import java.util.*;

public class BillController {
    Scanner sc=new Scanner(System.in);
    public static BillController instance=null;

    private BillController(){

    }

    public static BillController getInstance(){
        if(instance==null){
            instance=new BillController();
            return instance;
        }else{
            return instance;
        }
    }

    //New Bill Generater
    public void AddBill(){
        Map<Integer, Stock> stockMap=ModalController.getInstance().getStockMap();
        Map<Integer, Customer> customerMap=ModalController.getInstance().getCustomerMap();
        Set<Integer> keys=stockMap.keySet();
        List<Sales> salesList=new ArrayList<>();
        boolean flag=true;
        boolean exit=false;
        BillDetails billDetail=new BillDetails();
        float tot_amount=0;
        int product_id=0;
        String op="";
        System.out.println("New Bill #-for close");
        System.out.println();

        //Add products for billing
        while (flag){
            System.out.println("Enter the product id ");
            op=sc.next();
            if(op.equals("#")){
                exit=true;
                break;
            }else{
                product_id=Integer.parseInt(op);
            }
            if(!keys.isEmpty()){
                if(stockMap.containsKey(product_id)){
                    Sales sales=new Sales();
                    try{
                        sales.setProduct_id(product_id);
                        Stock stock=stockMap.get(product_id);
                        if(stock.getStockAvailable()<10){
                            System.out.println("Only "+stock.getStockAvailable()+" Stocks Left\n");
                        }
                        System.out.println("Enter the quantity");
                        int quantity=sc.nextInt();
                        sales.setQuantity(quantity);
                        sales.setStock_name(stock.getStockName());
                        tot_amount=tot_amount+stock.getStockPrice()*quantity;
                        sales.setAmount(stock.getStockPrice()*quantity);
                        salesList.add(sales);
                    }catch (Exception e){

                    }
                }else {
                    System.out.println("Invalid Product id...");
                }
            }else{
                System.out.println("No Product Available In the Store");
            }
            System.out.println("1.Add\n2.Print Bill");
            int ch=sc.nextInt();
            if(ch==1){
                continue;
            }else{
                flag=false;
            }

        }
        if(exit){
            PageController.login();
        }

        //Get Customer ID
        Set<Integer> customerKeys=customerMap.keySet();

        if(!customerKeys.isEmpty()){
            System.out.println("Enter the Customer Id:");
            int cust_id=sc.nextInt();
            if(customerMap.containsKey(cust_id)){
                billDetail.setCust_id(cust_id);
            }else{
                System.out.println("Invalid Customer Id....");
                billDetail.setCust_id(CustomerController.getInstance().addCustomer());
            }
        }else{
            billDetail.setCust_id(CustomerController.getInstance().addCustomer());
        }
        billDetail.setRep_id(PageController.getRep().getRep_id());
        System.out.println("Enter the Discount(%)");
        int discount=sc.nextInt();
        float discountAmount=tot_amount*((float) discount/100);
        tot_amount=tot_amount-discountAmount;
        billDetail.setTot_amount(tot_amount);
        billDetail.setDiscount(discount);

        //Insert Bill into Database
        try{
            int bill_id= SuperMarketDAO.getInstance().insertBill(billDetail);
            billDetail.setBill_id(bill_id);
        }catch (SQLException e){
            System.out.println(e);
        }
        ModalController.getInstance().addBillDetail(billDetail);

        //Insert Bill Details into database
        try{
            SuperMarketDAO.getInstance().insertCustomerPurchase(billDetail,salesList);
        }catch (SQLException e){
            System.out.println(e);
        }

        //Update Stocks in Database
        SuperMarketDAO.getInstance().updatestock(salesList);

        //Print the Bill
        System.out.println("_______Super Market_______");
        System.out.println("Bill_id:"+billDetail.getBill_id());
        System.out.println("Customer Id:"+billDetail.getCust_id());
        System.out.println(String.format("%30s %25s %10s %25s %10s", "Item", "|", "Qty", "|", "Price($)"));
        for (Sales p :
                salesList) {
            System.out.println(String.format("%30s %25s %10d %25s %10.2f", p.getStock_name(), "|", p.getQuantity(), "|", p.getAmount()));
        }

        System.out.println("Discount:"+(int)discount+"%");
        System.out.println("Discount Amount:"+discountAmount);
        System.out.println("Total Amount:"+(tot_amount-(discountAmount)));
        System.out.println();

        AddBill();
    }

    //Print Bill Details
    public void getBillDetails(){
        Map<Integer,BillDetails> billDetailsMap=ModalController.getInstance().getBillDetailsMap();
        Map<Integer,Customer> customerMap=ModalController.getInstance().getCustomerMap();
        Set<Integer> keys=billDetailsMap.keySet();
        System.out.println("Bill Details");
        System.out.println(String.format("%8s %10s %15s %10s %10s %10s %10s %10s %18s %10s %8s ", "ID", "|", "Customer Name","|", "Rep_id", "|", "Bill Date","|","Amount","|","Discount(%)"));
        for (Integer key :
                keys) {
            BillDetails billDetails=billDetailsMap.get(key);
            Customer customer=customerMap.get(billDetails.getCust_id());
            System.out.println(String.format("%8d %10s %15s %10s %10d %10s %10s %10s %18.2f %10s %8d ", billDetails.getBill_id(), "|", customer.getName(), "|", billDetails.getRep_id(), "|", billDetails.getBill_date(),"|",billDetails.getTot_amount(),"|",billDetails.getDiscount()));
        }
        System.out.println();
    }

}