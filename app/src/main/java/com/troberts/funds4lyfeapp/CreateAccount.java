package com.troberts.funds4lyfeapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.troberts.funds4lyfeapp.Accounts.UserAccount;

import java.util.Calendar;
import java.util.Date;

public class CreateAccount extends AppCompatActivity {
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    EditText etFirst, etLast;
    Button btnSubmit;
    private UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final Gson gson = new Gson();
        Bundle extras = getIntent().getExtras();
        String json = extras.getString("userAccount");

        userAccount = gson.fromJson(json,UserAccount.class);

        dateView = findViewById(R.id.tvBirthday);
        btnSubmit = findViewById(R.id.btnSubmit);
        etFirst = findViewById(R.id.etFirst);
        etLast = findViewById(R.id.etLast);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFirst.getText().toString().isEmpty() || etLast.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter all fields",Toast.LENGTH_SHORT).show();
                } else{
                    userAccount = new UserAccount(etFirst.getText().toString().trim(),etLast.getText().toString().trim(),new Date(year,month,day));
                    String json = gson.toJson(userAccount);

                    getIntent().putExtra("userAccount", json);

                    setResult(RESULT_OK,getIntent());
                    CreateAccount.this.finish();


                }
            }
        });

    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        DatePickerDialog dpDialog = new DatePickerDialog(this,
                AlertDialog.THEME_HOLO_DARK,myDateListener,year,month,day);
            return dpDialog;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
}
