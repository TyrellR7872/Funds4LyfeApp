package com.troberts.funds4lyfeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainPage extends AppCompatActivity {
    UserAccount userAccount;
    SharedPreferences prefs;
    final int ACCOUNT = 1;
    final int TRANSACTION = 2;
    final Gson gson = new Gson();
    TextView tvBalancetext, tvBalance;
    ImageView ivBalBackground;
    Button btnDeposit, btnWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String json;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tvBalancetext = findViewById(R.id.tvBalanceText);
        tvBalance = findViewById(R.id.tvBalance);
        ivBalBackground = findViewById(R.id.ivBalBackground);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);


        SpannableString content = new SpannableString(tvBalancetext.getText().toString().trim());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvBalancetext.setText(content);

        tvBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = tvBalance.getText().toString().length();
                switch(len){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        prefs = getSharedPreferences("myprefs",0);

        if (prefs.getString("userAccount","").isEmpty()){
            Intent intent = new Intent(MainPage.this,
                    CreateAccount.class);
            intent.putExtra("userAccount",  gson.toJson(userAccount));
            startActivityForResult(intent, ACCOUNT);
        } else {
            json = prefs.getString("userAccount", "");
            userAccount = gson.fromJson(json, UserAccount.class);
        }

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTransaction("deposit");
            }
        });

        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTransaction("withdraw");
            }
        });


    }

    private void onClickTransaction(String type){
        Intent intent = new Intent(MainPage.this, TransactionActivity.class);
        intent.putExtra("transactionType",type);
        startActivityForResult(intent, TRANSACTION);
    }
    
    private void showBalance(){
        userAccount.createAccount("checking");
        userAccount.deposit("checking",23.33);
        tvBalance.setText(baltoString(userAccount.total)); //TODO: Make for each currency
    }

    private String baltoString(Double d){
        DecimalFormat df = new DecimalFormat("00");
        StringBuilder sb = new StringBuilder();
        String bal = Integer.toString((int)Math.round(d));
        char[] chs =  bal.toCharArray();

        int i = bal.length();
        sb.append('$');
        for (Character ch: chs){
            sb.append(ch);
            if(--i%3==0 && i != 0){
                sb.append(',');
            }
        }

        int decimal = (int)((d % Math.round(d))*100)+1;
        sb.append('.'+df.format(decimal));
        return sb.toString();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACCOUNT){
            if (resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                String json = extras.getString("userAccount");
                userAccount = gson.fromJson(json,UserAccount.class);
                Toast.makeText(MainPage.this,"User Account Updated successfully",Toast.LENGTH_SHORT).show();
                savePreferences();
            } else if (resultCode ==  RESULT_CANCELED){
                Toast.makeText(MainPage.this, "No results received",Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == TRANSACTION){
            if (resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                String type = extras.getString("transactionType");
                String accountType = extras.getString("accountType");
                double amount = extras.getDouble("transactionAmount");
                if (type.equals("deposit")){
                    userAccount.deposit(accountType, amount);
                } else{
                    userAccount.withdraw(accountType, amount);
                }
            } else if (resultCode ==  RESULT_CANCELED){
                Toast.makeText(MainPage.this, "No results received",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePreferences(){
        prefs = getSharedPreferences("myprefs",0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString("userAccount",gson.toJson(userAccount));
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showBalance();
        Toast.makeText(MainPage.this,baltoString(userAccount.total),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }
}
