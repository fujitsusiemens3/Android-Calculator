package com.jakubowski.rafal.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdvancedActivity extends SimpleActivity {

    private Button btn_sin, btn_cos, btn_tan, btn_ln, btn_percent;
    private Button btn_sqrt, btn_pow_x, btn_pow_x_y, btn_log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        getSupportActionBar().hide();

        initializeCommonButtons();
        initializeAdvancedButton();

        restoreData(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        selectBaseAction(view.getId());
        selectAdvancedAction(view.getId());

        updateScreen();
    }

    private void initializeAdvancedButton() {
        btn_sin = findViewById(R.id.btn_sin);
        btn_sin.setOnClickListener(this);
        btn_cos = findViewById(R.id.btn_cos);
        btn_cos.setOnClickListener(this);
        btn_tan = findViewById(R.id.btn_tan);
        btn_tan.setOnClickListener(this);
        btn_ln = findViewById(R.id.btn_ln);
        btn_ln.setOnClickListener(this);
        btn_sqrt = findViewById(R.id.btn_sqrt);
        btn_sqrt.setOnClickListener(this);
        btn_pow_x = findViewById(R.id.btn_pow_x);
        btn_pow_x.setOnClickListener(this);
        btn_pow_x_y = findViewById(R.id.btn_pow_x_y);
        btn_pow_x_y.setOnClickListener(this);
        btn_log = findViewById(R.id.btn_log);
        btn_log.setOnClickListener(this);
        btn_percent = findViewById(R.id.btn_percent);
        btn_percent.setOnClickListener(this);
    }


    private void selectAdvancedAction(int button) {

        switch (button) {

            case R.id.btn_sin:
                addFunctionIfPossible("sin(");
                setLastClickedButton(R.id.btn_sin);
                break;


            case R.id.btn_cos:
                addFunctionIfPossible("cos(");
                setLastClickedButton(R.id.btn_cos);
                break;

            case R.id.btn_tan:
                addFunctionIfPossible("tan(");
                setLastClickedButton(R.id.btn_tan);
                break;

            case R.id.btn_ln:
                addFunctionIfPossible("ln(");
                setLastClickedButton(R.id.btn_ln);
                break;

            case R.id.btn_sqrt:
                addFunctionIfPossible("sqrt(");
                setLastClickedButton(R.id.btn_sqrt);
                break;

            case R.id.btn_pow_x:
                addOperationIfPossible(getLastClickedButton(), "^2");
                setLastClickedButton(R.id.btn_pow_x);
                break;

            case R.id.btn_pow_x_y:
                addOperationIfPossible(getLastClickedButton(), "^");
                setLastClickedButton(R.id.btn_pow_x_y);
                break;

            case R.id.btn_log:
                addFunctionIfPossible("log(");
                setLastClickedButton(R.id.btn_log);
                break;

            case R.id.btn_percent:
                if (!getStringResultBuilder().toString().isEmpty() &&
                    getLastClickedButton() != R.id.btn_percent &&
                        Character.isDigit(getStringResultBuilder().toString().
                                charAt(getStringResultBuilder().toString().length()-1))) {

                    appendStringResultBuilder("%");
                } else {
                    Toast.makeText(this,"Operation not permitted",Toast.LENGTH_SHORT).show();
                }

                setLastClickedButton(R.id.btn_percent);
                break;
        }
    }

    private boolean isNumberBefore() {
        int editTextLength = getStringResultBuilder().length();

        if (editTextLength != 0) {
            if (InfixToPostfix.isNumber( getStringResultBuilder().substring(editTextLength-1) ))
                return true;
        }
        return false;
    }

    private void addFunctionIfPossible(String func) {
        if (isNumberBefore())
            Toast.makeText(this,"Operation not permitted",Toast.LENGTH_SHORT).show();
        else
            appendStringResultBuilder(func);
    }

}
