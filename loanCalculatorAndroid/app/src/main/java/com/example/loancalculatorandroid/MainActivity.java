package com.example.loancalculatorandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText loanAmountField;
    private EditText yearField;
    private EditText monthsField;
    private EditText interestField;
    private EditText postStartField;
    private EditText postEndField;
    private EditText postInterestField;

    private RadioButton lineGraphRadio, annuityGraphRadio;

    private TextView errorText;

    private LinearLayout mainContentLinearLayout;
    private LinearLayout spinnerLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loanAmountField = findViewById(R.id.loanAmountField);
        yearField = findViewById(R.id.yearField);
        monthsField = findViewById(R.id.monthsField);
        interestField = findViewById(R.id.interestField);
        postStartField = findViewById(R.id.postStartField);
        postEndField = findViewById(R.id.postEndField);
        postInterestField = findViewById(R.id.postInterestField);

        lineGraphRadio = findViewById(R.id.lineGraphRadio);
        annuityGraphRadio = findViewById(R.id.annuityGraphRadio);

        errorText = findViewById(R.id.errorText);

        mainContentLinearLayout = findViewById(R.id.mainContentLinearLayout);
        spinnerLinearLayout = findViewById(R.id.spinnerLinearLayout);

        Button calculateBtn = findViewById(R.id.calculateBtn);

        calculateBtn.setOnClickListener(view -> {
            onCalculateBtnClick();
        });
    }

    public void onCalculateBtnClick() {
        Intent intent;
        int postStart;
        int postEnd;
        double postInterest;

        if(lineGraphRadio.isChecked()) {
            intent = new Intent(MainActivity.this, LineGraphActivity.class);
        }
        else if(annuityGraphRadio.isChecked()) {
            intent = new Intent(MainActivity.this, AnnuityGraphActivity.class);
        }
        else {
            errorText.setVisibility(View.VISIBLE);
            return;
        }

        try {
            postStart = Integer.parseInt(postStartField.getText().toString());
            postEnd = Integer.parseInt(postEndField.getText().toString());
            postInterest = Double.parseDouble(postInterestField.getText().toString());

            if(postStart > postEnd){
                errorText.setVisibility(View.VISIBLE);
                return;
            }
        }
        catch (NumberFormatException e) {
            postStart = 0;
            postEnd = 0;
            postInterest = 0;
        }

        try {
            intent.putExtra("loanAmount", Double.parseDouble(loanAmountField.getText().toString()));
            intent.putExtra("years", Integer.parseInt(yearField.getText().toString()));
            intent.putExtra("months", Integer.parseInt(monthsField.getText().toString()));
            intent.putExtra("interest", Double.parseDouble(interestField.getText().toString()));
            intent.putExtra("postStart", postStart);
            intent.putExtra("postEnd", postEnd);
            intent.putExtra("postInterest", postInterest);
        }
        catch (NumberFormatException e) {
            errorText.setVisibility(View.VISIBLE);
            return;
        }

        mainContentLinearLayout.setVisibility(View.GONE);
        spinnerLinearLayout.setVisibility(View.VISIBLE);

        startActivity(intent);
    }

}