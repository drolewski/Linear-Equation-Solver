package com.rolex.UI;

import com.rolex.datamodel.Matrix;
import javafx.beans.DefaultProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.function.DoubleBinaryOperator;

public class Controller {

    //gridPane that will handle coefficients of equations
    private static GridPane equationGridPane = new GridPane();

    //Array list with textField information
    private static ArrayList<TextField> textFields = new ArrayList<>();

    //Array list with result Information
    private static ArrayList<TextField> resultField = new ArrayList<>();

    @FXML
    private Spinner matrixSize;

    @FXML
    private GridPane mainWindow;

    @FXML
    private Button calculateButton;

//    Take size of equations system and generate it
    @FXML
    public void getMatrixSize(){


//      size clear the gridPane and set the size of gaps between element
        equationGridPane.getChildren().clear();
        mainWindow.getChildren().remove(equationGridPane);
        equationGridPane.setVgap(7);
        equationGridPane.setHgap(7);

        int sizeOfMatrix = (int) matrixSize.getValue();
        Matrix matrix = new Matrix(sizeOfMatrix);

        System.out.println(matrix.getMatrixSize());

//        create fields with declared size to input our matrix data
        textFields.clear();
        for(int i = 0 ; i < sizeOfMatrix*sizeOfMatrix ; i++ ) {
            TextField textElement = new TextField();
            textElement.setPromptText("Coefficient " + i);
            textElement.setMaxSize(100, 400);
            textFields.add(textElement);
        }


//        generate elements to the UI layout
        Label equalSign = new Label("=");
        int elementsCounter = 0;
        for(int i = 0 ; i < sizeOfMatrix ; i++){
            for(int j = 0; j < sizeOfMatrix ; j++ , elementsCounter++) {
                equationGridPane.add(textFields.get(elementsCounter),1 + j , 1 + i);
                if(elementsCounter == 0){
                    textFields.get(elementsCounter).requestFocus();
                }
            }
            TextField tf = new TextField();
            tf.setPromptText("Result " + i);
            tf.setMaxSize(100,400);
            resultField.add(tf);
            equationGridPane.add(tf, 2 + sizeOfMatrix , 1 + i);

            if(i == sizeOfMatrix/2){
                equationGridPane.add(equalSign,1 + sizeOfMatrix , i + 1);
            }
        }
        mainWindow.add(equationGridPane,1,2);
        calculateButton.setDisable(false);
    }

//    Calculate the system of linear equations.
    @FXML
    public void calculateMatrix(){

        Matrix matrix = new Matrix((int) matrixSize.getValue());
        ArrayList<Double> matrixVector = new ArrayList<>();
        double[] resultVector = new double[(int) matrixSize.getValue()];

        for(TextField tf : textFields){
            if(tf.getText().equals("")){
                tf.setText("0.0");
            }

            if(tf.getText().contains(",")){ //replace , to . from the input fields
                tf.setText(tf.getText().replace(',','.'));
            }

            //replace elements different than numbers
            if((!tf.getText().matches("^[0-9]++\\.??[0-9]++$")) &&
                    (!tf.getText().matches("^[0-9]++"))){

                tf.setText("0.0");
            }

            matrixVector.add(Double.parseDouble(tf.getText()));
        }

        for(int i = 0; i < (int) matrixSize.getValue(); i++){
            if(resultField.get(i).getText().equals("")){
                resultField.get(i).setText("0.0");
            }

            if(resultField.get(i).getText().contains(",")){ // replace , to .
                resultField.get(i).setText(resultField.get(i).getText().replace(',','.'));
            }

            //replace elements different than numbers
            if((!resultField.get(i).getText().matches("^[0-9]++\\.??[0-9]++$")) &&
                    (!resultField.get(i).getText().matches("^[0-9]++"))){

                resultField.get(i).setText("0.0");
            }

            resultVector[i] = Double.parseDouble(resultField.get(i).getText());
        }

        matrix.createMatrix(matrixVector);
        double[] result;
        if((result = matrix.solveLinearEquationDistributionMatrix(resultVector)) != null){
            for(int i = 0; i < result.length; i++){
                System.out.println("\n");
                System.out.println("X" + (i + 1) + " = " + result[i]);
            }
        }else{
            System.out.println("This System of Linear Equations doesn't have result.");
        }
    }
}
