package com.havish.modal;

public class Stock {
    private int stockId;
    private String stockName;
    private int stockSales;
    private int stockAvailable;
    private float stockPrice;
    private String updateDate;


    public Stock() {

    }

    public int getStockSales() {
        return stockSales;
    }

    public void setStockSales(int stockSales) {
        this.stockSales = stockSales;
    }

    public Stock(int stockId, String stockName, int stockAvailable, float stockPrice) {

        this.stockId = stockId;
        this.stockName = stockName;
        this.stockAvailable = stockAvailable;
        this.stockPrice = stockPrice;
    }

    public Stock(int stockId, String stockName, float stockPrice) {

        this.stockId = stockId;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(int stockAvailable) {
        this.stockAvailable = stockAvailable;
    }

    public float getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(float stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
