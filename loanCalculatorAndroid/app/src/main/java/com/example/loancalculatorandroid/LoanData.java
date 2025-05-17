package com.example.loancalculatorandroid;

public class LoanData {
    private final int month;
    private final double loanAmount;
    private final double monthlyPayment;
    private final double interest;
    private final double credit;

    public LoanData(int month, double loanAmount, double monthlyPayment, double interest, double credit) {
        this.month = month;
        this.loanAmount = loanAmount;
        this.monthlyPayment = monthlyPayment;
        this.interest = interest;
        this.credit = credit;
    }


    public int getMonth() {
        return month;
    }

    public double getLoanAmount() {
        return Math.round(loanAmount * 100.0) / 100.0;
    }

    public double getMonthlyPayment() {
        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

    public double getInterest() {
        return Math.round(interest * 100.0) / 100.0;
    }

    public double getCredit() {
        return Math.round(credit * 100.0) / 100.0;
    }
}
