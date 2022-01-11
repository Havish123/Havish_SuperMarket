package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.*;

import java.sql.SQLException;
import java.time.LocalDate;
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
    public int AddBill(){
        Map<Integer, Stock> stockMap=ModalController.getInstance().getStockMap();
        Map<Integer, Customer> customerMap=ModalController.getInstance().getCustomerMap();
        Set<Integer> keys=stockMap.keySet();
        if(keys.isEmpty()){
            System.out.println("No Stocks Available!!!\nPlease Contact Manager!!!");
            return 1;
           // System.exit(0);
        }else{
            List<Sales> salesList=new ArrayList<>();
            int salecount=0;
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
                int quantity=0;
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
                            sales.setProduct_id(product_id);
                            Stock stock=stockMap.get(product_id);
                            if(stock.getStockAvailable()<10){
                                System.out.println("Only "+stock.getStockAvailable()+" Stocks Left\n");
                            }
                            while (true){
                                System.out.println("Enter the quantity");
                                quantity=sc.nextInt();
                                if(quantity<=stock.getStockAvailable()){
                                    break;
                                }else {
                                    System.out.println("Only "+stock.getStockAvailable()+" Stocks Left\n");
                                    continue;
                                }
                            }
                            salecount+=quantity;
                            sales.setQuantity(quantity);
                            sales.setStock_name(stock.getStockName());
                            tot_amount=tot_amount+stock.getStockPrice()*quantity;
                            sales.setAmount(stock.getStockPrice()*quantity);
                            salesList.add(sales);
                    }else {
                        System.out.println("Invalid Product id...");
                    }
                }else{
                    System.out.println("No Product Available In the Store");
                }
                System.out.println("1.Add\n2.Print Bill\n3.Exit");
                int ch=sc.nextInt();
                if(ch==1){
                    continue;
                }else if(ch==2){
                    flag=false;
                }else if(ch==3){

                }

            }
            if(exit){
                return 1;
                //PageController.home();
            }else {
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
                //Insert Bill Details into Map
                ModalController.getInstance().addCustomerPurchases(billDetail,salesList);

                //Update Stocks in Database
                SuperMarketDAO.getInstance().updatestock(salesList);

                //Update Representative Sales
                RepresentativeController.getInstance().updateSales(salecount,tot_amount);

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
        }

        }

        return 0;
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

    //Print Particular Bill
    public void viewParticularBill(){
        System.out.println("Enter the Bill Id");
        int bill_id=sc.nextInt();
        if(ModalController.getInstance().getBillDetailsMap().containsKey(bill_id)){
            BillDetails billDetail=ModalController.getInstance().getBillDetailsMap().get(bill_id);
            List<Customer_Purchase> customer_purchases=ModalController.getInstance().getCustPurchaseMap().get(bill_id);
            String customer_name=ModalController.getInstance().getCustomerMap().get(billDetail.getCust_id()).getName();
            System.out.println("_______Super Market_______");
            System.out.println("Bill_id:"+billDetail.getBill_id());
            System.out.println("Customer Name:"+customer_name);
            System.out.println(String.format("%30s %25s %10s %25s %10s", "Item", "|", "Qty", "|", "Price($)"));
            for (Customer_Purchase c :
                    customer_purchases) {
                System.out.println(String.format("%30s %25s %10d %25s %10.2f", c.getStock_name(), "|", c.getQuantity(), "|", c.getTotal_amount()));
            }

            System.out.println("Discount:"+(int)billDetail.getDiscount()+"%");
            float discountAmount=billDetail.getTot_amount()*(billDetail.getDiscount()/100);
            System.out.println("Discount Amount:"+discountAmount);
            System.out.println("Total Amount:"+billDetail.getTot_amount());
            System.out.println();
        }else {
            System.out.println("Invalid Bill ID......");
        }

    }

    //View Today Bills
    public void viewTodayBills(){
        try{
            SuperMarketDAO.getInstance().getTodayBills();
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    //View Particular Period Bills
    public void particularPeriodBills(){
        System.out.println("Enter the Starting Date(YYYY-MM-DD)");
        LocalDate from=LocalDate.parse(sc.next());
        System.out.println("Enter the Ending Date(YYYY-MM-DD)");
        LocalDate to=LocalDate.parse(sc.next());
        if(to.isBefore(from)){
            try {
                SuperMarketDAO.getInstance().viewSomePeriodBill(from,to);
            }catch (SQLException e){
                System.out.println(e);
            }
        }else {
            System.out.println("Please Enter valid Period");
        }

    }


}
