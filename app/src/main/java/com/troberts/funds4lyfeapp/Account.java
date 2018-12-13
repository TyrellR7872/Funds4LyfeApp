package com.troberts.funds4lyfeapp;

public class Account {
    double balance = 0;

    public Account(){
    }

    public void deposit(double amount){
        balance += amount;
    }
    public boolean withdraw(double amount){
        if (amount > balance){
            return false;
        }
        balance -= amount;
        return true;
    }


}

class SavingsAccount extends Account{
    double interestRate;

    public SavingsAccount() {
        interestRate = 0.0085;
    }

    public void addInterest(){
        balance += (interestRate*balance);
    }

}

class CheckingAccount extends Account{
    double interestRate;

    public CheckingAccount() {

    }


}