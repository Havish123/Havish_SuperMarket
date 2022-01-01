package com.havish.modal;

public class Biller extends Representative{


    public Biller(){

    }

    @Override
    public int getTotal_sales() {
        return super.getTotal_sales();
    }

    @Override
    public void setTotal_sales(int total_sales) {
        super.setTotal_sales(super.getTotal_sales()+total_sales);
    }

    @Override
    public float getTotal_amount() {
        return super.getTotal_amount();
    }

    @Override
    public void setTotal_amount(float total_amount) {
        super.setTotal_amount(super.getTotal_amount()+total_amount);
    }
}
