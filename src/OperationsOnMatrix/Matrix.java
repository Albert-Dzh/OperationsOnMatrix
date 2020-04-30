package OperationsOnMatrix;


public class Matrix {

    private final double[][] matrix;
    private final int rows;
    private final int cols;


    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        rows = matrix.length;
        cols = matrix[rows - 1].length;
    }

    private double[][] copyMatrix() {
        double[][] temp = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            if (cols >= 0) {
                System.arraycopy(matrix[i], 0, temp[i], 0, cols);
            }
        }
        return temp;
    }

    Matrix sum(Matrix m) {
        if (rows != m.rows && cols != m.cols) {
            System.out.print("Can't sum two matrices - they are different dimensions");
            return new Matrix(new double[][]{{}});
        }
        double[][] result = copyMatrix();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] += m.matrix[i][j];
            }
        }
        return new Matrix(result);
    }

    Matrix subtract(Matrix m) {
        if (rows != m.rows && cols != m.cols) {
            System.out.print("Can't subtract two matrices - they are different dimensions");
            return new Matrix(new double[][]{{}});
        }
        double[][] result = copyMatrix();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] -= m.matrix[i][j];
            }
        }
        return new Matrix(result);
    }

    Matrix getScalar(double scale) {
        double[][] result = copyMatrix();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] *= scale;
            }
        }
        return new Matrix(result);
    }

    Matrix transpose(int option) {
        if (option == 1) {
            return mainDiag();
        } else if (option == 2) {
            return subDiag();
        } else if (option == 3) {
            return vertLine();
        }
        return horizLine();
    }

    Matrix mainDiag() {
        double[][] result = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return new Matrix(result);
    }

    Matrix subDiag() {
        double[][] result = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[rows - 1 - i][cols - 1 - j];
            }
        }
        return new Matrix(result);
    }

    Matrix horizLine() {
        double[][] temp = new double[rows][cols];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[rows - 1 - i], 0, temp[i], 0, matrix[i].length);
        }
        return new Matrix(temp);
    }

    Matrix vertLine() {
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][cols - 1 - j];
            }
        }
        return new Matrix(result);
    }

    Matrix inversion() {
        double[][] temp = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                temp[i][j] = (i + j) % 2 == 0 ? getD(stripMatrix(matrix, i, j)) :
                        -getD(stripMatrix(matrix, i, j));
            }
        }
        Matrix res = new Matrix(temp);
        double d = 1 / determinant();

        return res.getScalar(d).mainDiag();
    }

    boolean isSquare() {
        return rows == cols;
    }

    double determinant() {
        return getD(matrix);
    }

    double getD(double[][] m) {
        double det = 0;
        if (m.length == 2) {
            return m[0][0] * m[1][1] - m[0][1] * m[1][0];
        }
        for (int i = 0; i < m.length; i++) {
            double[][] temp = stripMatrix(m, 0, i);
            det += i % 2 == 0 ? m[0][i] * getD(temp) : -(m[0][i] * getD(temp));
        }
        return det;
    }

    double[][] stripMatrix(double[][] m, int x, int y) {
        double[][] res = new double[m.length - 1][m.length - 1];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (i != x && j != y) {
                    if (i < x && j < y) {
                        res[i][j] = m[i][j];
                    } else if (i < x) {
                        res[i][j - 1] = m[i][j];
                    } else if (j < y) {
                        res[i - 1][j] = m[i][j];
                    } else {
                        res[i - 1][j - 1] = m[i][j];
                    }
                }
            }
        }
        return res;
    }

    private double[] getRow(int row) {
        return matrix[row];
    }

    private double[] getCol(int col) {
        double[] temp = new double[rows];
        for (int i = 0; i < rows; i++) {
            temp[i] = matrix[i][col];
        }
        return temp;
    }

    Matrix multiply(Matrix m) {
        if (this.cols != m.rows) {
            System.out.print("Can't multiply:\n" +
                    "Num of rows of the " +
                    "FIRST matrix doesn't correlate with " +
                    "num of columns of the SECOND one.");
            return new Matrix(new double[][]{{}});
        }

        int height = rows;  //num of matrix rows (this) #1
        int width = m.cols; //num of matrix cols (m) #2
        double[][] result = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = multiplyRows(getRow(i), m.getCol(j));
            }
        }
        return new Matrix(result);
    }

    private static double multiplyRows(double[] rowA, double[] rowB) {
        double result = 0;
        for (int i = 0; i < rowA.length; i++) {
            result += rowA[i] * rowB[i];
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (double[] d : matrix) {
            for (double s : d) {
                output.append(String.format("%.5f", s)).append(' ');
            }
            output.append("\n");
        }
        return output.toString();
    }
}
