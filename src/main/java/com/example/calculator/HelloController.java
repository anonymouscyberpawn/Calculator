package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField myTextField;

    @FXML
    private TextField solution;

    String op = "";
    Double number1;
    Double number2;

    public void addNumber(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        myTextField.setText(myTextField.getText() + value);
    }

    public void operation(ActionEvent event) {
        String operate = ((Button)event.getSource()).getText();
        if(!operate.equals("=")){

            if(!op.equals("")){
                return;
            }
            op = operate;
            number1 = Double.parseDouble(myTextField.getText());
            myTextField.setText(myTextField.getText() + operate);

        }
        else{

            if(op.equals("")){
                return;

            }
            String expression = myTextField.getText();

            String secondPart = expression.substring(expression.indexOf(op) + 1);

            number2 = Double.parseDouble(secondPart);
            calculate(number1, number2, op);
            op = "";

        }
    }

    public void calculate (double n1, double n2, String op) {
        switch (op) {

            case "+" : solution.setText(String.valueOf(n1 + n2));
                break;
            case "-" : solution.setText(String.valueOf(n1 - n2));
                break;
            case "*" : solution.setText(String.valueOf(n1 * n2));
                break;
            case "/" :
                if (n2 == 0){
                    solution.setText("can't divide by zero");
                    return;
                }
                solution.setText(String.valueOf(n1 / n2));
                break;


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clear(ActionEvent event){

        myTextField.setText("");

    }

    public void delete(ActionEvent event){

        String string = myTextField.getText();
        myTextField.setText("");
        for(int i=0;i<string.length()-1;i++){
            myTextField.setText(myTextField.getText()+string.charAt(i));
        }

    }
}
