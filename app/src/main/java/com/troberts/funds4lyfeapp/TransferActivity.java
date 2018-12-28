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

public class TransferActivity extends AppCompatActivity {

    EditText etAmount;
    TextView tvCurrBalance,tvCurrBalance2;
    Button btnTComplete;
    Spinner spinType, spinType2;
    List<String> spinArr;
    UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        spinType = findViewById(R.id.spinType);
        spinType2 = findViewById(R.id.spinType2);
        btnTComplete = findViewById(R.id.btnTComplete);
        etAmount = findViewById(R.id.etAmount);
        tvCurrBalance = findViewById(R.id.tvCurrBalance);
        tvCurrBalance2 = findViewById(R.id.tvCurrBalance2);

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
        spinType2.setAdapter(adapter);

        btnTComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAmount.getText().toString().isEmpty() || spinType.getSelectedItem().toString().matches(" ") ||
                        spinType2.getSelectedItem().toString().matches(" ")) {
                    Toast.makeText(TransferActivity.this,"Please complete all fields",Toast.LENGTH_SHORT).show();
                } else {
                    String accFrom = spinType.getSelectedItem().toString().trim();
                    String accTo = spinType2.getSelectedItem().toString().trim();



                    if (accFrom.equals(accTo)){
                        Toast.makeText(TransferActivity.this,"You cannot transfer from the same account",Toast.LENGTH_SHORT).show();
                    } else{
                        double amount = Double.parseDouble(etAmount.getText().toString());

                        if (amount > userAccount.getBalance(accFrom).doubleValue()){
                            Toast.makeText(TransferActivity.this,"You don't have enough to transfer",Toast.LENGTH_SHORT).show();
                        } else {

                            getIntent().putExtra("accFrom", accFrom);
                            getIntent().putExtra("accTo",accTo);
                            getIntent().putExtra("transactionAmount", amount);

                            setResult(RESULT_OK, getIntent());
                            TransferActivity.this.finish();
                        }
                    }

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSpinnerListener(spinType,tvCurrBalance);
        setSpinnerListener(spinType2,tvCurrBalance2);

    }

    private void setSpinnerListener(Spinner spin, final TextView displayBalance){
        final Spinner spinner = spin;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected =  spinner.getSelectedItem().toString();
                if(!selected.matches(" ")) {
                    displayBalance.setText(String.format("%s: $%s", selected.trim(), userAccount.getBalance(selected)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}


