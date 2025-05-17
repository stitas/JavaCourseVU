package com.example.loancalculatorandroid;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AnnuityGraphActivity extends GraphActivity{
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
                double loanAmountCopy = loanAmount;
                double interestFee = loanAmountCopy * (interest / 12);
                double monthlyPayment = (interestFee / (1 - Math.pow(1 + interest / 12, -1 * period)));
                double credit;

                for(int i = 1; i <= period + (postEnd - postStart); i++){
                    if(i <= postStart || i >= postEnd) {
                        interestFee = loanAmountCopy * (interest / 12);
                        credit = monthlyPayment - interestFee;
                        loanData.add(new LoanData(i, loanAmountCopy, monthlyPayment, interestFee, credit));
                        loanAmountCopy -= credit;
                    }
                    else {
                        interestFee = loanAmountCopy * (postInterest / 12);
                        loanData.add(new LoanData(i, loanAmountCopy, interestFee, interestFee, 0));
                    }
                }
            }
        });

        thread.start();
    }
}
