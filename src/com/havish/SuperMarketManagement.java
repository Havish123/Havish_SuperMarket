package com.havish;
import com.havish.controller.PageController;
import com.havish.dao.SuperMarketDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public class SuperMarketManagement {
    public static void main(String[] args) throws SQLException {
        SuperMarketDAO.getInstance().createTables();
        PageController.loginPage();
        PageController.verification();
        PageController.showOption();
        PageController.doOption();
    }
}
