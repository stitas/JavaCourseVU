package com.example.loancalculatorandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GraphActivity extends AppCompatActivity {
    protected LineChart graph;

    protected TableLayout table;

    protected EditText filterEndField, filterStartField;

    protected Button newCalculationBtn;

    protected double loanAmount;
    protected double interest;
    protected double postInterest;

    protected int years;
    protected int months;
    protected int postStart;
    protected int postEnd;

    protected List<LoanData> loanData;

    protected void initData(Intent intent) {
        graph = findViewById(R.id.lineChart);
        table = findViewById(R.id.table);
        filterEndField = findViewById(R.id.filterEndField);
        filterStartField = findViewById(R.id.filterStartField);
        newCalculationBtn = findViewById(R.id.newCalculationBtn);

        loanAmount = intent.getDoubleExtra("loanAmount", 0);
        years = intent.getIntExtra("years", 0);
        months = intent.getIntExtra("months", 0);
        interest = intent.getDoubleExtra("interest", 0) / 100;
        postStart = intent.getIntExtra("postStart", 0);
        postEnd = intent.getIntExtra("postEnd", 0);
        postInterest = intent.getDoubleExtra("postInterest", 0) / 100;

        Intent calculateAgainIntent = new Intent(this, MainActivity.class);

        loanData = new ArrayList<>();

        filterStartField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                filterDataByMonth(loanData.size());
            }
        });

        filterEndField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                filterDataByMonth(loanData.size());
            }
        });

        newCalculationBtn.setOnClickListener(view -> startActivity(calculateAgainIntent));
    }

    protected void initGraphData(List<LoanData> loanData) {
        List<Entry> entries = new ArrayList<>();

        for(LoanData data : loanData) {
            entries.add(new Entry(data.getMonth(), (float) data.getLoanAmount()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Loan Balance");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        graph.setData(lineData);
        graph.getDescription().setEnabled(false);
        graph.animateX(1000);
        graph.invalidate();
    }

    protected void initTableData(List<LoanData> loanData) {
        // Set table header
        TableRow header = new TableRow(this);
        String[] headers = {"Month", "Loan amount", "Monthly Payment", "Interest", "Credit"};

        for (String title : headers) {
            TextView tv = new TextView(this);
            tv.setText(title);
            tv.setPadding(16, 16, 16, 16);
            header.addView(tv);
        }

        table.addView(header);

        for(LoanData data : loanData){
            TableRow row = new TableRow(this);

            String[] dataArr = {
                    String.valueOf(data.getMonth()),
                    String.valueOf(data.getLoanAmount()),
                    String.valueOf(data.getMonthlyPayment()),
                    String.valueOf(data.getInterest()),
                    String.valueOf(data.getCredit())
            };

            for (String cell : dataArr) {
                TextView tv = new TextView(this);
                tv.setText(cell);
                tv.setPadding(16, 16, 16, 16);
                row.addView(tv);
            }

            table.addView(row);
        }
    }

    protected void filterDataByMonth(int period) {
        Thread thread = new Thread(() -> {
            int lowerMonthBound;
            int upperMonthBound;

            try {
                lowerMonthBound = filterStartField.getText().toString().trim().isEmpty() ||
                        Integer.parseInt(filterStartField.getText().toString().trim()) < 0 ? 1 :
                        Integer.parseInt(filterStartField.getText().toString().trim());

                upperMonthBound = filterEndField.getText().toString().trim().isEmpty() ||
                        Integer.parseInt(filterEndField.getText().toString().trim()) < 0 ? period :
                        Integer.parseInt(filterEndField.getText().toString().trim());
            }
            catch (NumberFormatException e) {
                return;
            }

            List<LoanData> filteredLoanData = loanData.stream()
                    .filter(data -> data.getMonth() >= lowerMonthBound && data.getMonth() <= upperMonthBound)
                    .collect(Collectors.toList());

            runOnUiThread(() -> {
                table.removeAllViews();
                initGraphData(filteredLoanData);
                initTableData(filteredLoanData);
            });
        });

        thread.start();
    }

    protected abstract void initLoanData();
}
