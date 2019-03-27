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

//    determinant function, it takes rank of matrix, analyzing row, vector of column indexes, and calculating matrix
//    In this method we use recursion and method similar as Laplace's way to count determinant
    private double det(int rank, int row, int[] vector,double[][] matrixA){
        int result;
        int counter;
        int sign;
        int[] columns = new int[this.matrixSize];

        if(rank == 1){
            return matrixA[row][vector[0]];
        } else{
            result = 0;
            sign = 1;
            for(int i = 0; i < rank ; i++){

                counter = 0;
                for(int j = 0; j < rank -1; j++){
                    if(counter == i){
                        counter++;
                    }
                    columns[j] = vector[counter++];
                }
                result += sign* matrixA[row][vector[i]] * det(rank-1, row + 1, columns, matrixA);
                sign = -sign;
            }
        }
        return result;
    }

//    simplest call of the determinant method
    public double det(){
        int[] vector = new int[this.matrixSize];
        for(int i = 0; i < this.matrixSize ; i++){
            vector[i] = i;
        }
        return det(this.matrixSize,0,vector,this.matrix);
    }
}
































