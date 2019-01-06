package com.troberts.funds4lyfeapp.Accounts;

import java.math.BigDecimal;

public class Account {
    BigDecimal balance;

    public Account(){
        balance = new BigDecimal(0);
    }

    public Account(BigDecimal startBal){
        balance = startBal;
    }

    public void deposit(BigDecimal amount){

        balance = balance.add(amount);
    }
    public void withdraw(BigDecimal amount){
        if (amount.compareTo(balance) != 1){
            balance = balance.subtract(amount);
        }
    }

    public BigDecimal getBalance(){
        return balance;
    }




}

class SavingsAccount extends Account{
    BigDecimal interestRate = new BigDecimal(0.0085);

    public SavingsAccount() {
    }

    public SavingsAccount(BigDecimal startBal) {
        super(startBal);
    }

    public void addInterest(){
        balance=balance.add((interestRate.multiply(balance)));
    }

    public void setInterestRate(BigDecimal interestRate){
        this.interestRate = interestRate;
    }

}

class CheckingAccount extends Account{

    public CheckingAccount() {

    }

    public CheckingAccount(BigDecimal startBal){
        super(startBal);
    }


}