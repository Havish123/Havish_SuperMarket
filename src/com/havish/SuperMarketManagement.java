package com.havish;
import com.havish.controller.PageController;
import com.havish.dao.SuperMarketDAO;

import java.sql.SQLException;

public class SuperMarketManagement {
    public static void main(String[] args) throws SQLException {
        SuperMarketDAO.getInstance().createTables();
        PageController.home();
    }
}
