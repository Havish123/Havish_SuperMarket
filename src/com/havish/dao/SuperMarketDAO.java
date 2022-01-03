package com.havish.dao;

import com.havish.controller.ModalController;
import com.havish.modal.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.NULL;
import static java.sql.Types.REAL;

public class SuperMarketDAO {
    private static SuperMarketDAO instance;
    private static Connection con=DatabaseConnection.getConnection();
    private static PreparedStatement pstmt=null;
    private static Statement stmt=null;

    private SuperMarketDAO(){

    }

    //get SuperMarketDAO instance using Singleton Method
    public static SuperMarketDAO getInstance(){
        if(instance==null){
            instance=new SuperMarketDAO();
            return instance;
        }
        return instance;
    }

    //create Statement for execute Query
    public static void instantiateStmt(){
        try{
            stmt= con.createStatement();
        }catch (SQLException e){
            System.out.println("SQLException!"+e);
        }
    }

    //create Prepare Statement for execute query
    public static void instantiatePstmt(String sql){
        try{
            pstmt=con.prepareStatement(sql);
        }catch (SQLException e){
            System.out.println("SQLException!"+e);
        }
    }

    //Creating tables in the database
    public boolean createTables() throws SQLException{
        String createCustomerTable="CREATE TABLE IF NOT EXISTS "+DBData.Customer.CUSTOMERTABLE+"("+DBData.Customer.ID+" INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"+DBData.Customer.NAME+" VARCHAR(25) NOT NULL,"+DBData.Customer.EMAIL+" VARCHAR(50) NOT NULL,"+DBData.Customer.PHONE_NO+" VARCHAR(10) NOT NULL);";
        String createRepTable="CREATE TABLE IF NOT EXISTS "+DBData.Representative.REPTABLE+"("+DBData.Representative.ID+" INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"+DBData.Representative.NAME+" VARCHAR(25) NOT NULL,"+DBData.Representative.EMAIL+" VARCHAR(50) NOT NULL,"+DBData.Representative.PHONE_NO+" VARCHAR(10) NOT NULL,"+DBData.Representative.AGE+" INT NOT NULL,"+DBData.Representative.DATE+" DATE NOT NULL,"+DBData.Representative.REP_TYPE+" VARCHAR(20) NOT NULL,"+DBData.Representative.SALARY+" INT NOT NULL,"+DBData.Representative.PASSCODE+" VARCHAR(20) NOT NULL,"+DBData.Representative.TOT_AMOUNT+" FLOAT ,"+DBData.Representative.TOT_SALES+" INT );";
        String createBillTable="CREATE TABLE IF NOT EXISTS "+DBData.BillDetails.BillTABLE+"("+DBData.BillDetails.ID+" INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"+DBData.BillDetails.CUST_ID+" INT NOT NULL,"+DBData.BillDetails.REP_ID+" INT NOT NULL,"+DBData.BillDetails.DATE+" DATE NOT NULL,"+DBData.BillDetails.TOT_AMOUNT+" FLOAT NOT NULL,"+DBData.BillDetails.DISCOUNT+" INT NOT NULL,FOREIGN KEY ("+DBData.BillDetails.CUST_ID+") REFERENCES "+DBData.Customer.CUSTOMERTABLE+"("+DBData.Customer.ID+"),FOREIGN KEY ("+DBData.BillDetails.REP_ID+") REFERENCES "+DBData.Representative.REPTABLE+"("+DBData.Representative.ID+"));";
        String createStockTable="CREATE TABLE IF NOT EXISTS "+DBData.Stocks.STOCKTABLE+"("+DBData.Stocks.ID+" INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"+DBData.Stocks.NAME+" VARCHAR(25) NOT NULL,"+DBData.Stocks.PRICE+" FLOAT NOT NULL,"+DBData.Stocks.DATE+" DATE NOT NULL,"+DBData.Stocks.SALES+" INT NOT NULL,"+DBData.Stocks.AVAILABLE+" INT NOT NULL);";
        String createDealerTable="CREATE TABLE IF NOT EXISTS "+DBData.Dealer.DEALER_TABLE+"("+DBData.Dealer.ID+" INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"+DBData.Dealer.NAME+" VARCHAR(20) NOT NULL,"+DBData.Dealer.EMAIL+" VARCHAR(50) NOT NULL,"+DBData.Dealer.PHONE_NO+" VARCHAR(10) NOT NULL)";
        String createStockPurchaseBillTable="CREATE TABLE IF NOT EXISTS "+DBData.Stock_Purchase_Bill.STOCK_PURCHASE_TABLE+"("+DBData.Stock_Purchase_Bill.ID+" INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"+DBData.Stock_Purchase_Bill.DEALER_ID+" INT NOT NULL,"+DBData.Stock_Purchase_Bill.DATE+" DATE NOT NULL,"+DBData.Stock_Purchase_Bill.AMOUNT+" FLOAT NOT NULL,FOREIGN KEY ("+DBData.Stock_Purchase_Bill.DEALER_ID+") REFERENCES "+DBData.Dealer.DEALER_TABLE+"("+DBData.Dealer.ID+"))";
        String createStockPurchaseTable="CREATE TABLE IF NOT EXISTS "+ DBData.Stock_Purchase.PURCHASE_STOCKS+"("+DBData.Stock_Purchase.ID+" INT NOT NULL,"+DBData.Stock_Purchase.PURCHASE_ID+" INT NOT NULL,"+DBData.Stock_Purchase.DEALER_ID+" INT NOT NULL,"+DBData.Stock_Purchase.QUANTITY+" INT NOT NULL,"+DBData.Stock_Purchase.PRICE+" FLOAT NOT NULL,"+DBData.Stock_Purchase.TOTAL_AMOUNT+" FLOAT NOT NULL,FOREIGN KEY ("+ DBData.Stock_Purchase.ID +") REFERENCES "+DBData.Stocks.STOCKTABLE+"("+DBData.Stocks.ID+"),FOREIGN KEY ("+DBData.Stock_Purchase.PURCHASE_ID+") REFERENCES "+DBData.Stock_Purchase_Bill.STOCK_PURCHASE_TABLE+"("+DBData.Stock_Purchase_Bill.ID+"),FOREIGN KEY ("+DBData.Stock_Purchase.DEALER_ID+") REFERENCES "+DBData.Dealer.DEALER_TABLE+"("+DBData.Dealer.ID+"));";
        String createCustomerPurchaseTable="CREATE TABLE IF NOT EXISTS "+DBData.Customer_Purchase.CUSTOMER_PURCHASE_TABLE+"("+DBData.Customer_Purchase.ID+" INT NOT NULL,"+DBData.Customer_Purchase.BILL_ID+" INT NOT NULL,"+DBData.Customer_Purchase.PRODUCT_ID+" INT NOT NULL,"+DBData.Customer_Purchase.STOCK_NAME+" VARCHAR(50) NOT NULL,"+DBData.Customer_Purchase.QUANTITY+" INT NOT NULL,"+DBData.Customer_Purchase.AMOUNT+" FLOAT NOT NULL,FOREIGN KEY ("+DBData.Customer_Purchase.ID+") REFERENCES "+DBData.Customer.CUSTOMERTABLE+"("+DBData.Customer.ID+"),FOREIGN KEY ("+DBData.Customer_Purchase.PRODUCT_ID+") REFERENCES "+DBData.Stocks.STOCKTABLE+"("+DBData.Stocks.ID+"),FOREIGN KEY ("+DBData.Customer_Purchase.BILL_ID+") REFERENCES "+DBData.BillDetails.BillTABLE+"("+DBData.BillDetails.ID+"));";
//        System.out.println(createCustomerTable+"\n"+createRepTable+"\n"+createBillTable+"\n"+createStockTable+"\n"+createDealerTable+"\n"+createStockPurchaseBillTable+"\n"+createStockPurchaseTable+"\n"+createCustomerPurchaseTable);
        try{
            instantiateStmt();
            stmt.executeUpdate(createCustomerTable);
            stmt.executeUpdate(createRepTable);
            stmt.executeUpdate(createBillTable);
            stmt.executeUpdate(createStockTable);
            stmt.executeUpdate(createDealerTable);
            stmt.executeUpdate(createStockPurchaseBillTable);
            stmt.executeUpdate(createStockPurchaseTable);
            stmt.executeUpdate(createCustomerPurchaseTable);
        }finally {
            if(stmt!=null)
                stmt.close();
        }
        return true;
    }

    //Insert a new Customer
    public int insertCustomer(Customer c) throws  SQLException{
        try{
            String insertCustomer="INSERT INTO customer(cust_name,cust_email,cust_phone) VALUES(?,?,?);";
            String rsSql="SELECT LAST_INSERT_ID();";
            instantiatePstmt(insertCustomer);
            pstmt.setString(1,c.getName());
            pstmt.setString(2,c.getEmail());
            pstmt.setString(3,c.getPhone_no());
            int rs=pstmt.executeUpdate();
            if(rs<1){
                System.out.println("Not inserted");
            }else{
                instantiateStmt();
                ResultSet id=stmt.executeQuery(rsSql);
                id.next();
                c.setId(id.getInt(1));
                ModalController.getInstance().addCustomer(c);
                return id.getInt(1);
            }
        }finally {
            if(pstmt!=null )
                pstmt.close();
            if(stmt!=null){
                stmt.close();
            }
        }
        return 0;
    }

    //Insert a new Dealer
    public int insertDealer(Dealer dealer) throws SQLException{
        try{
            String insertDealer="INSERT INTO "+DBData.Dealer.DEALER_TABLE+"("+DBData.Dealer.NAME+","+DBData.Dealer.EMAIL+","+DBData.Dealer.PHONE_NO+") VALUES(?,?,?);";
            String rsSql="SELECT LAST_INSERT_ID();";
            instantiatePstmt(insertDealer);
            pstmt.setString(1,dealer.getDealer_name());
            pstmt.setString(2,dealer.getEmail());
            pstmt.setString(3,dealer.getPhone_no());
            int rs=pstmt.executeUpdate();
            if(rs<1){
                System.out.println("Not inserted");
            }else{
                instantiateStmt();
                ResultSet id=stmt.executeQuery(rsSql);
                id.next();
                dealer.setDealer_id(id.getInt(1));
                ModalController.getInstance().addDealer(dealer);
                return id.getInt(1);
            }
        }finally {
            if(pstmt!=null )
                pstmt.close();
            if(stmt!=null){
                stmt.close();
            }
        }
        return 0;
    }

    //Insert a new Representative
    public int insertRepresentative(Representative rep) throws SQLException{
        try {
            String insertRep="INSERT INTO Representative(rep_name,rep_email,rep_phone,date_of_joining,age,rep_type,salary,passcode,total_amount,total_sales) VALUES(?,?,?,?,?,?,?,?,?,?)";
            String rsSql="SELECT LAST_INSERT_ID();";
            instantiatePstmt(insertRep);
            if(rep.getRep_type().equals("biller")){
                pstmt.setInt(9,0);
                pstmt.setInt(10,0);
            }else{
                pstmt.setNull(9,NULL);
                pstmt.setNull(10,NULL);
            }
            pstmt.setString(1,rep.getRep_name());
            pstmt.setString(2,rep.getRep_email());
            pstmt.setString(3,rep.getRep_phoneno());
            pstmt.setString(4,rep.getDoj());
            pstmt.setInt(5,rep.getAge());
            pstmt.setString(6,rep.getRep_type());
            pstmt.setInt(7,rep.getSalary());
            pstmt.setString(8,rep.getPasscode());
            int rs=pstmt.executeUpdate();
            if(rs<1){
                System.out.println("Not inserted");
            }else{
                instantiateStmt();
                ResultSet id=stmt.executeQuery(rsSql);
                id.next();
                return id.getInt(1);
            }
        }
        finally {
            if(pstmt!=null)
                pstmt.close();
            if(stmt!=null){
                stmt.close();
            }
        }
        return 0;
    }

    //Insert a new Stock
    public int insertStock(Stock stock) throws SQLException{
        try{
            String insertStock="INSERT INTO "+DBData.Stocks.STOCKTABLE+"("+DBData.Stocks.NAME+","+DBData.Stocks.PRICE+","+DBData.Stocks.AVAILABLE+","+DBData.Stocks.SALES+","+DBData.Stocks.DATE+") VALUES(?,?,?,?,?)";
            String rsSql="SELECT LAST_INSERT_ID();";
            instantiatePstmt(insertStock);
            pstmt.setString(1,stock.getStockName());
            pstmt.setFloat(2,stock.getStockPrice());
            pstmt.setInt(3,stock.getStockAvailable());
            pstmt.setInt(4,0);
            stock.setUpdateDate(LocalDate.now().toString());
            pstmt.setString(5, stock.getUpdateDate());
            int rs=pstmt.executeUpdate();
            if(rs<1){
                System.out.println("Not inserted");
            }else{
                instantiateStmt();
                ResultSet id=stmt.executeQuery(rsSql);
                id.next();
                return id.getInt(1);
            }
        }finally {
            if(pstmt!=null)
                pstmt.close();
            if(stmt!=null){
                stmt.close();
            }
        }
        return 0;
    }

    //Insert Bills
    public int insertBill(BillDetails billDetails) throws SQLException{
        try{
            String insertBill="INSERT INTO "+DBData.BillDetails.BillTABLE+"("+DBData.BillDetails.CUST_ID+","+DBData.BillDetails.REP_ID+","+DBData.BillDetails.DATE+","+DBData.BillDetails.TOT_AMOUNT+","+DBData.BillDetails.DISCOUNT+") VALUES(?,?,?,?,?);";
            String rsSql="SELECT LAST_INSERT_ID();";
            instantiatePstmt(insertBill);
            pstmt.setInt(1,billDetails.getCust_id());
            billDetails.setBill_date(LocalDate.now().toString());
            pstmt.setInt(2,billDetails.getRep_id());
            pstmt.setString(3,billDetails.getBill_date());
            pstmt.setFloat(4,billDetails.getTot_amount());
            pstmt.setInt(5,billDetails.getDiscount());
            int rs=pstmt.executeUpdate();
            if(rs<1){
                System.out.println("Not inserted");
            }else{
                instantiateStmt();
                ResultSet id=stmt.executeQuery(rsSql);
                id.next();
                return id.getInt(1);
            }
        }finally {
            if(stmt!=null)
                stmt.close();
            if(pstmt!=null){
                pstmt.close();
            }
        }
        return 0;
    }

    //Insert Stock Purchase Bill
    public int insertStockPurchase(Stock_Purchase_Bill stockPurchaseBill) throws SQLException{
        String sql="INSERT INTO "+DBData.Stock_Purchase_Bill.STOCK_PURCHASE_TABLE+"("+DBData.Stock_Purchase_Bill.DEALER_ID+","+DBData.Stock_Purchase_Bill.DATE+","+DBData.Stock_Purchase_Bill.AMOUNT+") VALUES (?,?,?);";
        String rsSql="SELECT LAST_INSERT_ID();";
        try{
            instantiatePstmt(sql);
            pstmt.setInt(1,stockPurchaseBill.getDealer_id());
            pstmt.setString(2,stockPurchaseBill.getPurchase_date());
            pstmt.setFloat(3,stockPurchaseBill.getTotal_amount());
            int rs=pstmt.executeUpdate();
            if(rs<1){
                System.out.println("Not inserted");
            }else{
                instantiateStmt();
                ResultSet id=stmt.executeQuery(rsSql);
                id.next();
                return id.getInt(1);
            }
        }finally {
            if(stmt!=null)
                stmt.close();
            if(pstmt!=null)
                pstmt.close();
        }
        return 0;
    }

    //Insert Stock Purchase Details
    public int insertStockPurchaseDetails(Stock_Purchase_Bill bill,List<Purchase> purchaseList) throws SQLException{
        String sql="INSERT INTO "+DBData.Stock_Purchase.PURCHASE_STOCKS+"("+DBData.Stock_Purchase.PURCHASE_ID+","+DBData.Stock_Purchase.DEALER_ID+","+DBData.Stock_Purchase.ID+","+DBData.Stock_Purchase.PRICE+","+DBData.Stock_Purchase.QUANTITY+","+DBData.Stock_Purchase.TOTAL_AMOUNT+") VALUES(?,?,?,?,?,?);";
        try{
            instantiatePstmt(sql);

            pstmt.setInt(1,bill.getPurchase_id());
            pstmt.setInt(2,bill.getDealer_id());
            for (Purchase purchase :
                    purchaseList) {
                pstmt.setInt(3,purchase.getStock_id());
                pstmt.setFloat(4,purchase.getPrice());
                pstmt.setInt(5,purchase.getQuantity());
                pstmt.setFloat(6,purchase.getTot_amount());
                pstmt.executeUpdate();
            }

        }finally {
            if(pstmt!=null)
                pstmt.close();
        }
        return 0;
    }

    //Insert Customer Purchase List
    public void insertCustomerPurchase(BillDetails billDetails,List<Sales> sales) throws SQLException{
        String sql="INSERT INTO "+DBData.Customer_Purchase.CUSTOMER_PURCHASE_TABLE+"(cust_id,bill_id,product_id,product_name,quantity,amount) VALUES(?,?,?,?,?,?);";
        try{
            instantiatePstmt(sql);
            pstmt.setInt(1,billDetails.getCust_id());
            pstmt.setInt(2,billDetails.getBill_id());
            for (Sales s :
                    sales) {
                pstmt.setInt(3,s.getProduct_id());
                pstmt.setString(4,s.getStock_name());
                pstmt.setInt(5,s.getQuantity());
                pstmt.setFloat(6,s.getAmount());
                pstmt.executeUpdate();
            }
        }finally {
            if(pstmt!=null){
                pstmt.close();
            }
        }


    }

    //Get a particular Representative Details
    public Representative getRepresentative(int id) throws SQLException{
        Representative rep=new Representative();
        try{
            String SELECT="SELECT * FROM "+DBData.Representative.REPTABLE+" WHERE "+DBData.Representative.ID+"="+id+";";
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(SELECT);
            if(!rs.next()){

            }else {
                rep=new Representative(rs.getInt(DBData.Representative.ID),rs.getString(DBData.Representative.NAME),rs.getString(DBData.Representative.PASSCODE),rs.getString(DBData.Representative.REP_TYPE));
            }
        }finally {
            if(stmt!=null){
                stmt.close();
            }
        }

        return rep;
    }

    //Update Stocks when Sales
    public int updatestock(List<Sales> salesList){
        ModalController.getInstance().updateStocks(salesList);
        String sql="UPDATE "+DBData.Stocks.STOCKTABLE+" SET available=available-?,sales=sales+? where product_id=?;";
        try{
            instantiatePstmt(sql);
            for (Sales sale :
                    salesList) {
                pstmt.setInt(1, sale.getQuantity());
                pstmt.setInt(2,sale.getQuantity());
                pstmt.setInt(3,sale.getProduct_id());
                pstmt.executeUpdate();
            }
            pstmt.close();
        }catch (SQLException e){
            System.out.println(e);
        }
        return 0;
    }

    //Update Stock When Purchase
    public int updateStock(List<Purchase> purchaseList){
        ModalController.getInstance().updateStock(purchaseList);
        String sql="UPDATE "+DBData.Stocks.STOCKTABLE+" SET available=available+?,"+DBData.Stocks.DATE+"=? where product_id=?;";
        try{
            instantiatePstmt(sql);
            for (Purchase purchase :
                    purchaseList) {
                pstmt.setInt(1, purchase.getQuantity());
                pstmt.setString(2,LocalDate.now().toString());
                pstmt.setInt(3,purchase.getStock_id());
                pstmt.executeUpdate();
            }
            pstmt.close();
        }catch (SQLException e){
            System.out.println(e);
        }
        return 0;
    }

    //Update Stock Price
    public int updateStockPrice(int stock_id,float price)throws SQLException{
        String sql="UPDATE "+DBData.Stocks.STOCKTABLE+" SET "+DBData.Stocks.PRICE+"=? WHERE "+DBData.Stocks.ID+"=?;" ;
        try{
            instantiatePstmt(sql);
            pstmt.setFloat(1,price);
            pstmt.setInt(2,stock_id);
            pstmt.executeUpdate();
        }finally {
            if(pstmt!=null)
                pstmt.close();
        }
        return 0;
    }

    //Update Representative Salary
    public int updateSalary(int rep_id,int salary) throws SQLException{
        String sql="UPDATE "+DBData.Representative.REPTABLE+" SET "+ DBData.Representative.SALARY +"=? WHERE "+ DBData.Representative.ID +"=?";
        try{
            instantiatePstmt(sql);
            pstmt.setInt(1,salary);
            pstmt.setInt(2,rep_id);
            pstmt.executeUpdate();

        }finally {
            if(pstmt!=null){
                pstmt.close();
            }
        }
        return 0;
    }

    //Update Representative Phone Number
    public int updatePhoneNumber(int rep_id,String phone_no) throws SQLException{
        String sql="UPDATE "+DBData.Representative.REPTABLE+" SET "+ DBData.Representative.PHONE_NO +"=? WHERE "+ DBData.Representative.ID +"=?";
        try{
            instantiatePstmt(sql);
            pstmt.setString(1,phone_no);
            pstmt.setInt(2,rep_id);
            pstmt.executeUpdate();

        }finally {
            if(pstmt!=null){
                pstmt.close();
            }
        }
        return 0;
    }

    //Update Reperesentative Designation
    public int updateDesignation(int rep_id,String type) throws SQLException{
        String sql="UPDATE "+DBData.Representative.REPTABLE+" SET "+ DBData.Representative.REP_TYPE +"=? WHERE "+ DBData.Representative.ID +"=?";
        try{
            instantiatePstmt(sql);
            pstmt.setString(1,type);
            pstmt.setInt(2,rep_id);
            pstmt.executeUpdate();

        }finally {
            if(pstmt!=null){
                pstmt.close();
            }
        }
        return 0;
    }

    //Update Representative Sales
    public int updateSalesRep(int rep_id,int sales,float amount) throws  SQLException{
        String sql="UPDATE "+DBData.Representative.REPTABLE+" SET "+DBData.Representative.TOT_SALES+"="+DBData.Representative.TOT_SALES+"+?,"+DBData.Representative.TOT_AMOUNT+"="+DBData.Representative.TOT_AMOUNT+"+? WHERE "+DBData.Representative.ID+"=?;";
        try{
            instantiatePstmt(sql);
            pstmt.setInt(1,sales);
            pstmt.setFloat(2,amount);
            pstmt.setInt(3,rep_id);
            pstmt.executeUpdate();
        }finally {
            if(pstmt!=null)
                pstmt.close();
        }
        return 0;
    }

    //Get all Dealer Details
    public Map<Integer, Dealer> getAllDealer() throws SQLException{
        Map<Integer,Dealer> dealerMap=new HashMap<>();
        String sql="SELECT * FROM "+DBData.Dealer.DEALER_TABLE+";";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                Dealer dealer=new Dealer();
                int id=rs.getInt(DBData.Dealer.ID);
                dealer.setDealer_id(id);
                dealer.setDealer_name(rs.getString(DBData.Dealer.NAME));
                dealer.setEmail(rs.getString(DBData.Dealer.EMAIL));
                dealer.setPhone_no(rs.getString(DBData.Dealer.PHONE_NO));
                dealerMap.put(id,dealer);
            }
        }finally {
            if(stmt!=null)
                stmt.close();
        }
        return dealerMap;
    }

    //Get all representative Details
    public Map<Integer, Representative> getAllRep() throws SQLException{
        Map<Integer, Representative> representativeMap=new HashMap<>();
        String sql="SELECT * FROM "+ DBData.Representative.REPTABLE+";";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                Representative rep=new Representative();
                int rep_id=rs.getInt(DBData.Representative.ID);
                rep.setRep_id(rep_id);
                rep.setRep_name(rs.getString(DBData.Representative.NAME));
                rep.setRep_phoneno(rs.getString(DBData.Representative.PHONE_NO));
                rep.setDoj(rs.getString(DBData.Representative.DATE));
                rep.setSalary(rs.getInt(DBData.Representative.SALARY));
                rep.setRep_email(rs.getString(DBData.Representative.EMAIL));
                rep.setRep_type(rs.getString(DBData.Representative.REP_TYPE));
                rep.setPasscode(rs.getString(DBData.Representative.PASSCODE));
                rep.setAge(rs.getInt(DBData.Representative.AGE));
                rep.setTotal_sales(rs.getInt(DBData.Representative.TOT_SALES));
                rep.setTotal_amount(rs.getFloat(DBData.Representative.TOT_AMOUNT));
                representativeMap.put(rep_id,rep);
                //System.out.println(representativeMap);

            }
        }finally {
            if(stmt!=null){
                stmt.close();
            }
        }
        return representativeMap;
    }

    //Get all Customers Details
    public Map<Integer, Customer> getAllCustomer() throws SQLException{
        Map<Integer,Customer> customerMap=new HashMap<>();
        String sql="SELECT * FROM "+DBData.Customer.CUSTOMERTABLE+";";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                Customer c=new Customer();
                int id=rs.getInt(DBData.Customer.ID);
                c.setId(id);
                c.setName(rs.getString(DBData.Customer.NAME));
                c.setEmail(rs.getString(DBData.Customer.EMAIL));
                c.setPhone_no(rs.getString(DBData.Customer.PHONE_NO));
                customerMap.put(id,c);
            }
        }finally {
            if (stmt!=null){
                stmt.close();
            }
        }
        return customerMap;
    }

    //Get All Stocks
    public Map<Integer,Stock> getAllStock() throws SQLException{
        Map<Integer,Stock> stockMap=new HashMap<>();
        String sql="SELECT * FROM "+DBData.Stocks.STOCKTABLE+";";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                Stock stock=new Stock();
                int id=rs.getInt(DBData.Stocks.ID);
                stock.setStockId(id);
                stock.setStockName(rs.getString(DBData.Stocks.NAME));
                stock.setStockPrice(rs.getFloat(DBData.Stocks.PRICE));
                stock.setStockSales(rs.getInt(DBData.Stocks.SALES));
                stock.setStockAvailable(rs.getInt(DBData.Stocks.AVAILABLE));
                stock.setUpdateDate(rs.getString(DBData.Stocks.DATE));
                stockMap.put(id,stock);
            }
        }finally {
            if(stmt!=null){
                stmt.close();
            }
        }

        return stockMap;
    }

    //Get All Bills
    public Map<Integer,BillDetails> getAllBills() throws SQLException{
        Map<Integer, BillDetails> billDetailsMap=new HashMap<>();
        String sql="SELECT * FROM "+DBData.BillDetails.BillTABLE+";";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                BillDetails billDetails=new BillDetails();
                int id=rs.getInt(DBData.BillDetails.ID);
                billDetails.setBill_id(id);
                billDetails.setCust_id(rs.getInt(DBData.BillDetails.CUST_ID));
                billDetails.setRep_id(rs.getInt(DBData.BillDetails.REP_ID));
                billDetails.setTot_amount(rs.getFloat(DBData.BillDetails.TOT_AMOUNT));
                billDetails.setDiscount(rs.getInt(DBData.BillDetails.DISCOUNT));
                billDetails.setBill_date(rs.getString(DBData.BillDetails.DATE));
                ModalController.getInstance().addCustomerPurchase(id,getCustomerPurchase(id));
                billDetailsMap.put(id,billDetails);
            }
        }finally {
            if(stmt!=null){
                stmt.close();
            }
        }
        return billDetailsMap;
    }

    //Get Bill Details
    public List<Customer_Purchase> getCustomerPurchase(int bill_id){
        List<Customer_Purchase> customer_purchases=new ArrayList<>();
        String sql="SELECT * FROM "+DBData.Customer_Purchase.CUSTOMER_PURCHASE_TABLE+" WHERE "+DBData.Customer_Purchase.BILL_ID+"="+bill_id+";";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                Customer_Purchase customer_purchase=new Customer_Purchase();
                customer_purchase.setCust_id(rs.getInt(DBData.Customer_Purchase.ID));
                customer_purchase.setBill_id(rs.getInt(DBData.Customer_Purchase.BILL_ID));
                customer_purchase.setStock_id(rs.getInt(DBData.Customer_Purchase.PRODUCT_ID));
                customer_purchase.setQuantity(rs.getInt(DBData.Customer_Purchase.QUANTITY));
                customer_purchase.setTotal_amount(rs.getFloat(DBData.Customer_Purchase.AMOUNT));
                customer_purchase.setStock_name(rs.getString(DBData.Customer_Purchase.STOCK_NAME));
                customer_purchases.add(customer_purchase);
            }
            stmt.close();
        }catch (SQLException e){
            System.out.println();
        }
        return customer_purchases;
    }

    //Get Purchase Details
    public Map<Integer,Stock_Purchase_Bill> getAllPurchaseBill() throws SQLException{
        Map<Integer,Stock_Purchase_Bill> billMap=new HashMap<>();
        String sql="SELECT * FROM "+DBData.Stock_Purchase_Bill.STOCK_PURCHASE_TABLE+";";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            if(!rs.next()){
                System.out.println("No purchase Bill Available");
            }else{
                do{
                    int id=rs.getInt(DBData.Stock_Purchase_Bill.ID);
                    Stock_Purchase_Bill bill=new Stock_Purchase_Bill();
                    bill.setPurchase_id(id);
                    bill.setDealer_id(rs.getInt(DBData.Stock_Purchase_Bill.DEALER_ID));
                    bill.setPurchase_date(rs.getString(DBData.Stock_Purchase_Bill.DATE));
                    bill.setTotal_amount(rs.getFloat(DBData.Stock_Purchase_Bill.AMOUNT));
                    billMap.put(id,bill);
                }while(rs.next());
            }

        }finally {
            if(stmt!=null)
                stmt.close();
        }
        return billMap;
    }

    //Get Top 5 Customers
    public void getTopCustomers() throws SQLException{
        String sql = "Select c."+DBData.Customer.ID+", c."+DBData.Customer.NAME+", SUM(b."+DBData.BillDetails.TOT_AMOUNT+") as Total from "+DBData.BillDetails.BillTABLE+" b INNER JOIN "+DBData.Customer.CUSTOMERTABLE+" c ON c."+DBData.Customer.ID+" = b."+DBData.BillDetails.CUST_ID+" GROUP BY b."+DBData.Customer.ID+" ORDER BY Total DESC LIMIT 5;";
        try{
            System.out.println(sql);
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            if(!rs.next()){
                System.out.println("No data Available....");
            }else {
                System.out.println(String.format("%15s %10s %10s %10s %20s", "ID", "|", "Name", "|", "Total Amount"));
                do{
                    System.out.println(String.format("%15d %10s %-10s %10s %-20s", rs.getInt(DBData.Customer.ID), "|", rs.getString(DBData.Customer.NAME), "|", rs.getFloat("Total")));
                }while (rs.next());
            }

        }finally {
            if(stmt!=null)
                stmt.close();
        }
    }

    //Get Low Available Stocks
    public void getLowAvailableStocks() throws SQLException{
        String sql="SELECT p."+DBData.Stocks.ID+",p."+DBData.Stocks.NAME+",p."+DBData.Stocks.AVAILABLE+" FROM "+DBData.Stocks.STOCKTABLE+" as p WHERE p."+DBData.Stocks.AVAILABLE+"<20;";

        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            if(!rs.next()){
                System.out.println("No data Available");
            }else{
                System.out.println(String.format("%15s %10s %10s %10s %20s", "ID", "|", "Name", "|", "Available Stock "));
                do{
                    System.out.println(String.format("%15d %10s %-10s %10s %-20s", rs.getInt(DBData.Stocks.ID), "|", rs.getString(DBData.Stocks.NAME), "|", rs.getInt(DBData.Stocks.AVAILABLE)));
                }while (rs.next());
            }
        }finally {
            if(stmt!=null){
                stmt.close();
            }
        }
    }

    //View Today Bill Details
    public void getTodayBills() throws SQLException{
        String sql="SELECT * FROM "+DBData.BillDetails.BillTABLE+" WHERE "+DBData.BillDetails.DATE+"=?";
        try{
            instantiatePstmt(sql);
            String date=LocalDate.now().toString();
            pstmt.setString(1,date);
            ResultSet rs=pstmt.executeQuery();
            if(!rs.next()){
                System.out.println("No Bills Availabe Today.........");
            }else {
                System.out.println(String.format("%8s %10s %15s %10s %10s %10s %10s %10s %18s %10s %8s ", "ID", "|", "Customer ID","|", "Rep_id", "|", "Bill Date","|","Amount","|","Discount(%)"));
                do{
                    System.out.println(String.format("%8d %10s %15d %10s %10d %10s %10s %10s %18.2f %10s %8d ", rs.getInt(DBData.BillDetails.ID), "|", rs.getInt(DBData.BillDetails.CUST_ID), "|", rs.getInt(DBData.BillDetails.REP_ID), "|",rs.getString(DBData.BillDetails.DATE),"|",rs.getFloat(DBData.BillDetails.TOT_AMOUNT),"|",rs.getInt(DBData.BillDetails.DISCOUNT)));
                }while (rs.next());
            }

        }finally {
            if(pstmt!=null)
                pstmt.close();
        }
    }

    //View Today Purchase
    public void getTodayPurchase() throws SQLException{
        String sql="SELECT * FROM "+DBData.Stock_Purchase_Bill.STOCK_PURCHASE_TABLE+" WHERE "+DBData.Stock_Purchase_Bill.DATE+"=?";
        try{
            instantiatePstmt(sql);
            String date=LocalDate.now().toString();
            pstmt.setString(1,date);
            ResultSet rs=pstmt.executeQuery();
            if(!rs.next()){
                System.out.println("No Purchase Bills Availabe Today.........");
            }else {
                do{
                    System.out.println(String.format("%8s %10s %15s %10s %10s %10s %10s", "ID", "|", "Dealer ID","|", "Purchase_date", "|", "Amount"));
                    System.out.println(String.format("%8d %10s %15d %10s %10d %10s %10.2f ", rs.getInt(DBData.Stock_Purchase_Bill.ID), "|", rs.getInt(DBData.Stock_Purchase_Bill.DEALER_ID), "|", rs.getString(DBData.Stock_Purchase_Bill.DATE), "|",rs.getString(DBData.Stock_Purchase_Bill.AMOUNT)));
                }while (rs.next());
            }

        }finally {
            if(pstmt!=null)
                pstmt.close();
        }
    }

    //View Top Sales Stocks
    public void viewTopSalesStocks() throws SQLException{
        String sql="SELECT * FROM "+DBData.Stocks.STOCKTABLE+" ORDER BY "+DBData.Stocks.SALES+" DESC LIMIT 5";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            if(!rs.next()){
                System.out.println("No Stocks available");
            }else{
                System.out.println(String.format("%8s %10s %28s %10s %10s %10s %18s %10s %14s %10s %8s ", "ID", "|", "Name", "|", "PRICE","|","DATE","|","SALES","|","Available"));
                do{
                    System.out.println(String.format("%8d %10s %28s %10s %10.2f %10s %18s %10s %14d %10s %8d ",rs.getInt(DBData.Stocks.ID), "|", rs.getString(DBData.Stocks.NAME), "|", rs.getFloat(DBData.Stocks.PRICE),"|",rs.getString(DBData.Stocks.DATE),"|",rs.getInt(DBData.Stocks.SALES),"|",rs.getInt(DBData.Stocks.AVAILABLE)));
                }while (rs.next());
            }
        }finally {
            if(stmt!=null)
                stmt.close();
        }
    }

    //View Particular Period Bills
    public void viewSomePeriodBill(LocalDate from ,LocalDate to) throws SQLException{
        String sql="SELECT * FROM "+DBData.BillDetails.BillTABLE+" WHERE "+DBData.BillDetails.DATE+">='"+from+"' AND "+DBData.BillDetails.DATE+"<='"+to+"';";
        try{
            instantiateStmt();
            ResultSet rs=stmt.executeQuery(sql);
            if(!rs.next()){
                System.out.println("No Bill Details Availabe");
            }else{
                System.out.println(String.format("%8s %10s %15s %10s %10s %10s %10s %10s %18s %10s %8s ", "ID", "|", "Customer ID","|", "Rep_id", "|", "Bill Date","|","Amount","|","Discount(%)"));
                do{
                    System.out.println(String.format("%8d %10s %15d %10s %10d %10s %10s %10s %18.2f %10s %8d ", rs.getInt(DBData.BillDetails.ID), "|", rs.getInt(DBData.BillDetails.CUST_ID), "|", rs.getInt(DBData.BillDetails.REP_ID), "|",rs.getString(DBData.BillDetails.DATE),"|",rs.getFloat(DBData.BillDetails.TOT_AMOUNT),"|",rs.getInt(DBData.BillDetails.DISCOUNT)));
                }while (rs.next());
            }
        }finally {
            if(stmt!=null)
                stmt.close();
        }
    }
}
