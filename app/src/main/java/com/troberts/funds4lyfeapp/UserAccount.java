package com.troberts.funds4lyfeapp;

import java.util.Date;

public abstract class UserAccount {
    private String firstName;
    private String lastName;
    private Date userBirthday;
    public int total;

    public UserAccount(String firstName, String lastName, Date birthday){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userBirthday = birthday;
        this.total = 0;
    }

}
