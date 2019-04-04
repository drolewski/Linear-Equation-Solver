package com.rolex.UI;

import com.rolex.datamodel.Matrix;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.util.ArrayList;

public class Controller {

    //gridPane that will handle coefficients of equations
    private static GridPane equationGridPane = new GridPane();

    @FXML
    private Spinner matrixSize;

    @FXML
    private GridPane mainWindow;

    @FXML
    private Button sizeButton;

    @FXML
    public void getMatrixSize(){


        equationGridPane.getChildren().clear();
        mainWindow.getChildren().remove(equationGridPane);

        int sizeOfMatrix = (int) matrixSize.getValue();
        Matrix matrix = new Matrix(sizeOfMatrix);

        System.out.println(matrix.getMatrixSize());

        ArrayList<TextField> textFields = new ArrayList<>();
        for(int i = 0 ; i < sizeOfMatrix*sizeOfMatrix ; i++ ){
            textFields.add(new TextField("Coefficient " + i));
        }


        Label equalSign = new Label("=");
        int elementsCounter = 0;
        for(int i = 0 ; i < sizeOfMatrix ; i++){
            for(int j = 0; j < sizeOfMatrix ; j++ , elementsCounter++) {
                equationGridPane.add(textFields.get(elementsCounter),1 + j , 1 + i);
                if(elementsCounter == 0){
                    textFields.get(elementsCounter).requestFocus();
                }
            }
            equationGridPane.add(new TextField("Result " + i), 2 + sizeOfMatrix , 1 + i);
            if(i == (int) sizeOfMatrix/2){
                equationGridPane.add(equalSign,1 + sizeOfMatrix , i + 1);
            }
        }
        mainWindow.add(equationGridPane,1,2);
    }
}
