package com.troberts.funds4lyfeapp.Accounts;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundsAccount implements Serializable {
    private Map<String,Account> accounts;
    private BigDecimal fundsTotal;

    public FundsAccount(){
        accounts = new HashMap<>();
        accounts.put("ON_HAND",new Account());
        fundsTotal = new BigDecimal(0);
    }

    public void createAccount(String accountType) throws UnsupportedOperationException{
        createAccount(accountType,new BigDecimal(0));
    }

    public void createAccount(String accountType, BigDecimal amount) throws UnsupportedOperationException{
        switch(accountType.toUpperCase()){
            case "CHECKING":
                accounts.put(accountType, new CheckingAccount(amount));
                break;
            case "SAVINGS":
                accounts.put(accountType, new SavingsAccount(amount));
                break;
            default:
                throw new UnsupportedOperationException();
        }
        fundsTotal = fundsTotal.add(amount);

    }

    public void deposit(String accountType, BigDecimal amount){
        accounts.get(accountType).deposit(amount);
        fundsTotal = fundsTotal.add(amount);
    }

    public void withdraw(String accountType, BigDecimal amount) throws UnsupportedOperationException{
        accounts.get(accountType).withdraw(amount);
        fundsTotal = fundsTotal.subtract(amount);
    }

    public void transfer(String accountFrom, String accountTo, BigDecimal amount){
        accounts.get(accountFrom).withdraw(amount);
        accounts.get(accountTo).deposit(amount);
    }



    public boolean containsAccount(String accountType){
        return accounts.containsKey(accountType);
    }

    public BigDecimal getBalance(String accountType) throws UnsupportedOperationException{
        if (!containsAccount(accountType)){
            throw new UnsupportedOperationException();
        }
        return accounts.get(accountType).balance.setScale(2, RoundingMode.HALF_EVEN);


    }

    public BigDecimal getTotal(){
        return fundsTotal.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getBankTotal(){

        return fundsTotal.subtract(getBalance("ON_HAND")).setScale(2, RoundingMode.HALF_EVEN);


    }

    public List<String> getAccountNames(){
        return new ArrayList<>(accounts.keySet());
    }

    public ArrayList<Account> getAccounts(){
        return new ArrayList<>(accounts.values());
    }



    public void refreshTotals(){
        fundsTotal = BigDecimal.ZERO;
        for (Account acc: accounts.values()){
            acc.withdraw(acc.balance.abs());
        }
    }

}
