package com.troberts.funds4lyfeapp.Utilities;

import java.util.Map;

public class WageCalculator {

    Map<String,Integer> result;
    Map<String,Integer> unadjustedResult;

    public WageCalculator(){
    }

    public void calculateResult(int salaryAmount, String unit, int hoursPerWeek, int daysPerWeek, int holidaysPerYear, int vacationDaysPerYear){

        switch(unit){
            case "day":
                salaryAmount /= (hoursPerWeek/daysPerWeek);
                break;
            case "week":
                salaryAmount /= hoursPerWeek;
                break;
            case "bi-week":
                salaryAmount /= hoursPerWeek*2;
                break;
            case "semi-month":
                salaryAmount /= (hoursPerWeek*52)/24;
                break;
            case "month":
                salaryAmount /= (hoursPerWeek*52)/12;
                break;
            case "quarter":
                salaryAmount /= (hoursPerWeek*52)/4;
                break;
            case "annual":
                salaryAmount /= (hoursPerWeek*52);
                break;
        }

        result.put("hourly",salaryAmount);
        result.put("daily",salaryAmount*(hoursPerWeek/daysPerWeek));
        result.put("weekly",salaryAmount*hoursPerWeek);
        result.put("bi-weekly",salaryAmount*hoursPerWeek*2);
        result.put("semi-montly",salaryAmount*(hoursPerWeek*52)/24);
        result.put("monthly",salaryAmount*(hoursPerWeek*52)/12);
        result.put("quarterly",salaryAmount*(hoursPerWeek*52)/4);
        result.put("annually",salaryAmount*(hoursPerWeek*52));

    }



}
