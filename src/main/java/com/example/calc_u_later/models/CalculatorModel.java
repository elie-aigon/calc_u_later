package com.example.calc_u_later.models;


import java.lang.Math;
import java.util.ArrayList;
import java.util.Stack;



/*
Defines the calculator's logic or how it mostly evaluates mathematical expressions
It basically uses the ShuntingYardAlgorithm to get the ReversePolishExpression to evaluate, the algorithm handles pemdas and parentheses
 */
public class CalculatorModel {

    public CalculatorModel() { }

    public static String StringResult(ArrayList<String> tokens) {
        ArrayList<String> reversePolishExpression = ShuntingYardAlgo(tokens);
        return Double.toString(ReversePolishNotation(reversePolishExpression));
    }



    //Returns the function result
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



    //Used for chained operations, tries to get the operand on the rightest part of the whole expression
    public ArrayList<String> defineSecondOperand(ArrayList<String> tokens) {
        ArrayList<String> secondOperandExpr = new ArrayList<>();
        String baseOperator = "";
        int operandIndexStart = 0;

        int parenthesesDepth = 0;
        boolean inParentheses = false; //Used to skip parentheses

        for (int i = 0; i < tokens.size(); i++) {
            if (!inParentheses && Pemdas(tokens.get(i)) <= Pemdas(baseOperator)) {
                baseOperator = tokens.get(i);
                operandIndexStart = i + 1;
                continue;
            }

            if (tokens.get(i).equals("(")) {
                parenthesesDepth++;
                inParentheses = true;
            }
            else if (tokens.get(i).equals(")")) {
                parenthesesDepth--;
                if (parenthesesDepth == 0) { inParentheses = false; }
            }
        }

        //Fill (starting where the last operator with the most priority is) the secondOperandExpression to be evaluated after
        for (int i = operandIndexStart; i < tokens.size() - 1; i++) {
            secondOperandExpr.add(tokens.get(i));
        }
        secondOperandExpr.add(baseOperator);

        return secondOperandExpr;
    }





    //Defines the RPN expression, tokens here is the expression as an arraylist with each token being either a value, an operator, or a parentheses
    private static ArrayList<String> ShuntingYardAlgo(ArrayList<String> tokens) {
        ArrayList<String> rpnExpr = new ArrayList<>(); //Actual RPN expression to return
        Stack<String> opStack = new Stack<String>(); //Stack of operators

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("yroot") || token.equals("^") || token.equals("mod") || token.equals("%")) {
                while(!opStack.isEmpty() && Pemdas(token) <= Pemdas(opStack.peek()) && LeftToRightOp(token)){
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



    //Evaluates the RPN expression
    private static double ReversePolishNotation(ArrayList<String> rpnExpression) {
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
