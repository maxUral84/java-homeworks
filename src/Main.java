import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final int SIZE = 8;
    public static final int MAX_COLORS_NUMBER = 256;
    public static final int ROTATION_STEP = 90;

    public static void main(String[] args) {
        Random random = new Random();
        Scanner console = new Scanner(System.in);
        // создадим матрицу 8x8
        int[][] matrixColors = new int[SIZE][SIZE];

        // заполним матрицу случайными значениями nextInt в диапазоне от 0 до 255:
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrixColors[i][j] = random.nextInt(MAX_COLORS_NUMBER);
            }
        }

        // Вывод исходной матрицы
        System.out.println("Исходная матрица:");
        printMatrix(matrixColors, SIZE);

        // Ввод угла поворота
        System.out.print("\nВведите угол поворота (90, 180, 270):");
        int angle = console.nextInt();

        // Создание новой матрицы для повернутого результата
        int[][] rotatedColors = new int[SIZE][SIZE];

        // Поворот матрицы на заданный угол
        int rotations = angle / ROTATION_STEP;

        for (int r = 0; r < rotations; r++) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    rotatedColors[i][j] = matrixColors[SIZE - 1 - j][i];
                }
            }
        }

        // Вывод повернутой матрицы
        System.out.println("Повернутая матрица на " + angle + " градусов:");
        printMatrix(rotatedColors, SIZE);
    }

    // Метод для вывода матрицы
    public static void printMatrix(int[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.format("%4d", matrix[i][j]);
            }

            System.out.println();
        }
    }
}
