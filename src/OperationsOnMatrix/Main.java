package OperationsOnMatrix;

import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                case 3:
                    System.out.println("Enter size of first matrix:");
                    Matrix m = new Matrix(getArr(in.nextInt(), in.nextInt()));
                    System.out.println("Enter size of second matrix:");
                    Matrix m2 = new Matrix(getArr(in.nextInt(), in.nextInt()));
                    System.out.println(choice == 1 ?
                            "The sum result is:\n" + m.sum(m2) :
                            "The multiplication result is:\n" + m.multiply(m2));
                    break;
                case 2:
                case 5:
                case 6:
                    System.out.println("Enter size of the matrix:");
                    Matrix m3 = new Matrix(getArr(in.nextInt(), in.nextInt()));
                    if (choice == 5 || choice == 6) {
                        if (!m3.isSquare()) {
                            System.out.println("Not a square matrix error");
                        }
                        else {
                            System.out.println("The result is: \n" +
                                    (choice == 5 ? m3.determinant(): m3.inversion()));
                        }
                        break;
                    }
                    System.out.println("Enter constant: ");
                    System.out.println("The scalar matrix product is:\n" + m3.getScalar(in.nextInt()));
                    break;
                case 4:
                    System.out.println("1. Main diagonal\n" +
                            "2. Sub diagonal\n" +
                            "3. Vertical line\n" +
                            "4. Horizontal line\n");
                    int t = in.nextInt();
                    System.out.println("Enter size of the matrix:");
                    Matrix m4 = new Matrix(getArr(in.nextInt(), in.nextInt()));
                    System.out.println(m4.transpose(t));
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    static void printMenu() {
        System.out.println("1. Add matrices\n" +
                "2. Multiply matrix to a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit\n" +
                "Your choice: ");
    }

    static double[][] getArr(int a, int b) {
        double[][] res = new double[a][b];
        System.out.println("Enter matrix: ");
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                res[i][j] = in.nextDouble();
            }
        }
        return res;
    }
}
