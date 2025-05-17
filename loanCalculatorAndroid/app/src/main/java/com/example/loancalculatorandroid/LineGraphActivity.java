package com.example.loancalculatorandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LineGraphActivity extends GraphActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_screen);

        // Initialize fields from with data from MainActivity
        initData(getIntent());
        initLoanData();
        initGraphData(loanData);
        initTableData(loanData);
    }

    @Override
    protected void initLoanData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int period = years * 12 + months;
                double credit = loanAmount / period;
                double loanAmountCopy = loanAmount;
                double interestFee;

                for(int i = 1; i <= period + (postEnd - postStart); i++){
                    if(i <= postStart || i >= postEnd) {
                        interestFee = (loanAmountCopy * interest) / 12;
                        loanData.add(new LoanData(i, loanAmountCopy, credit + interestFee, interestFee, credit));
                        loanAmountCopy -= credit;
                    }
                    else {
                        interestFee = (loanAmountCopy * postInterest) / 12;
                        loanData.add(new LoanData(i, loanAmountCopy, interestFee, interestFee, 0));
                    }
                }
            }
        });

        thread.start();

        try {
            thread.join(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
