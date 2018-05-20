package com.jakubowski.rafal.calculator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rafal on 20.03.18.
 */

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;
    private Button btn_c, btn_ac, btn_dot, btn_bksp, btn_sign, btn_result;
    private Button btn_add, btn_divide, btn_subtract, btn_multiply;

    private EditText resultBox;
    private StringBuilder stringResultBuilder;
    private String stringResult;
    private int lastClickedButton;
    private int dotsNumber = 0;


    private Timer mTimer1;
    private TimerTask mTt1;
    private Handler mTimerHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_simple);
        initializeCommonButtons();

        restoreData(savedInstanceState);
        startTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("LastClicked", lastClickedButton);
        savedInstanceState.putString("BuilderString", stringResultBuilder.toString());
    }


    protected void restoreData(Bundle savedInstanceState) {

        lastClickedButton = 0;
        stringResultBuilder = new StringBuilder();
        stringResult = "";

        if (savedInstanceState != null) {
            lastClickedButton = savedInstanceState.getInt("LastClicked");
            stringResultBuilder.append(savedInstanceState.getString("BuilderString"));
        }
    }

    protected void initializeCommonButtons() {
        btn_0 = findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6 = findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9 = findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);

        btn_c = findViewById(R.id.btn_c);
        btn_c.setOnClickListener(this);
        btn_ac = findViewById(R.id.btn_ac);
        btn_ac.setOnClickListener(this);
        btn_dot = findViewById(R.id.btn_dot);
        btn_dot.setOnClickListener(this);
        btn_bksp = findViewById(R.id.btn_bksp);
        btn_bksp.setOnClickListener(this);
        btn_sign = findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(this);
        btn_result = findViewById(R.id.btn_result);
        btn_result.setOnClickListener(this);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_divide = findViewById(R.id.btn_divide);
        btn_divide.setOnClickListener(this);
        btn_subtract = findViewById(R.id.btn_subtract);
        btn_subtract.setOnClickListener(this);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_multiply.setOnClickListener(this);
        resultBox = findViewById(R.id.equation_box);
        resultBox.setShowSoftInputOnFocus(false);
    }

    @Override
    public void onClick(View view) {
        selectBaseAction(view.getId());
        updateScreen();
    }


    public void appendStringResultBuilder(String string) {
        stringResultBuilder.append(string);
    }

    public void setLastClickedButton(int button) { lastClickedButton = button; }

    public StringBuilder getStringResultBuilder() { return stringResultBuilder; }

    public int getLastClickedButton() { return lastClickedButton; }

    protected void selectBaseAction(int button) {

        switch (button) {

            case R.id.btn_0:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'0');
                lastClickedButton = R.id.btn_0;
                break;

            case R.id.btn_1:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'1');
                lastClickedButton = R.id.btn_1;
                break;

            case R.id.btn_2:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'2');
                lastClickedButton = R.id.btn_2;
                break;

            case R.id.btn_3:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'3');
                lastClickedButton = R.id.btn_3;
                break;

            case R.id.btn_4:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'4');
                lastClickedButton = R.id.btn_4;
                break;

            case R.id.btn_5:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'5');
                lastClickedButton = R.id.btn_5;
                break;

            case R.id.btn_6:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'6');
                lastClickedButton = R.id.btn_6;
                break;

            case R.id.btn_7:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'7');
                lastClickedButton = R.id.btn_7;
                break;

            case R.id.btn_8:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'8');
                lastClickedButton = R.id.btn_8;
                break;

            case R.id.btn_9:
                stringResultBuilder.insert(resultBox.getSelectionEnd(),'9');
                lastClickedButton = R.id.btn_9;
                break;

            case R.id.btn_c:
                if (lastClickedButton != R.id.btn_c) {

                    try {
                        stringResultBuilder.deleteCharAt(resultBox.getSelectionEnd() - 1);
                        dotsNumber = countDots(stringResultBuilder.toString());
                    }
                    catch (StringIndexOutOfBoundsException e ) { }

                } else {
                    stringResultBuilder.setLength(0);
                    dotsNumber = 0;
                }
                lastClickedButton = R.id.btn_c;
                break;

            case R.id.btn_ac:
                stringResultBuilder.setLength(0);
                dotsNumber = 0;
                lastClickedButton = R.id.btn_ac;
                break;

            case R.id.btn_dot:

                if (lastClickedButton != R.id.btn_dot) {

                    String [] asd = stringResult.split("[\\+\\*\\(\\)\\/\\-^]");

//                    Log.d("after_split-before_len", Integer.valueOf(asd.length).toString() );   // 12.3/1.9 not working

                    if (asd.length > dotsNumber) {
                        try {
                            Double.parseDouble( asd[asd.length-1]+'.' );
                        } catch (NumberFormatException e) {
                            lastClickedButton = R.id.btn_dot;
                            break;
                        }
                        stringResultBuilder.append('.');
                        dotsNumber++;
                    }
                }

                lastClickedButton = R.id.btn_dot;
                break;

            case R.id.btn_bksp:
                try {
                    stringResultBuilder.deleteCharAt(resultBox.getSelectionEnd()-1);
                    dotsNumber = countDots(stringResultBuilder.toString());
                }
                catch (StringIndexOutOfBoundsException e ) { }
                lastClickedButton = R.id.btn_bksp;
                break;

            case R.id.btn_sign:
                try {
                    Double.parseDouble(stringResult);
                }
                catch (NumberFormatException e) {
                    lastClickedButton = R.id.btn_sign;
                    break;  // cannot change sign of sth you can't parse
                }

                if (lastClickedButton == R.id.btn_sign) {
                    if (stringResultBuilder.indexOf("-") == 0 )
                        stringResultBuilder.deleteCharAt(0);
                    else
                        stringResultBuilder.insert(0,'-');
                }
                else if (stringResultBuilder.indexOf("-") == 0 ) {
                    stringResultBuilder.deleteCharAt(0);
                }
                else
                    stringResultBuilder.insert(0,'-');

                lastClickedButton = R.id.btn_sign;
                break;

            case R.id.btn_add:
                addOperationIfPossible(lastClickedButton, "+");
                lastClickedButton = R.id.btn_add;
                break;

            case R.id.btn_divide:
                addOperationIfPossible(lastClickedButton, "/");
                lastClickedButton = R.id.btn_divide;
                break;

            case R.id.btn_subtract:
                addOperationIfPossible(lastClickedButton, "-");
                lastClickedButton = R.id.btn_subtract;
                break;

            case R.id.btn_multiply:
                addOperationIfPossible(lastClickedButton, "*");
                lastClickedButton = R.id.btn_multiply;
                break;

            case R.id.btn_result:

                if (stringResultBuilder.length() != 0) {
                    bracketsAutoCompletion(stringResult);

                    stringResultBuilder.setLength(0);
                    stringResultBuilder.append(calculate2(stringResult));
                }
                lastClickedButton = R.id.btn_result;
                break;
        }

    }


    private int countDots(String equation) {
        int dots = 0;
        for (char c : equation.toCharArray() )
            if (c == '.')
                dots++;

        return dots;
    }

    protected void addOperationIfPossible(int last_btn, String op) {

        if (stringResult.isEmpty()) {
            Toast.makeText(this,"Operation not permitted",Toast.LENGTH_SHORT).show();
        }
        else if (stringResult.endsWith("(")) { // prevent cos(+)
            Toast.makeText(this,"Operation not permitted",Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            removeOperatorIfAlreadyExists(last_btn);
            stringResultBuilder.append(op);
        }
    }

    private void removeOperatorIfAlreadyExists(int btn) {

        if ( clickedOperator(btn) ) {
            stringResultBuilder.deleteCharAt(stringResultBuilder.length()-1);

        }
        String sub = stringResultBuilder.substring(stringResultBuilder.length()-1);

        if (InfixToPostfix.isOperation(sub) && !sub.endsWith("(") && !sub.endsWith(")")) {

            stringResultBuilder.deleteCharAt(stringResultBuilder.length()-1);
        }
    }

    private boolean clickedOperator(int btn) {

        int [] operators = {
                R.id.btn_add,
                R.id.btn_divide,
                R.id.btn_subtract,
                R.id.btn_multiply,
                R.id.btn_pow_x_y
        };

        for (int operator : operators) {
            if (btn == operator)
                return true;
        }
        return false;
    }

    protected void updateScreen() {

        stringResult = stringResultBuilder.toString();
        resultBox.setText(stringResult);
        resultBox.setSelection(resultBox.getText().length());
    }


    private int diffBetweenBracketsNumber(String equation) {
        int left_bracket = 0;
        int right_bracket = 0;

        for (char c : equation.toCharArray()) {
            if (c == '(') left_bracket++;
            if (c == ')') right_bracket++;
        }
        return left_bracket-right_bracket;
    }

    private void bracketsCompletionByTimer() {

        if (diffBetweenBracketsNumber(stringResult) > 0) {
            if ( InfixToPostfix.isNumber( stringResult.substring(stringResult.length()-1)) ||
                    stringResult.substring(stringResult.length()-1).equals(")") ||
                    stringResult.substring(stringResult.length()-1).equals("%") ) {

                stringResultBuilder.append(')');
                updateScreen();
            }
        }
    }

    private void bracketsAutoCompletion(String equation) {

        int diff = diffBetweenBracketsNumber(equation);

        for (int i = 0; i < diff; i++)
            stringResultBuilder.append(')');

        updateScreen();
    }


    private String calculate2(String equation) {

        InfixToPostfix infToPost = new InfixToPostfix(equation);
        StringTokenizer st = new StringTokenizer(infToPost.getOnpEquation(), "+-*/^ ", true);
        Stack<Double> stack = new Stack<>();

        double number1;
        double number2;
        double result = 0.0;

        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            if (InfixToPostfix.isNumber(token)) {
                stack.push(Double.parseDouble(token));

            }
            else if (InfixToPostfix.isOperation(token)) {

                try {
                    number2 = stack.pop();
                    number1 = stack.pop();
                } catch (EmptyStackException e) {
                    Toast.makeText(this,"Operation not permitted",Toast.LENGTH_SHORT).show();
                    return equation;
                }

                switch (token) {
                    case "+":
                        result = number1 + number2;
                        break;
                    case "-":
                        result = number1 - number2;
                        break;
                    case "*":
                        result = number1 * number2;
                        break;
                    case "/":
                        if (number2 == 0) {
                            Toast.makeText(this,"Division by 0!",Toast.LENGTH_SHORT).show();
                            return equation;
                        }
                        result = number1 / number2;
                        break;
                    case "^":
                        result = Math.pow(number1, number2);
                        break;

                }
                stack.push(result);
            }
            else if (InfixToPostfix.isFunction(token)) {

                try {
                    number1 = stack.pop();
                } catch (EmptyStackException e) {
                    Toast.makeText(this,"Operation not permitted",Toast.LENGTH_SHORT).show();
                    return equation;
                }

                switch (token) {
                    case "sin":
                        result = Math.sin(Math.toRadians(number1));
                        break;
                    case "cos":
                        result = Math.cos(Math.toRadians(number1));
                        break;
                    case "tan":
                        result = Math.tan(Math.toRadians(number1));
                        break;
                    case "log":
                        result = Math.log10(number1);
                        break;
                    case "ln":
                        result = Math.log(number1);
                        break;
                    case "sqrt":
                        result = Math.sqrt(number1);
                        break;
                }
                stack.push(result);
            }
            else if (token.equals(" ")) {
                continue;
            }
            else {
                Toast.makeText(this,"Something is wrong",Toast.LENGTH_SHORT).show();
                return equation;
            }
        }

        if (stack.isEmpty()) {
            return equation;
        }

        return doubleOrInteger(stack.pop());
    }


    public String doubleOrInteger(double result) {
        int a = (int) result;
        if (a == result) {
            dotsNumber = 0;
            return Integer.toString(a);
        }
        else {
            dotsNumber = 1;
            return Double.toString(result);
        }
    }


    private void stopTimer(){
        if(mTimer1 != null){
            mTimer1.cancel();
            mTimer1.purge();
        }
    }

    private void startTimer(){
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        bracketsCompletionByTimer();
                    }
                });
            }
        };

        mTimer1.schedule(mTt1, 1, 4000);
    }


}
