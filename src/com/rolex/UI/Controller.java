package com.rolex.UI;

import com.rolex.datamodel.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;

public class Controller {

    @FXML
    private Spinner matrixSize;

    @FXML
    public void getMatrixSize(){
        Matrix matrix = new Matrix((int) matrixSize.getValue());
        System.out.println(matrix.getMatrixSize());
    }

}
