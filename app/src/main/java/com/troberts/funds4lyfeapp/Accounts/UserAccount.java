package com.troberts.funds4lyfeapp.Accounts;

import java.math.BigDecimal;
import java.util.Date;


public class UserAccount extends FundsAccount {
    private String firstName;
    private String lastName;
    private Date userBirthday;


    public UserAccount(String firstName, String lastName, Date birthday){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userBirthday = birthday;
    }

    public String toString(){
        return "First Name: "+firstName+"\n"
                +"Last Name: "+lastName+"\n"
                +"Birthday: "+userBirthday+"\n"
                +"Total Balance: "+ getTotal();
    }
    public void createAccount(String accType, double amount){
        super.createAccount(accType,new BigDecimal(amount));
    }

    public void deposit(String accType, double amount){
        super.deposit(accType,new BigDecimal(amount));
    }

    public void withdraw(String accType, double amount){
        super.withdraw(accType,new BigDecimal(amount));
    }

    public void transfer(String accFrom, String accTo, double amount){
        super.transfer(accFrom,accTo,new BigDecimal(amount));
    }


    public double getTotalDouble() {
        return super.getTotal().doubleValue();
    }


    public double getBankTotalDouble() {
        return super.getBankTotal().doubleValue();
    }
}
