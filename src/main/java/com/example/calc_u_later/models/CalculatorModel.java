package com.example.calc_u_later.models;


import java.lang.Math;

import java.util.*;


public class CalculatorModel {

    public CalculatorModel() {

    }

    public String StringResult(ArrayList<String> tokens) {
        ArrayList<String> reversePolishExpression = this.ShuntingYardAlgo(tokens);
        for (String token : reversePolishExpression) {
            System.out.print(token + " ");
        }
        System.out.println();
        return Double.toString(this.ReversePolishNotation(reversePolishExpression));
    }



//    public String SingleOperation(ArrayList<String> tokens, String value){
//        String operator = tokens.get(1);
//        double x = Double.parseDouble(tokens.get(0 )), y = Double.parseDouble(value);
//
//        switch (operator) {
//            case "+": return Double.toString(x - y);
//            case "-": return Double.toString(x - y);
//            case "*": return Double.toString(x * y);
//            case "/": return Double.toString(x / y);
//            case "%": return Double.toString((x/100) * y);
//            case "yroot": return Double.toString(Math.pow(x, 1/y));
//            case "^": return Double.toString(Math.pow(x, y));
//            case "mod": return Double.toString(x % y);
//            default: return null;
//        }
//    }



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
            case "rand": return Double.toString(Math.random() * (0.99 - 0.0));
            case "n!":
                double factResult = 1.0;
                for (double i = 1; i <= value; i++) { factResult *= i; }
                return Double.toString(factResult);
            default:
                return "0";
        }
    }





    private ArrayList<String> ShuntingYardAlgo(ArrayList<String> tokens) {
        ArrayList<String> rpnExpr = new ArrayList<>();
        Stack<String> opStack = new Stack<String>();

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("yroot") || token.equals("^") || token.equals("mod") || token.equals("%")) {
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

        return rpnExpr;
    }



    private double ReversePolishNotation(ArrayList<String> rpnExpression) {
        Stack<String> resultStack = new Stack<>();
        double x, y;
        String result;

        for (String token : rpnExpression) {
            if (!(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("yroot") || token.equals("^") || token.equals("mod") || token.equals("%"))) {
                resultStack.push(token);
                continue;
            }

            switch (token) {
                case "+":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString(y + x);
                    resultStack.push(result);
                    break;
                case "-":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString(y - x);
                    resultStack.push(result);
                    break;
                case "*":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString(y * x);
                    resultStack.push(result);
                    break;
                case "/":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString(y / x);
                    resultStack.push(result);
                    break;
                case "%":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString((x/100) * y);
                    resultStack.push(result);
                    break;
                case "yroot":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString(Math.round(Math.pow(y, 1/x)));
                    resultStack.push(result);
                    break;
                case "^":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString(Math.pow(y, x));
                    resultStack.push(result);
                    break;
                case "mod":
                    x = Double.parseDouble(resultStack.pop());
                    y = Double.parseDouble(resultStack.pop());
                    result = Double.toString(y % x);
                    resultStack.push(result);
                    break;
                default:
                    continue;
            }
            if (!resultStack.isEmpty()) {System.out.println(resultStack.peek());}
        }

        return Double.parseDouble(resultStack.pop());
    }



    private static int Pemdas(String op) {
        if (op.equals("+") || op.equals("-"))
            return 1;
        else if (op.equals("*") || op.equals("/"))
            return 2;
        else if (op.equals("^") || op.equals("yroot") || op.equals("mod") || op.equals("%"))
            return 3;
        else
            return -1;
    }

    private static boolean LeftToRightOp(String arithmeticOp) {
        if (arithmeticOp.equals("+") || arithmeticOp.equals("-") || arithmeticOp.equals("/") || arithmeticOp.equals("*") || arithmeticOp.equals("%")) { return true; }
        else { return false; }
    }
}
