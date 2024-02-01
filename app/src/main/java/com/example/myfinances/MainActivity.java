package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton cd, loan, checking;
    private EditText accountNumber, initialBalance,
            currentBalance, interestRate, paymentAmount;
    private Button saveBtn, cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.rad_grp);
        cd = findViewById(R.id.rad_cd);
        loan = findViewById(R.id.rad_loan);
        checking = findViewById(R.id.rad_account);
        accountNumber = findViewById(R.id.acc_num);
        initialBalance = findViewById(R.id.init_bal);
        currentBalance = findViewById(R.id.cur_bal);
        interestRate = findViewById(R.id.int_rate);
        paymentAmount = findViewById(R.id.payment_amnt);
        saveBtn = findViewById(R.id.save_button);
        cancelBtn = findViewById(R.id.cancel_btn);
        activateRadioButtons();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                String accNumVal = String.valueOf(accountNumber.getText());
                String curBalVal = String.valueOf(currentBalance.getText());
                String initBalVal = !initialBalance.isEnabled() ? "0" :
                        String.valueOf(initialBalance.getText());
                String intRateVal = !interestRate.isEnabled() ? "0" :
                        String.valueOf(interestRate.getText());
                String payAmntVal = !paymentAmount.isEnabled() ? "0" :
                        String.valueOf(paymentAmount.getText());
                String type = cd.isChecked() ? String.valueOf(cd.getText())
                        : loan.isChecked() ? String.valueOf(loan.getText())
                        : String.valueOf(checking.getText());
                if (Float.parseFloat(intRateVal) < 0 || Float.parseFloat(intRateVal) > 100) {
                    Toast.makeText(MainActivity.this, "Invalid interest rate",
                            Toast.LENGTH_SHORT).show();
                } else if (accountNumber.isEnabled() && isValid(accountNumber, initialBalance,
                        currentBalance, interestRate, paymentAmount)) {
                    float a = Float.parseFloat(initBalVal);
                    float b = Float.parseFloat(curBalVal);
                    float c = Float.parseFloat(intRateVal);
                    float d = Float.parseFloat(payAmntVal);
                    db.addToDb(type, accNumVal, a, b, c, d);
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all required fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isValid(EditText acc, EditText iBal, EditText cBal, EditText rate, EditText
            amnt) {
        if (acc.isEnabled() && acc.getText().length() < 1) return false;
        else if (iBal.isEnabled() && iBal.getText().length() < 1) return false;
        else if (cBal.isEnabled() && cBal.getText().length() < 1) return false;
        else if (rate.isEnabled() && rate.getText().length() < 1) return false;
        else return !amnt.isEnabled() || amnt.getText().length() >= 1;
    }
    private void activateRadioButtons() {
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // %)");
            }

        });
        enableEditText(interestRate, "Enter interest rate (0 - 100" );
        enableEditText(initialBalance, "Enter initial balance");
        enableEditText(currentBalance, "Enter current balance");
        enableEditText(accountNumber, "Enter account number");
        disableEditText(paymentAmount, "Payment");
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditText(paymentAmount, "Enter payment amount");
                enableEditText(initialBalance, "Enter initial balance");
                enableEditText(currentBalance, "Enter current balance");
                enableEditText(accountNumber, "Enter account number");
                disableEditText(interestRate, "Interest rate");
            }
        });
        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEditText(paymentAmount, "Payment");
                disableEditText(interestRate, "Interest rate");
                disableEditText(initialBalance, "Initial balance");
                enableEditText(currentBalance, "Enter current balance");
                enableEditText(accountNumber, "Enter account number");
            }
        });
    }
    private void disableEditText(EditText eText, String type) {
        eText.setHint(type + " - Disabled");

        eText.setEnabled(false);
    }
    private void enableEditText(EditText eText, String hint) {
        eText.setHint(hint);
        eText.setEnabled(true);
    }
}
