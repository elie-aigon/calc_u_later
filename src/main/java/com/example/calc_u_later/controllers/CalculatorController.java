package com.example.calc_u_later.controllers;


import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.calc_u_later.models.CalculatorModel;


public class CalculatorController implements Initializable {
    //FXML elements
    @FXML private Label exprField;
    @FXML private Label valueField;


    //Calculator's logic, solves expression using reverse polish and shunting yard algorithm
    private ArrayList<String> exprTokens = new ArrayList<String>();
    private CalculatorModel calculator = new CalculatorModel();

    //Controller's flags/utilities
    private String currentFuncLabel;
    private int parenthesesDepth = 0;
    private int previousFuncLabel;
    private boolean isNewValue;
    private boolean isFuncValue;
    private boolean isDecimal;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valueField.setText("0");
    }





    @FXML
    private void OperatorButtons(Event event) {
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();

        if (buttonLabel.equals("+") || buttonLabel.equals("-") || buttonLabel.equals("x") || buttonLabel.equals("/")) {
            this.exprTokens.add(valueField.getText());

            if (this.isFuncValue) { exprField.setText( exprField.getText() + " " + buttonLabel + " " ); }
            else if (this.previousFuncLabel > 0) { exprField.setText(exprField.getText().substring(0, exprField.getText().length() - this.previousFuncLabel) + valueField.getText() + " " + buttonLabel + " "); }
            else { exprField.setText( exprField.getText() + valueField.getText() + " " + buttonLabel + " " ); }

            valueField.setText(this.calculator.StringResult(this.exprTokens));
            if (buttonLabel.equals("x")) { this.exprTokens.add("*"); }
            else { this.exprTokens.add(buttonLabel); }
        }
        else {
            this.exprTokens.add(valueField.getText());

            if (this.isFuncValue) { exprField.setText(exprField.getText() + " " + this.SpecialOperator(buttonLabel) + " " ); }
            else if (this.previousFuncLabel > 0) { exprField.setText(exprField.getText().substring(0, exprField.getText().length() - this.previousFuncLabel) + valueField.getText() + " " + this.SpecialOperator(buttonLabel) + " "); }
            else { exprField.setText(exprField.getText() + valueField.getText() + " " + this.SpecialOperator(buttonLabel) + " " ); }

            valueField.setText(this.calculator.StringResult(this.exprTokens));
            this.exprTokens.add(this.SpecialOperator(buttonLabel));
        }

        for (String token : this.exprTokens) { System.out.print(token + " "); }
        System.out.println();
        this.isDecimal = false;
        this.isNewValue = true;
        this.isFuncValue = false;
        this.previousFuncLabel = 0;
    }

    @FXML
    private void ValueButtons(Event event) {
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();

        if (this.isNewValue) {
            if (this.isFuncValue) {
                exprField.setText(exprField.getText().substring(0, exprField.getText().length() - this.previousFuncLabel));
            }
            valueField.setText("0");
            this.isNewValue = false;
        }

        String currentValueTxt = valueField.getText();

        switch (buttonLabel) {
            case "π":
                valueField.setText("3.1415926535897932384626433832795");
                break;
            case "e":
                valueField.setText("2.7182818284590452353602874713527");
                break;
            default:
                if (currentValueTxt == "0" || (valueField.getText() == "2.7182818284590452353602874713527" || valueField.getText() == "3.1415926535897932384626433832795") || this.isFuncValue) { valueField.setText(buttonLabel); }
                else { valueField.setText(currentValueTxt + buttonLabel); }
                break;
        }
        this.isFuncValue = false;
        this.previousFuncLabel = 0;
    }

    @FXML
    private void FunctionButtons(Event event) {
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();

        this.currentFuncLabel = this.Functions(buttonLabel, valueField.getText());
        if (!buttonLabel.equals("rand")) {
            if (this.isFuncValue || this.previousFuncLabel > 0) { exprField.setText(exprField.getText().substring(0, exprField.getText().length() - this.previousFuncLabel) + this.currentFuncLabel); }
            else { exprField.setText(exprField.getText() + this.currentFuncLabel); }
            this.previousFuncLabel = this.currentFuncLabel.length();
        }

        valueField.setText(this.calculator.Functions(buttonLabel, valueField.getText()));
        this.isFuncValue = true;
        this.isNewValue = true;
    }

    @FXML
    private void ParenthesesButtons(Event event) {
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();
        String currentValueTxt = valueField.getText();

        if (buttonLabel.equals("(")) {
            if (!this.isNewValue) {
                this.exprTokens.add(currentValueTxt);
                this.exprTokens.add("*");
                exprField.setText(exprField.getText() + currentValueTxt + " x " + buttonLabel);
            }
            else {
                valueField.setText("0");
                exprField.setText(exprField.getText() + buttonLabel);
            }

            this.parenthesesDepth++;
            this.exprTokens.add(buttonLabel);
        }
        else if (buttonLabel.equals(")") && this.parenthesesDepth != 0) {
            this.parenthesesDepth--;
            this.exprTokens.add(currentValueTxt);
            this.exprTokens.add(buttonLabel);
            exprField.setText(exprField.getText() + currentValueTxt + buttonLabel);
        }
        for (String token : this.exprTokens) { System.out.print(token + " "); }
        System.out.println();
    }

    @FXML
    private void EqualButton() {
        if (this.parenthesesDepth != 0) {
            exprField.setText( exprField.getText() + " ");

            if (!this.isNewValue){
                this.exprTokens.add(valueField.getText());
                exprField.setText( exprField.getText() + valueField.getText() + " ");
            }

            while (this.parenthesesDepth != 0) {
                exprField.setText( exprField.getText() + ")");
                this.exprTokens.add(")");
                this.parenthesesDepth--;
            }
        }
        else if (!this.isNewValue) {
            this.exprTokens.add(valueField.getText());
            exprField.setText( exprField.getText() + valueField.getText() + " ");
        }

        for (String token : this.exprTokens) { System.out.print(token + " "); }
        System.out.println();
        valueField.setText(this.calculator.StringResult(this.exprTokens));
        exprField.setText( exprField.getText() + " = " );
    }

    @FXML
    private void SignButton(Event event) {
        String currentValueTxt = valueField.getText();

        if (currentValueTxt != "0") {
            if (currentValueTxt.charAt(0) != '-') { valueField.setText("-" + currentValueTxt); }
            else { valueField.setText(currentValueTxt.substring(1)); }
        }
    }

    @FXML
    private void DecimalButton() {
        if (!this.isDecimal) {
            valueField.setText(valueField.getText() + ".");
            this.isDecimal = true;
        }
    }

    @FXML
    private void ClearButton() {
        if (valueField.getText() != "0") {
            valueField.setText("0");
            this.isDecimal = false;
            this.isFuncValue = false;
            this.isNewValue = true;
            this.previousFuncLabel = 0;
            this.parenthesesDepth = 0;
        }
        else if (exprField.getText() != "") {
            this.exprTokens.clear();
            exprField.setText("");
        }
    }

    @FXML
    private void BackspaceButton() {
        String currentValueTxt = valueField.getText();
        if (this.isFuncValue){
            return;
        }

        if (currentValueTxt.length() > 1) {
            if (currentValueTxt.charAt(currentValueTxt.length() - 1) == '.') { this.isDecimal = false; }
            valueField.setText(currentValueTxt.substring(0, currentValueTxt.length() - 1));
        }
        else { valueField.setText("0"); }
    }





    private static String SpecialOperator(String op) {
        switch (op) {
            case "x√": return "yroot";
            case "%": return "%";
            case "mod": return "Mod";
            case "x^y": return "^";
            default:
                return null;
        }
    }



    private String Functions(String func, String valueLabel) {
        if (this.isFuncValue) {
            switch (func) {
                case "|x|": return "abs( " + this.currentFuncLabel + ")";
                case "n!": return "fact( " + this.currentFuncLabel + ")";
                case "1/x": return "1/( " + this.currentFuncLabel + ")";
                case "2√": return "sqrt( " + this.currentFuncLabel + ")";
                case "sin", "cos", "tan", "log", "ln": return func + "( " + this.currentFuncLabel + ")";
                default:
                    return null;
            }
        }
        else {
            switch (func) {
                case "|x|": return "abs(" + valueLabel + ")";
                case "n!": return "fact(" + valueLabel + ")";
                case "1/x": return "1/(" + valueLabel + ")";
                case "2√": return "sqrt(" + valueLabel + ")";
                case "sin", "cos", "tan", "log", "ln": return func + "(" + valueLabel + ")";
                default:
                    return null;
            }
        }
    }
}
