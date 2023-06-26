package com.example.calc_u_later.controllers;


//FXML imports
import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import java.net.URL;

//Java imports
import java.util.ArrayList;


//Custom modules imports
import com.example.calc_u_later.models.CalculatorModel;
import com.example.calc_u_later.controllers.toolscalculator.HistoryObject;
import com.example.calc_u_later.controllers.toolscalculator.MemoryObject;



/*
Controller for the calculator's tab, code here only handles what to display on both labels according to user input
The goal was to reproduce has much as possible the behaviours on the windows calculator
 */
public class CalculatorController implements Initializable {
    //FXML elements
    @FXML private Label exprField;
    @FXML private Label valueField;
    @FXML private ScrollPane historyScrollPane;
    @FXML private ScrollPane memoryScrollPane;
    @FXML private HistoryObject historyObject;
    @FXML private MemoryObject memoryObject;


    //Calculator's logic, solves expression using reverse polish and shunting yard algorithm
    private ArrayList<String> exprTokens = new ArrayList<String>();
    private CalculatorModel calculator = new CalculatorModel();

    //Controller's flags/utilities
    private String currentFuncLabel;
    private String result;
    private int previousFuncLabel;
    private int parenthesesDepth = 0;
    private boolean isNewValue;
    private boolean isFuncValue;
    private boolean isDecimal;
    private boolean isChainedOperation = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valueField.setText("0");
    }


    /* ----- FXML Methods ----- */


    //Method for buttons considered as operator's (+, -, x, /, %, ^, Mod, yroot)
    @FXML
    private void OperatorButtons(Event event) {
        //New operation and gets which operator has been pressed
        this.NewOperation();
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();

        //Needs if it has to use 'SpecialOperation' method or not but basically does the same thing
        if (buttonLabel.equals("+") || buttonLabel.equals("-") || buttonLabel.equals("x") || buttonLabel.equals("/")) {
            //Adds the current value in the actual expression to be evaluated
            this.exprTokens.add(valueField.getText());

            //Handles how to display the current value and the operator pressed in the expression label, depends on if the value comes from a function or if it's repeated
            if (this.isFuncValue) { exprField.setText( exprField.getText() + " " + buttonLabel + " " ); }
            else if (this.previousFuncLabel > 0) { exprField.setText(exprField.getText().substring(0, exprField.getText().length() - this.previousFuncLabel) + valueField.getText() + " " + buttonLabel + " "); }
            else { exprField.setText( exprField.getText() + valueField.getText() + " " + buttonLabel + " " ); }

            //Adds the operator in the expression
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

        //Updates flags and utilities accordingly
        this.isDecimal = false;
        this.isNewValue = true;
        this.isFuncValue = false;
        this.previousFuncLabel = 0;
    }

    //Method for value buttons (all digits and constants)
    @FXML
    private void ValueButtons(Event event) {
        //New operation and gets which value has been pressed
        this.NewOperation();
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();

        //Resets the label if it's a new value
        if (this.isNewValue) {
            if (this.isFuncValue) {
                exprField.setText(exprField.getText().substring(0, exprField.getText().length() - this.previousFuncLabel));
            }
            valueField.setText("0");
            this.isNewValue = false;
        }

        //Gets the current value as variable
        String currentValueTxt = valueField.getText();

        //Displays the corresponding value
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

        //Updates flags and utilities accordingly
        this.isFuncValue = false;
        this.previousFuncLabel = 0;
    }

    //Method for buttons considered as functions, they directly perform a math function on the current value
    @FXML
    private void FunctionButtons(Event event) {
        //New operation and gets which value has been pressed
        this.NewOperation();
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();

        this.currentFuncLabel = this.Functions(buttonLabel, valueField.getText());
        if (!buttonLabel.equals("rand")) {
            if (this.isFuncValue || this.previousFuncLabel > 0) { exprField.setText(exprField.getText().substring(0, exprField.getText().length() - this.previousFuncLabel) + this.currentFuncLabel); }
            else { exprField.setText(exprField.getText() + this.currentFuncLabel); }
            this.previousFuncLabel = this.currentFuncLabel.length();
        }

        valueField.setText(this.calculator.Functions(buttonLabel, valueField.getText()));
        //Updates flags and utilities accordingly
        this.isFuncValue = true;
        this.isNewValue = true;
    }

    //Handles parentheses
    @FXML
    private void ParenthesesButtons(Event event) {
        //New operation and gets which value has been pressed and the current value as a variable
        this.NewOperation();
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
    }

    //Outputs the result given the expression, handles chained operation as well
    @FXML
    private void EqualButton() {
        //Depending on the expression depths in parentheses it needs to close them if not done by the user
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

        //Will perform the corresponding method if consecutive press on '=', else we just need the result
        if (this.isChainedOperation) {
            this.ChainedOperation();
        }
        else {
            this.result = this.calculator.StringResult(this.exprTokens);
        }

        //Updates the value with the result
        valueField.setText(this.result);
        exprField.setText( exprField.getText() + " = " );

        historyObject.AddHistoricElement(exprField.getText(), valueField.getText());

        //Updates flags and utilities accordingly
        this.isChainedOperation = true;
    }

    //Defines how to handle variables
    @FXML
    private void MemoryButtons(Event event) {
        //New operation and gets which memory function has been pressed and the current value as a variable
        this.NewOperation();
        Button valueButton = (Button) event.getSource();
        String buttonLabel = valueButton.getText();
        String currentValueTxt = valueField.getText();

        //Directly creates the elements needed in the vbox stored in the memory tab, doing this here gives access to the value label
        Label memoryLabel = new Label(currentValueTxt);
        Button MAdd = new Button("M+");
        Button MSub = new Button("M-");

        switch (buttonLabel) {
//            case "M+":
//                memoryObject.CalculatorButtonMAdd(valueField.getText());
//                break;
//            case "M-":
//                memoryObject.CalculatorButtonMSub(valueField.getText());
//                break;
            case "MR":
                valueField.setText(memoryObject.CalculatorButtonMR());
                break;
            case "MC":
                memoryObject.ClearMemory();
                break;
            //
            case "MS":
                if (!valueField.getText().equals("")) {
                    memoryLabel.setOnMouseClicked(e -> valueField.setText(memoryLabel.getText()));
                    MAdd.setOnAction(e -> {
                        double variableResult = Double.parseDouble(memoryLabel.getText()) + Double.parseDouble(valueField.getText());
                        memoryLabel.setText(Double.toString(variableResult));
                    });
                    MSub.setOnAction(e -> {
                        double variableResult = Double.parseDouble(memoryLabel.getText()) - Double.parseDouble(valueField.getText());
                        memoryLabel.setText(Double.toString(variableResult));
                    });

                    memoryObject.AddMemoryElement(memoryLabel, MAdd, MSub);
                }
                break;
        }

        //Updates flags and utilities accordingly
        this.isFuncValue = false;
        this.isNewValue = true;
    }

    //Just adds or removes the negative sign in front of the current value
    @FXML
    private void SignButton(Event event) {
        this.NewOperation();
        String currentValueTxt = valueField.getText();

        if (currentValueTxt != "0") {
            if (currentValueTxt.charAt(0) != '-') { valueField.setText("-" + currentValueTxt); }
            else { valueField.setText(currentValueTxt.substring(1)); }
        }
    }

    //Adds the decimal point
    @FXML
    private void DecimalButton() {
        this.NewOperation();

        if (!this.isDecimal) {
            valueField.setText(valueField.getText() + ".");
            this.isDecimal = true;
        }
    }

    //Resets all flags, removes the expression if pressed a second time
    @FXML
    private void ClearButton() {
        this.NewOperation();

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

    //Deletes last char of current value
    @FXML
    private void BackspaceButton() {
        this.NewOperation();

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


    /* ----- Utility Methods ----- */


    //Copies the operators in windows calculator
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



    //What to display on the expression depending on the function pressed
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



    //Defines how operations are chained if equal button is pressed consecutively
     private void ChainedOperation() {
         ArrayList<String> chainedOp = new ArrayList<>();
         ArrayList<String> secondOperandExpr = this.calculator.defineSecondOperand(this.exprTokens);

         chainedOp.add(this.result);

         if (!this.isChainedOperation) {
             exprField.setText(this.result);

             chainedOp.add(secondOperandExpr.get(secondOperandExpr.size() - 1));
             exprField.setText(exprField.getText() + " " + secondOperandExpr.get(secondOperandExpr.size() - 1));
             secondOperandExpr.remove(secondOperandExpr.size() - 1);

             String secondOperand = this.calculator.StringResult(secondOperandExpr);
             chainedOp.add(secondOperand);
             exprField.setText(exprField.getText() + " " + secondOperand);
         }
         else {
             chainedOp.add(this.exprTokens.get(1));
             chainedOp.add(this.exprTokens.get(2));
             exprField.setText(this.result + " " + this.exprTokens.get(1) + " " + this.exprTokens.get(2));
         }

         this.exprTokens = chainedOp;
         this.result = this.calculator.StringResult(chainedOp);
     }

    private void NewOperation() {
        if (this.isChainedOperation) {
            this.isChainedOperation = false;
            this.exprField.setText("");
            this.valueField.setText("");
            this.exprTokens.clear();
        }
    }
}
