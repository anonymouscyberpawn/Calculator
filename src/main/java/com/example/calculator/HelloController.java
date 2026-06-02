package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class HelloController implements Initializable {
    @FXML
    private TextField myTextField;

    @FXML
    private TextField solution;

    public void addNumber(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        myTextField.setText(myTextField.getText() + value);
        calculateLive();
    }

    public void operation(ActionEvent event) {
        String operator = ((Button) event.getSource()).getText();
        if (operator.equals("=")) {

            calculateLive();

            myTextField.clear();
            return;
        }
        String currentText = myTextField.getText();
        if (currentText.isEmpty()) {
            return;
        }
        char lastChar = currentText.charAt(currentText.length() - 1);

        if (lastChar == '+' ||
                lastChar == '-' ||
                lastChar == '*' ||
                lastChar == '/') {
            return;
        }
        myTextField.setText(currentText + operator);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void calculateLive() {
        try{
            String expression = myTextField.getText();
            if(expression.isEmpty()){
                solution.clear();
                return;
            }

            char lastChar = expression.charAt(expression.length() - 1);

            if(lastChar == '+' ||
                lastChar == '-' ||
                lastChar == '*' ||
                lastChar == '/'){
                return;
            }
            double result = evaluateExpression(expression);

            if(result == (long) result){
                solution.setText(String.valueOf((long)result));
            }
            else{
                solution.setText(String.valueOf(result));
            }

        }catch(Exception e){
            e.printStackTrace();
            solution.setText("Error");
        }
    }

    private double evaluateExpression(String expression){
        expression = expression.replaceAll("\\s+", "");
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;

        while(i < expression.length()){
            char c = expression.charAt(i);
            if(Character.isDigit(c) || c == '.'){
                StringBuilder number = new StringBuilder();
                while(i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')){
                    number.append(expression.charAt(i));
                    i++;
                }
                numbers.push(Double.parseDouble(number.toString()));
                continue;
            }
            if(c == '+' || c == '-' || c == '*' || c == '/'){
                while(!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)){
                    performOperation(numbers, operators.pop());
                }
                operators.push(c);
            }
            i++;
        }
        while(!operators.isEmpty()){
            performOperation(numbers, operators.pop());
        }
        return numbers.pop();
    }
    private int precedence(char operator){
        switch (operator){
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
    private void performOperation(Stack<Double> numbers, char operator){
        double b = numbers.pop();
        double a = numbers.pop();

        switch(operator){
            case '+':
                numbers.push(a+b);
                break;

            case '-':
                numbers.push(a-b);
                break;
            case '*':
                numbers.push(a*b);
                break;
            case '/':
                if (b == 0){
                    throw new ArithmeticException("Division by zero");
                }
                numbers.push(a/b);
                break;
        }
    }

    public void addDecimal(ActionEvent event){
        String text = myTextField.getText();
        if(text.isEmpty()){
            myTextField.setText("0.");
            return;
        }
        int lastOperator = Math.max(Math.max(text.lastIndexOf('+'), text.lastIndexOf('-')),
                Math.max(text.lastIndexOf('*'), text.lastIndexOf('/')));

        String currentNumber = text.substring(lastOperator + 1);
        if(!currentNumber.contains(".")){
            myTextField.setText(text + ".");
            calculateLive();
        }
    }


    public void clear(ActionEvent event){

        myTextField.clear();
        solution.clear();

    }

    public void delete(ActionEvent event){

        String string = myTextField.getText();
       if(string.isEmpty()){
           return;
       }
       myTextField.setText(string.substring(0, string.length() - 1));
       calculateLive();

    }

}
