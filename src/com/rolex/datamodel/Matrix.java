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

    //Return transposed matrix
    public double[][] transposeMatrix(){
        double[][] matrixTransposed = new double[this.matrixSize][this.matrixSize];

        for(int i = 0; i < this.matrixSize; i++){
            for(int j = 0; j < this.matrixSize; j++){
                matrixTransposed[i][j] = this.matrix[j][i];
            }
        }
        return matrixTransposed;
    }

    //Temporary method to create matrix
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
}
