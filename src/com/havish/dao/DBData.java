package com.havish.dao;

import com.havish.modal.Purchase;

public class DBData {
    public static final String SCHEMA = "supermarketDB";
    public static class Customer {
        public static final String CUSTOMERTABLE = "Customer";
        public static final String NAME="cust_name";
        public static final String ID="cust_id";
        public static final String EMAIL="cust_email";
        public static final String PHONE_NO="cust_phone";

    }
    public static class Representative{
        public static final String REPTABLE = "Representative";
        public static final String NAME="rep_name";
        public static final String ID="rep_id";
        public static final String EMAIL="rep_email";
        public static final String DATE="date_of_joining";
        public static final String PHONE_NO="rep_phone";
        public static final String AGE="age";
        public static final String REP_TYPE="rep_type";
        public static final String SALARY="salary";
        public static final String PASSCODE="passcode";
        public static final String TOT_AMOUNT="total_amount";
        public static final String TOT_SALES="total_sales";
    }
    public static class Dealer{
        public static final String DEALER_TABLE="dealer";
        public static final String ID="dealer_id";
        public static final String NAME="dealer_name";
        public static final String EMAIL="dealer_email";
        public static final String PHONE_NO="dealer_phone";
    }
    public static class BillDetails{
        public static final String BillTABLE = "BillDetails";
        public static final String ID="bill_id";
        public static final String DATE="bill_date";
        public static final String REP_ID=Representative.ID;
        public static final String CUST_ID=Customer.ID;
        public static final String TOT_AMOUNT="tot_amount";
        public static final String DISCOUNT="discount";
    }
    public static class Stocks{
        public static final String STOCKTABLE = "Stocks";
        public static final String ID="product_id";
        public static final String NAME="product_name";
        public static final String PRICE="price";
        public static final String DATE="update_date";
        public static final String SALES="sales";
        public static final String AVAILABLE="available";
    }
    public static class Customer_Purchase{
        public static final String CUSTOMER_PURCHASE_TABLE="Customer_purchase";
        public static final String ID=Customer.ID;
        public static final String BILL_ID=BillDetails.ID;
        public static final String STOCK_NAME=Stocks.NAME;
        public static final String PRODUCT_ID=Stocks.ID;
        public static final String QUANTITY="quantity";
        public static final String AMOUNT="amount";
    }
    public static class Stock_Purchase_Bill{
        public static final String STOCK_PURCHASE_TABLE = "Stock_purchase_bill";
        public static final String ID="purchase_id";
        public static final String DEALER_ID=Dealer.ID;
        public static final String DATE="purchase_date";
        public static final String AMOUNT="purchase_amount";
    }
    public static class Stock_Purchase{
        public static final String PURCHASE_STOCKS="purchase_stocks";
        public static final String ID=Stocks.ID;
        public static final String DEALER_ID=Dealer.ID;
        public static final String PURCHASE_ID=Stock_Purchase_Bill.ID;
        public static final String QUANTITY="quantity";
        public static final String PRICE="price";
        public static final String TOTAL_AMOUNT="total_amount";
    }
    public static final String MYSQL_USER_NAME = "root";
    public static final String MYSQL_PASSWORD = "";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL="jdbc:mysql://localhost/";
}
