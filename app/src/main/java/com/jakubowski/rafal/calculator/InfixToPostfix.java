package com.jakubowski.rafal.calculator;

import android.util.Log;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by rafal on 20.03.18.
 */

public class InfixToPostfix {

    private String equation = null;

    private static String [] functions = {
            "sin",
            "cos",
            "tan",
            "log",
            "ln",
            "sqrt"
    };

    private static String [] operators = {
            "(",
            "+", "-", ")",
            "*", "/",
            "^"
    };

    public InfixToPostfix(String equation) {
        this.equation = equation;
    }


    public String getOnpEquation() {
        return toONP2(equation);
    }


    private String toONP2(String equation) {

        if (equation.charAt(0) == '-') {
            equation = "0" + equation; // -5 == 0-5
        }

        for (int i = 0; i < equation.length(); i++){
            if (equation.charAt(i) == '%' ) {
                int j=i-1;
                StringBuilder num = new StringBuilder();
                num.append(equation.charAt(j));

                while ( j > 0 && !isOperation(equation.substring(j-1, j))) {

                    num.append(equation.charAt(--j));
                }
                num.reverse();
                double a = Double.parseDouble(num.toString());
                String temp = equation.substring(0,j) + Double.toString(a/100) + equation.substring(i+1);
                equation = temp;
            }
        }

        StringTokenizer st = new StringTokenizer(equation, "+-*/^()", true);
        Stack<String> stack = new Stack<>();
        String onp = "";

        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            if (isOperation(token)) {
                if ( token.equals("(") ) {
                    stack.push("(");
                    continue;

                } else if ( token.equals(")") ) {
                    while (!stack.peek().equals("(")) {
                        onp += stack.pop() + " ";
                    }

                    stack.pop();
                    if ( isFunction(stack.peek()) )
                        onp += stack.pop() + " ";
                    continue;
                }

                while (!stack.empty() && priority(stack.peek()) >= priority(token))
                    onp += stack.pop() + " ";
                stack.push(token);

            } else if (isFunction(token)) {
                stack.push(token);

            } else if (isNumber(token)) {
                onp += token + " ";
            }
        }

        while (!stack.empty())
            onp += stack.pop() + " ";

//        Log.d("onp", onp);

        return onp;
    }




    private int priority(String operator) {

        switch (operator) {
            case "(" :
                return 1;
            case "+" :
            case "-" :
            case ")" :
                return 2;
            case "*" :
            case "/" :
            case "%" :
                return 3;
            case "^" :
                return 4;
            default:
                return 0;
        }
    }

    public static boolean isOperation(String token) {
        for (String op: operators)
            if (token.equals(op)) return true;

        return false;
    }

    public static boolean isFunction(String token) {
        for (String func: functions)
            if (token.equals(func)) return true;

        return false;
    }

    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
