package com.havish;
import com.havish.controller.PageController;
import com.havish.dao.SuperMarketDAO;

import java.sql.SQLException;

public class SuperMarketManagement {
    public static void main(String[] args) {
        try{
            SuperMarketDAO.getInstance().createTables();
        }catch (SQLException|NullPointerException e){

        }

        PageController.home();
    }
}
