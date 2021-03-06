package com.havish.controller;


import com.havish.modal.Representative;
import java.util.Scanner;

public class PageController {
    private static int loginOption;
    static Scanner sc=new Scanner(System.in);
    private static boolean isAdmin=false,isRep=false;
    private static Representative currentRep;
    private static int choice;

    //Login Home Page
    public static void loginPage(){
        System.out.println("\t\tHavishwaran's Super Market");
        System.out.println("1.Admin\n2.Representative\n3.Exit");
        System.out.println("\nEnter your option");
        loginOption=sc.nextInt();
    }

    public static void home(){
        loginPage();
        verification();
        showOption();
        doOption();
    }
    public static void login(){
        showOption();
        doOption();
    }

    //Verify Admin and Representative Details
    public static int verification(){
        if(loginOption==1){
            System.out.println("Do you want to Continue(y/n)?");
            char ch=sc.next().charAt(0);
            if(ch=='n'){
                return 0;
            }else if(ch=='y'){
                System.out.print("Enter Admin Id : ");
                int id=sc.nextInt();
                System.out.println("Enter Admin Passcode : ");
                String passcode=sc.next();
                isAdmin=Validation.verifyAdmin(id,passcode);
                if(isAdmin){
                    System.out.println("Admin Verification Success....");
                }else{
                    System.out.println("Verification Failed. Incorrect ID or passcode");
                }
            }
        }else if(loginOption==2){
            System.out.println("Do you want to Continue(y/n)?");
            char ch=sc.next().charAt(0);
            if(ch=='n'){
                return 0;
            }else if(ch=='y'){
                System.out.print("Enter Representative Id : ");
                int id=sc.nextInt();
                System.out.println("Enter Representative Passcode : ");
                String passcode=sc.next();
                isRep=Validation.verifyRepresentative(id,passcode);
                if(isRep){
                    System.out.println("Representative Verification Success....");
                }else{
                    System.out.println("Verification Failed. Incorrect ID or passcode");
                }
            }
        }else if(loginOption==3){
            System.exit(0);
        }else{
            System.out.println("Invalid Option.. \nPlease Select Correct option......");
        }
        return 0;
    }

    //Show Admin and Representative Options
    public static int showOption(){
        if(isAdmin){
            System.out.println("Select your option");
            System.out.println("1.Add representative");
            System.out.println("2.Modify Representative Details\n3.View Customer Details" +
                    "\n4.View Representative Details\n5.View Dealer Details\n6.View Bill Details" +
                    "\n7.View Purchase Details\n8.View Particular Details\n9.Exit");
            choice=sc.nextInt();
        }else if(isRep){
            if(currentRep.getRep_type().equals("biller")){
                while(true){
                    int status=BillController.getInstance().AddBill();
                    if(status==1){
                        break;
                    }else{
                        continue;
                    }
                }

            }else{
                System.out.println("Select your option\n1.Product Price Update\n2.Purchase stock" +
                        "\n3.Add product\n4.View Products\n5.View Stock\n6.Exit");
                choice=sc.nextInt();
            }

        }else{
            return 0;
        }
        return 0;
    }

    //Do the selected option
    public static int doOption() {
        if(isAdmin){
            switch (choice){
                case 1:
                    RepresentativeController.getInstance().addRepresentative();
                    login();
                    break;
                case 2:
                    UpdateRep();
                    login();
                    break;
                case 3:
                    CustomerController.getInstance().printCustomerDetails();
                    login();
                    break;
                case 4:
                    RepresentativeController.getInstance().getAllRep();
                    login();
                    break;
                case 5:
                    DealerController.getInstance().DealerDetails();
                    login();
                    break;
                case 6:
                    BillController.getInstance().getBillDetails();
                    login();
                    break;
                case 7:
                    PurchaseController.getInstance().purchaseDetails();
                    login();
                    break;
                case 8:
                    new PageController().showParticularOption();
                    break;
                case 9:
                    isAdmin=false;
                    home();
                    break;
                default:
                    System.out.println("Invalid Option....\nPlease Enter Correct Option....");
                    login();
                    break;
            }
        }else if(isRep){
            switch (choice){
                case 1:
                    StockController.getInstance().updateStockPrice();
                    login();
                    break;
                case 2:
                    PurchaseController.getInstance().AddPurchase();
                    login();
                    break;
                case 3:
                    StockController.getInstance().addStock();
                    login();
                    break;
                case 4:
                    StockController.getInstance().viewStockList();
                    login();
                    break;
                case 5:
                    StockController.getInstance().viewStock();
                    login();
                    break;
                case 6:
                    isRep=false;
                    home();
                    break;
            }
        }else{
            return 0;
        }
        return 0;
    }


    //Show Individual Data Option
    public void showParticularOption(){
        System.out.println("Select Your Option\n1.View Top 5 Customers\n2.View low available Stocks" +
                "\n3.View Particular Bill Details\n4.View Today Bills\n5.View Today Purchase" +
                "\n6.View Top Sale Stocks\n7.View Particular Bills for some period\n8.View Particular Purchase\n9.Exit");
        int ch1=sc.nextInt();
        switch (ch1){
            case 1:
                CustomerController.getInstance().getTopList();
                showParticularOption();
                break;
            case 2:
                StockController.getInstance().viewLowStocks();
                showParticularOption();
                break;
            case 3:
                BillController.getInstance().viewParticularBill();
                showParticularOption();
                break;
            case 4:
                BillController.getInstance().viewTodayBills();
                showParticularOption();
                break;
            case 5:
                PurchaseController.getInstance().viewTodayPurchase();
                showParticularOption();
                break;
            case 6:
                StockController.getInstance().viewTopStocks();
                showParticularOption();
                break;
            case 7:
                BillController.getInstance().particularPeriodBills();
                showParticularOption();
                break;
            case 8:
                PurchaseController.getInstance().particularPurchase();
                showParticularOption();
                break;
            case 9:
                login();
                break;
            default:
                System.out.println("Invalid Option....\nPlease Enter Correct Option....");
                login();
                break;
        }
    }

    //Update Representative Details
    public static void UpdateRep(){
        System.out.println("1.Update Salary\n2.Update Mobile Number\n3.Update Designation\n4.Exit");
        int option=sc.nextInt();
        switch (option){
            case 1:
                RepresentativeController.getInstance().updateSalary();
                break;
            case 2:
                RepresentativeController.getInstance().updatePhoneNo();
                break;
            case 3:
                RepresentativeController.getInstance().updateRepType();
                break;
        }
    }



    //Set current Representative
    public static void setRep(Representative rep){
        currentRep=rep;
    }

    public static Representative getRep(){
        return currentRep;
    }
}
