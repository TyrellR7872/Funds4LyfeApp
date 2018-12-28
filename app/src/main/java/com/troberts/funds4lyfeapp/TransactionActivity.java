package com.troberts.funds4lyfeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.troberts.funds4lyfeapp.Accounts.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    EditText etAmount;
    TextView tvCurrBalance;
    Button btnTComplete;
    Spinner spinType;
    List<String> spinArr;
    UserAccount userAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        spinType = findViewById(R.id.spinType);
        btnTComplete = findViewById(R.id.btnTComplete);
        etAmount = findViewById(R.id.etAmount);
        tvCurrBalance = findViewById(R.id.tvCurrBalance);

        Gson gson = new Gson();
        final String userAccountJson = getIntent().getStringExtra("userAccount");
        userAccount = gson.fromJson(userAccountJson,UserAccount.class);

        spinArr = new ArrayList<>();
        spinArr.add(" ");
        spinArr.addAll(userAccount.getAccountNames());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, spinArr);


        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);



        spinType.setAdapter(adapter);



        btnTComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAmount.getText().toString().isEmpty() || spinType.getSelectedItem().toString().matches(" ")) {
                    Toast.makeText(TransactionActivity.this,"Please complete all fields",Toast.LENGTH_SHORT).show();
                } else {
                    String selected = spinType.getSelectedItem().toString().trim();
                    String tType = getIntent().getStringExtra("transactionType");

                    double amount = Double.parseDouble(etAmount.getText().toString());
                    if (amount > userAccount.getBalance(selected).doubleValue() && tType.equals("withdraw")){
                        Toast.makeText(TransactionActivity.this,"You cannot withdraw that much money",Toast.LENGTH_SHORT).show();
                    } else {

                        getIntent().putExtra("accountType", selected);
                        getIntent().putExtra("transactionAmount", amount);

                        setResult(RESULT_OK, getIntent());
                        TransactionActivity.this.finish();
                    }
                }
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        setSpinnerListener();
    }

    private void setSpinnerListener(){
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = spinType.getSelectedItem().toString();
                if(!selected.matches(" ")) {
                    tvCurrBalance.setText(String.format("%s: $%s", selected.trim(), userAccount.getBalance(selected)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}