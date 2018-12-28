package com.troberts.funds4lyfeapp;

import com.troberts.funds4lyfeapp.Accounts.UserAccount;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AccountTests {

    final String ONHAND = "ON_HAND";
    final String CHECKING = "CHECKING";


    @Test
    public void accountContainsAmount(){
        UserAccount userAccount = new UserAccount("Tyrell","Roberts",new Date(1997,12,11));
        assertTrue(userAccount.containsAccount(ONHAND));
        assertFalse(userAccount.containsAccount(CHECKING));

        userAccount.deposit(ONHAND,50);
        assertTrue(userAccount.getTotalDouble() == 50);
        assertTrue(userAccount.getBankTotalDouble() == 0);
        assertTrue(userAccount.getTotalDouble() == 50);

        userAccount.createAccount(CHECKING,32);
        assertTrue(userAccount.getBalance(CHECKING).doubleValue()==32);
        assertTrue(userAccount.getBankTotalDouble() == 32);
        assertTrue(userAccount.getTotalDouble() == 82);

        userAccount.transfer(ONHAND,CHECKING,5);
        assertTrue(userAccount.getTotalDouble() == 82);
        userAccount.transfer(CHECKING,ONHAND,10);
        assertTrue(userAccount.getTotalDouble() == 82);
        assertTrue(userAccount.getBalance(ONHAND).doubleValue()==55);
        assertTrue(userAccount.getBalance(CHECKING).doubleValue()==27);



    }

}