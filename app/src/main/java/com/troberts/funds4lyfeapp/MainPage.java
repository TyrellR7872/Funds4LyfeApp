package com.troberts.funds4lyfeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.troberts.funds4lyfeapp.Accounts.UserAccount;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MainPage extends AppCompatActivity {
    UserAccount userAccount;
    SharedPreferences prefs;


    final Gson gson = new Gson();

    TextView tvBalancetext, tvBalance;

    ImageView ivBalBackground;
    Button btnDeposit, btnWithdraw, btnTransfer, btnRefresh;

    final int ACCOUNT = 1;
    final int TRANSACTION = 2;
    final int TRANSFER = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tvBalancetext = findViewById(R.id.tvBalanceText);
        tvBalance = findViewById(R.id.tvBalance);
        ivBalBackground = findViewById(R.id.ivBalBackground);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnRefresh = findViewById(R.id.btnRefresh);
        tvBalance.setSelected(true);



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

        openingProcedure();






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

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAccount.getAccountNames().size() < 2){
                    Toast.makeText(MainPage.this, "Please make sure you have at least 2 accounts", Toast.LENGTH_SHORT).show();
                    userAccount.createAccount("CHECKING",50);
                    showSaveBalance();
                }else {
                    Intent intent = new Intent(MainPage.this, TransferActivity.class);
                    intent.putExtra("userAccount",gson.toJson(userAccount));
                    startActivityForResult(intent, TRANSFER);
                }

            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccount.refreshTotals();
                showSaveBalance();
            }
        });

    }

    private void onClickTransaction(String type){
        Intent intent = new Intent(MainPage.this, TransactionActivity.class);
        intent.putExtra("userAccount",gson.toJson(userAccount));
        intent.putExtra("transactionType",type);
        startActivityForResult(intent, TRANSACTION);
    }
    
    private void showSaveBalance(){
        tvBalance.setText(baltoString(userAccount.getTotal())); //TODO: Make for each currency
        savePreferences();
    }

    private String baltoString(BigDecimal d){
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
        usdCostFormat.setMinimumFractionDigits( 2 );
        usdCostFormat.setMaximumFractionDigits( 2 );
        return usdCostFormat.format(d);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case ACCOUNT:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    String userAccountJson = extras.getString("userAccount");
                    userAccount = gson.fromJson(userAccountJson, UserAccount.class);
                    Toast.makeText(MainPage.this, "User Account Updated successfully", Toast.LENGTH_SHORT).show();
                    showSaveBalance();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(MainPage.this, "No results received", Toast.LENGTH_SHORT).show();
                }
                break;
            case TRANSACTION:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    String type = extras.getString("transactionType");
                    String accountType = extras.getString("accountType");
                    double amount = extras.getDouble("transactionAmount");
                    if (type.equals("deposit")) {
                        userAccount.deposit(accountType, amount);
                    } else {
                        userAccount.withdraw(accountType, amount);
                    }
                    showSaveBalance();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(MainPage.this, "No results received", Toast.LENGTH_SHORT).show();
                }

                break;
            case TRANSFER:
                if (resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    String accFrom = extras.getString("accFrom");
                    String accTo = extras.getString("accTo");
                    double amount = extras.getDouble("transactionAmount");
                    userAccount.transfer(accFrom,accTo,amount);
                    showSaveBalance();
                }  else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(MainPage.this, "No results received", Toast.LENGTH_SHORT).show();
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

    public void openingProcedure() {
        prefs = getSharedPreferences("myprefs", 0);
        if (prefs.getString("userAccount", "").isEmpty()) {
            Intent intent = new Intent(MainPage.this,
                    CreateAccount.class);
            intent.putExtra("userAccount", gson.toJson(userAccount));
            startActivityForResult(intent, ACCOUNT);
        } else {
            String userAccountJson = prefs.getString("userAccount", "");
            userAccount = gson.fromJson(userAccountJson, UserAccount.class);

            showSaveBalance();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuView) {
            startActivity(new Intent(MainPage.this,ViewUser.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }




}
