package com.troberts.funds4lyfeapp.Utilities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.troberts.funds4lyfeapp.R;

public class SalaryCalculator extends AppCompatActivity {
    WageCalculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_calculator);
    }
}
