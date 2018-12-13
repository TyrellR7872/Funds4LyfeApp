package com.troberts.funds4lyfeapp;

import java.util.Date;


public class UserAccount extends BankAccount{
    private String firstName;
    private String lastName;
    private Date userBirthday;


    public UserAccount(String firstName, String lastName, Date birthday){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userBirthday = birthday;
    }



}
