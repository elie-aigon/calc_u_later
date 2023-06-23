package com.example.calc_u_later.models;


import java.lang.Math;

import java.util.*;


public class CalculatorModel {

    public CalculatorModel() {

    }

    public String StringResult(ArrayList<String> tokens) {
        Object[] reversePolishExpression = this.ShuntingYardAlgo(tokens);
        for (Object test : reversePolishExpression) {
            System.out.print(test + " ");
        }
        System.out.println();
        double result = this.ReversePolishNotation(reversePolishExpression);
        return "";
    }

    public static String Functions(String func, String strValue) {
        double value = Double.parseDouble(strValue);

        switch (func) {
            case "|x|": return Double.toString(Math.abs(value));
            case "1/x": return Double.toString(1 / value);
            case "2√": return Double.toString(Math.sqrt(value));
            case "sin": return Double.toString(Math.sin(value));
            case "cos": return Double.toString(Math.cos(value));
            case "log": return Double.toString(Math.log(value));
            case "ln": return Double.toString((-Math.log(1-value))/value);
            case "n!":
                double factResult = 1.0;
                for (double i = 1; i <= value; i++) { factResult *= i; }
                return Double.toString(factResult);
            default:
                return "0";
        }
    }





    private Object[] ShuntingYardAlgo(ArrayList<String> tokens) {
        ArrayList<String> rpnExpr = new ArrayList<>();
        Stack<String> opStack = new Stack<String>();

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("yroot") || token.equals("^") || token.equals("mod")) {
                while(!opStack.isEmpty() && this.Pemdas(token) <= this.Pemdas(opStack.peek()) && this.LeftToRightOp(token)){
                    rpnExpr.add(opStack.pop());
                }
                opStack.push(token);
            }
            else if (token.equals("(")) {
                opStack.push(token);
            }
            else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")){
                    rpnExpr.add(opStack.pop());
                }
                opStack.pop();
            }
            else {
                rpnExpr.add(token);
            }
        }

        while (!opStack.isEmpty()) {
            if (opStack.peek().equals("(")){
                opStack.pop();
                continue;
            }
            rpnExpr.add(opStack.pop());
        }

        Object[] output = rpnExpr.toArray();
        return output;
    }



    private double ReversePolishNotation(Object[] rpnExpression) {
        Stack<String> resultStack = new Stack<>();

        return 0;
    }



    private static int Pemdas(String op) {
        if (op.equals("+") || op.equals("-"))
            return 1;
        else if (op.equals("*") || op.equals("/"))
            return 2;
        else if (op.equals("^") || op.equals("yroot") || op.equals("mod"))
            return 3;
        else
            return -1;
    }

    private static boolean LeftToRightOp(String arithmeticOp) {
        if (arithmeticOp.equals("+") || arithmeticOp.equals("-") || arithmeticOp.equals("/") || arithmeticOp.equals("*")) { return true; }
        else { return false; }
    }
}
