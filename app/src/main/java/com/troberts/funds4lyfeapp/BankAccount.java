package com.troberts.funds4lyfeapp;

import java.util.HashMap;
import java.util.Map;

public class BankAccount {
    private Map<String,Account> accounts;
    public double total;

    public BankAccount(){
        accounts = new HashMap<>();
    }

    public void deposit(String accountType, double amount){
        accounts.get(accountType).deposit(amount);
        total += amount;
    }

    public boolean withdraw(String accountType, double amount) throws UnsupportedOperationException{
        if(!accounts.get(accountType).withdraw(amount)){
            return false;
        }
        total -= amount;
        return true;
    }

    public void createAccount(String accountType) throws UnsupportedOperationException{
        switch(accountType){
            case "checking":
                accounts.put(accountType, new CheckingAccount());
                break;
            case "savings":
                accounts.put(accountType, new SavingsAccount());
                break;
            default:
                throw new UnsupportedOperationException();
        }

    }

    public boolean containsAccount(String accountType){
        return accounts.containsKey(accountType);
    }

    public double displayBalance(String accountType) throws UnsupportedOperationException{
        if (!containsAccount(accountType)){
            throw new UnsupportedOperationException();
        }
        return accounts.get(accountType).balance;

    }
}
