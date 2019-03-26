package com.rolex.datamodel;

import java.util.ArrayList;

public class Matrix {

    private int matrixSize;
    private double[][] matrix;

    public Matrix(int matrixSize) {
        this.matrixSize = matrixSize;
        this.matrix = new double[matrixSize][matrixSize];
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public double[][] getMatrix() {
        return matrix;
    }

//    Transpose matrix stored in the matrix field
    public void transposeMatrix(){
        double[][] matrixTransposed = new double[this.matrixSize][this.matrixSize];

        for(int i = 0; i < this.matrixSize; i++){
            for(int j = 0; j < this.matrixSize; j++){
                matrixTransposed[i][j] = this.matrix[j][i];
            }
        }
        this.matrix = matrixTransposed;
    }

//    Temporary method to create matrix
    public void createMatrix(ArrayList<Double> listOfElements){
        int vectorPointer=0;
        for(int i = 0; i < this.matrixSize; i++){
            for(int j = 0 ; j < this.matrixSize ; j++){
                this.matrix[i][j] = listOfElements.get(vectorPointer);
                vectorPointer++;
            }
        }
    }

    public void printMatrix(){
        for(int i = 0 ; i < this.matrixSize ; i++ ){
            for( int j = 0 ; j < this.matrixSize ; j++){
                System.out.print(this.matrix[i][j] + "\t\t");
            }
            System.out.println("\n");
        }
    }

//    matrices multiplication with vector parameter
//    this method multiple vector and this class field matrix
//    We dont have to worry about different vectors as Nx1,
//    because in Systems of Linear Equations vector X and produce
//    has only one column
    public double[][] multipleMatrix(double[][] tmpVector){
        double[][] resultMatrix = new double[this.matrixSize][1];

        if(this.matrixSize == tmpVector.length){
            for (int i = 0; i < this.matrixSize ; i++){

                double tempValue = 0;
                for (int j = 0; j < this.matrixSize; j++){

                    tempValue += tmpVector[j][0]*this.matrix[i][j];

                    if(j == this.matrixSize - 1){
                        resultMatrix[i][0] = tempValue;
                    }
                }

            }
        }else{
            System.out.println("Dimensions of matrices aren't compatible.");
        }

        return resultMatrix;
    }
}
