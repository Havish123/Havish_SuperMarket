package com.havish.controller;

import com.havish.dao.SuperMarketDAO;
import com.havish.modal.Representative;

import java.sql.SQLException;

public class Validation {
    //Verify Admin Details
    public static boolean verifyAdmin(int id,String passcode){
        if(id==1){
            if(passcode.equals("1234")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //Verify Representative Details
    public static boolean verifyRepresentative(int id,String passcode)  {
        try{
            Representative rep= SuperMarketDAO.getInstance().getRepresentative(id);
            if(id==rep.getRep_id()){
                if(passcode.equals(rep.getPasscode())){
                    PageController.setRep(rep);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return true;
    }

}
